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

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class UpdatePasswordController {

    @Autowired
    private UserDao userDao;
    Map<String, Object> map = new HashMap<String, Object>();
    @RequestMapping(value = "/info/update/password", method = RequestMethod.POST)
    public JSON blogSearch(@RequestBody JSONObject request,@RequestHeader(value = "token") String token){
        //String token = request.getString("token");
        String vercode = request.getString("vercode");
        String phone = request.getString("phone");
        String password = request.getString("password");

        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            if(code.get("Subject").equals(vercode)){
                String Md5password = Md5Util.code(password);
                System.out.println(Integer.parseInt(code.get("ID").toString()));
                userDao.updatePassword(Md5password,Integer.parseInt(code.get("ID").toString()));
                String newToken = TokenUtil.createJWT(code.get("ID").toString(),"ruijin",phone,(long)1000*60*60*24*3);
                userDao.updateToken(phone,newToken);
                map.put("code", StateUtil.SC_OK);
                map.put("msg","修改成功");
                map.put("data",newToken);
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }
            map.put("code",StateUtil.SC_BAD_REQUEST);
            map.put("msg","验证码错误");
            map.put("data"," ");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (ExpiredJwtException e) {
            map.put("code",StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误1");
            map.put("data"," ");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code",StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误2");
            map.put("data"," ");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code",StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误3");
            map.put("data"," ");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
    }
}
