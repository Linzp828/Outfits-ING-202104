package com.example.backendframework.Controller.login_registerController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.Md5Util;
import com.example.backendframework.util.StateUtil;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class registerController {

    @Autowired
    private UserDao userDao;

    /**
     * 注册操作
     *
     * @param request
     * @param token
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSON register(@RequestBody JSONObject request, @RequestHeader(value = "token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        //String token = request.getString("token");
        String vercode = request.getString("vercode");
        String phone = request.getString("phone");
        String password = request.getString("password");
        String nickname = request.getString("nickname");
        String sex = request.getString("sex");
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int num = userDao.phoneFind(phone);
            if (num == 0) {
                if (code.get("Subject").equals(vercode)) {
                    String newToken = TokenUtil.createJWT(code.get("ID").toString(), "ruijin", phone, (long) 1000 * 60 * 60 * 24 * 3);
                    String Md5password = Md5Util.code(password);
                    //a.jpg为默认头像路径
                    User user = new User(Integer.parseInt(code.get("ID").toString()), phone, Md5password, nickname, sex, "a.jpg", " ", newToken);
                    userDao.insertUser(user);
                    map.put("code", StateUtil.SC_OK);
                    map.put("msg", "注册成功");
                    map.put("data", newToken);
                    JSONObject jsonp = new JSONObject(map);
                    return jsonp;
                }
                map.put("code", StateUtil.SC_BAD_REQUEST);
                map.put("msg", "验证码错误");
                map.put("data", " ");
                JSONObject jsonp = new JSONObject(map);
                return jsonp;
            }
            map.put("code", StateUtil.SC_BAD_REQUEST);
            map.put("msg", "账号已存在");
            map.put("data", " ");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;

        } catch (ExpiredJwtException e) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误1");
            map.put("data", " ");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误2");
            map.put("data", " ");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误3");
            map.put("data", " ");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        }
    }
}
