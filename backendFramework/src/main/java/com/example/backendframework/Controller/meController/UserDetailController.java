package com.example.backendframework.Controller.meController;


import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.GetUserDetailDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class UserDetailController {
    @Autowired
    private GetUserDetailDao getUserDetailDao;

    @RequestMapping("/getDetail")
    public JSONObject modifyIntro(@RequestBody JSONObject request){
        String token = request.getString("token");
        Map<String, Object> mapUser = new HashMap<>();
        JSONObject response = new JSONObject();
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            User userDetail = getUserDetailDao.getDetail(Integer.parseInt(code.get("ID").toString()));
            mapUser.put("userAccount",userDetail.getUser_account());
            mapUser.put("userNickname",userDetail.getUser_nickname());
            mapUser.put("userPic",userDetail.getUser_pic_path());
            mapUser.put("userSex",userDetail.getUser_sex());
            mapUser.put("userProfile",userDetail.getUser_profile());
            response.put("code",200);
            response.put("msg","操作成功");
            response.put("data",mapUser);

        } catch (NumberFormatException e) {
            response.put("code",500);
            response.put("msg","Token错误");
            response.put("data","");
        }

        return response;
    }

}