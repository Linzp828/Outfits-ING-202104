package com.example.backendframework.Controller.meController;


import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.GetIntroDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class GetUserController {
    @Autowired
    private GetIntroDao getIntroDao;

    @RequestMapping("getIntro")
    public JSONObject getInfo(@RequestBody JSONObject request){
        int userId = request.getInteger("userId");
        String token = request.getString("token");

        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("msg","操作成功");

        User userInfo =getIntroDao.getIntro(userId,token);
        Map<String, Object> mapUser = new HashMap<String, Object>();
        mapUser.put("userNickname",userInfo.getUser_nickname());
        mapUser.put("userPic",userInfo.getUser_pic_path());
        mapUser.put("userSex",userInfo.getUser_sex());
        mapUser.put("userProfile",userInfo.getUser_profile());

        response.put("data",mapUser);

        return response;
    }

}
