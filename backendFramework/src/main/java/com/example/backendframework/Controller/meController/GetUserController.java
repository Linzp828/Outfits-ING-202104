package com.example.backendframework.Controller.meController;


import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.GetIntroDao;
import com.example.backendframework.Dao.meDao.SubscribeDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class GetUserController {
    @Autowired
    private GetIntroDao getIntroDao;

    @Autowired
    private SubscribeDao subscribeDao;

    @RequestMapping("getIntro")
    public JSONObject getInfo(@RequestBody JSONObject request,@RequestHeader(value = "token") String token){
        int checkedUserId = request.getInteger("userId");
        //String token = request.getString("token");
        boolean isSubscribed = false;

        JSONObject response = new JSONObject();

        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int userId = Integer.parseInt(code.get("ID").toString());
            User userInfo =getIntroDao.getIntro(checkedUserId);
            Map<String, Object> mapUser = new HashMap<>();
            List<User> subscription = subscribeDao.getSubscription(userId);
            for (User user : subscription) {
                if(user.getId()==checkedUserId){
                    mapUser.put("userState",1);
                    isSubscribed = true;
                }
            }

            if(!isSubscribed){
                mapUser.put("userState",2);
            }
            if(userId == checkedUserId){
                mapUser.put("userState",0);
            }

            mapUser.put("userNickname", userInfo.getUser_nickname());
            mapUser.put("userPic", userInfo.getUser_pic_path());
            mapUser.put("userSex", userInfo.getUser_sex());
            mapUser.put("userProfile", userInfo.getUser_profile());
            response.put("data",mapUser);
            response.put("code",200);
            response.put("msg","操作成功");

        } catch (NumberFormatException e) {
            response.put("code",500);
            response.put("msg","Token错误");
            response.put("data","");
        }


        return response;
    }

}
