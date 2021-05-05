package com.example.backendframework.Controller.meController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.ModifyInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ModifyInfoController {

    @Autowired
    private ModifyInfoDao modifyInfoDao;

    @RequestMapping("/modifyInfo")
    public JSONObject modifyIntro(@RequestBody JSONObject request){
        int userId = request.getInteger("userId");
        String userNickname = request.getString("userNickname");
        String userSex = request.getString("userSex");
        String userProfile = request.getString("userProfile");

        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("msg","操作成功");

        modifyInfoDao.modifyInfo(userId,userNickname,userSex,userProfile);

        response.put("data",null);

        return response;
    }

}
