package com.example.backendframework.Controller.login_registerController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Model.User;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.util.Md5Util;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 */
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserDao userDao;
    //登录验证
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSON checkLogin(@RequestBody JSONObject request,@RequestHeader(value = "token") String user_token) {
        String user_account= request.getString("phone");
        String user_password=request.getString("password");
        //String user_token=request.getString("token");

        Map<String, Object> map = new HashMap<String, Object>();
        List<User> userList = userDao.accountFindUser(user_account);
        if(userList.size()==0){
            map.put("code",500);
            map.put("msg","登入失败");
            map.put("data","账号错误");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
        User user = userList.get(0);
        String Md5password = Md5Util.code(user_password);


        //解析token，判断是否正确
        try {
            Map<String, Object> code = TokenUtil.parseJWT(user_token);
        System.out.println(code);
        if(code.get("code").equals("update")){
            String newToken = TokenUtil.createJWT(code.get("ID").toString(),code.get("Issuer").toString(),code.get("Subject").toString(),(long)1000*60*60*24*3);
            userDao.updateToken(user_account,newToken);
            map.put("code",200);
            map.put("msg","登入成功");
            map.put("token",newToken);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }else if (code.get("code").equals("success")){
             map.put("code",200);
             map.put("msg","登入成功");
             map.put("token",user_token);
             JSONObject jsonp= new JSONObject(map);
             return jsonp;
        }else{                    //不正确的话就要判断密码咯

            if (user.getUser_password().equals(Md5password)) {
                String newToken = TokenUtil.createJWT(String.valueOf(user.getId()),"ruijin",user.getUser_account(),(long)1000*60*60*24*3);
                userDao.updateToken(user_account,newToken);
                map.put("token",newToken);
                map.put("code",200);
                map.put("msg","登入成功");
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }
        }
        map.put("code",500);
        map.put("msg","登入失败");
        JSONObject jsonp= new JSONObject(map);
        return jsonp;

        }catch (MalformedJwtException e){
            if (user.getUser_password().equals(Md5password)) {
                String newToken = TokenUtil.createJWT(String.valueOf(user.getId()),"ruijin",user.getUser_account(),(long)1000*60*60*24*3);
                userDao.updateToken(user_account,newToken);
                map.put("token",newToken);
                map.put("code",200);
                map.put("msg","登入成功");
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }
            map.put("code",500);
            map.put("msg","登入失败");
            map.put("data",Md5password);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (SignatureException e1){
            if (user.getUser_password().equals(Md5password)) {
                String newToken = TokenUtil.createJWT(String.valueOf(user.getId()),"ruijin",user.getUser_account(),(long)1000*60*60*24*3);
                userDao.updateToken(user_account,newToken);
                map.put("token",newToken);
                map.put("code",200);
                map.put("msg","标签登入成功");
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }
            map.put("code",500);
            map.put("msg","标签登入失败");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (ExpiredJwtException e2){
            if (user.getUser_password().equals(Md5password)) {
                String newToken = TokenUtil.createJWT(String.valueOf(user.getId()),"ruijin",user.getUser_account(),(long)1000*60*60*24*3);
                userDao.updateToken(user_account,newToken);
                map.put("token",newToken);
                map.put("code",200);
                map.put("msg","超时登入成功");
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }
            map.put("code",500);
            map.put("msg","超时登入失败");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }

    }
}
