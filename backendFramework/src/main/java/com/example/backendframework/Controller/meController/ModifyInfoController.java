package com.example.backendframework.Controller.meController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.ModifyInfoDao;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class ModifyInfoController {

    @Autowired
    private ModifyInfoDao modifyInfoDao;

    @RequestMapping("/modifyInfo")
    public JSONObject modifyIntro(@RequestBody JSONObject request,@RequestHeader(value = "token") String token){
        String userNickname = request.getString("userNickname");
        String userSex = request.getString("userSex");
        String userProfile = request.getString("userProfile");
        JSONObject response = new JSONObject();

        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int userId = Integer.parseInt(code.get("ID").toString());

            response.put("code",200);
            response.put("msg","操作成功");

            modifyInfoDao.modifyInfo(userId,userNickname,userSex,userProfile);

            response.put("data",null);

        } catch (NumberFormatException e) {
            response.put("code",500);
            response.put("msg","操作失败");
            e.printStackTrace();
        }

        return response;
    }

}
