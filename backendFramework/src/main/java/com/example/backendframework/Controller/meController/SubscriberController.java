package com.example.backendframework.Controller.meController;


import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.SubscribeDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SubscriberController {
    @Autowired
    private SubscribeDao subscribeDao;

    @RequestMapping("/getSubscriber")
    public JSONObject getSubscription(@RequestBody JSONObject request){
        int userId = request.getInteger("userId");
        String token = request.getString("token");
        List<Map<String, Object>> listUser = new ArrayList<>();
        JSONObject response = new JSONObject();

        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            List<User> subscriber = subscribeDao.getSubscriber(userId);
            for (User user : subscriber) {
                Map<String, Object> mapUser = new HashMap<>();
                mapUser.put("userId",user.getId());
                mapUser.put("userNickname", user.getUser_nickname());
                mapUser.put("userPic", user.getUser_pic_path());
                mapUser.put("userSex", user.getUser_sex());
                mapUser.put("userProfile", user.getUser_profile());
                listUser.add(mapUser);
            }
            response.put("code",200);
            response.put("msg","操作成功");
            response.put("data",listUser);

        } catch (NumberFormatException e) {
            response.put("code",500);
            response.put("msg","Token错误");
            response.put("data","请重新登录");
        }

        return response;
    }
}
