package com.example.backendframework.Controller.login_registerController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改用户信息
 */
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class UpdateUserIfo {
    @Autowired
    private UserDao userDao;
    Map<String, Object> map = new HashMap<String, Object>();
    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    public JSON UpdateUserIfo(@RequestBody JSONObject request){
        int id = request.getInteger("id");
        String user_account = request.getString("phone");
        String user_password = " ";
        String user_nickname = request.getString("nickname");
        String user_sex = request.getString("sex");
        String user_pic_path = request.getString("pic_path");
        String user_profile = request.getString("profile");
        String user_token = request.getString("token");
        User user1 = new User(id,user_account,user_password,user_nickname,user_sex,user_pic_path,user_profile,user_token);
        System.out.println(user1);
        int success = userDao.updateUserIfo(user1);
        if(success!=1){
            map.put("code",500);
            map.put("msg","修改失败");
            map.put("data","fail");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
        map.put("code",200);
        map.put("msg","修改成功");
        map.put("data","success");
        JSONObject jsonp= new JSONObject(map);
        return jsonp;
    }
}
