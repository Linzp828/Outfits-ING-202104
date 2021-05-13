package com.example.backendframework.Controller.meController;


import com.alibaba.fastjson.JSONObject;
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
public class UserDetailController {
    @Autowired
    private UserDao userDao;

    static final String server = "http://121.5.100.116/static/headPic/";

    /**
     * @author:  林龙星
     * @date:2021-5-4 13:20
     * @description: 根据用户token获取用户的信息
     * @param:  token
     * @return: response
     */
    @RequestMapping("/getDetail")
    public JSONObject modifyIntro(@RequestHeader(value = "token") String token){
        //String token = request.getString("token");
        Map<String, Object> mapUser = new HashMap<>();
        JSONObject response = new JSONObject();
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            User userDetail = userDao.getIntro(Integer.parseInt(code.get("ID").toString()));
            mapUser.put("userId",userDetail.getId());
            mapUser.put("userAccount",userDetail.getUser_account());
            mapUser.put("userNickname",userDetail.getUser_nickname());
            mapUser.put("userPic",server+userDetail.getUser_pic_path());
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
