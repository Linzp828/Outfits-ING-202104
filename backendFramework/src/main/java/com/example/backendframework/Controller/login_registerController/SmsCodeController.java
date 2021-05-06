package com.example.backendframework.Controller.login_registerController;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class SmsCodeController {

    @Autowired
    private UserDao userDao;
    Map<String, Object> map = new HashMap<String, Object>();
    @RequestMapping(value = "/sendSmsCode", method = RequestMethod.POST)
    public JSON blogSearch(@RequestBody JSONObject request){
        String phone = request.getString("phone");
        String msg = request.getString("msg");

        if(msg.equals("注册")){
            int num = userDao.phoneFind(phone);
            if (num ==0){
                map.put("code",200);
                map.put("msg","获取验证码成功");
                User user = userDao.findLastData();
                //System.out.println(user);
                String token = TokenUtil.createJWT(String.valueOf(user.getId()+1),"ruijin","crj6",(long)1000*60*5);
                map.put("data",token);
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }else{
                map.put("code",201);
                map.put("msg","该账号已存在");
                map.put("data"," ");
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }
        }else{
            User user = userDao.accountFind(phone);
            String token = TokenUtil.createJWT(String.valueOf(user.getId()),"ruijin","crj6",(long)1000*60*5);
            map.put("code",200);
            map.put("msg","获取验证码成功");
            map.put("data",token);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
    }
}
