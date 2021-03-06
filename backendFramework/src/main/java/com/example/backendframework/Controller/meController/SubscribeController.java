package com.example.backendframework.Controller.meController;


import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.SubscribeDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.StateUtil;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class SubscribeController {
    @Autowired
    private SubscribeDao subscribeDao;



    /**
     * @author:  林龙星
     * @date:2021-5-4 13:20
     * @description: 关注用户
     * @param:  request,token
     * @return: response
     */
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
                    response.put("code",StateUtil.SC_OK);
                    response.put("msg","取消关注成功");
                    response.put("data",null);
                    break;
                }
            }
            if(!isSubscribed){
                subscribeDao.addSubscribeUser(userId,subscribeUserId);
                response.put("code",StateUtil.SC_OK);
                response.put("msg","关注成功");
                response.put("data",null);
            }

        } catch (NumberFormatException e) {
            response.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            response.put("msg","Token错误");
            response.put("data","请重新登录");
        }

        return response;
    }

}
