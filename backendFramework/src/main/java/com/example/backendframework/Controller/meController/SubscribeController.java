package com.example.backendframework.Controller.meController;


import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.SubscribeDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SubscribeController {
    @Autowired
    private SubscribeDao subscribeDao;

    @RequestMapping("/subscribe")
    public JSONObject getSubscription(@RequestBody JSONObject request,@RequestHeader(value = "token") String token){
        int subscribeUserId = request.getInteger("userId");
        //String token = request.getString("token");
        JSONObject response = new JSONObject();
        boolean isSubscribed = false;

        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int userId = Integer.parseInt(code.get("ID").toString());
            List<User> subscription = subscribeDao.getSubscription(userId);
            for (User user : subscription) {
                if(user.getId()==subscribeUserId){
                    isSubscribed = true;
                    subscribeDao.deleteSubscribeUser(userId,subscribeUserId);
                    response.put("code",200);
                    response.put("msg","取消关注成功");
                    response.put("data",null);
                    break;
                }
            }
            if(!isSubscribed){
                subscribeDao.addSubscribeUser(userId,subscribeUserId);
                response.put("code",200);
                response.put("msg","关注成功");
                response.put("data",null);
            }

        } catch (NumberFormatException e) {
            response.put("code",500);
            response.put("msg","Token错误");
            response.put("data","请重新登录");
        }

        return response;
    }

}
