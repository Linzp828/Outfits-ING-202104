package com.example.backendframework.Controller.login_registerController;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.TokenUtil;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class SmsCodeController {

    @Autowired
    private UserDao userDao;
    Map<String, Object> map = new HashMap<String, Object>();
    @RequestMapping(value = "/sendSmsCode", method = RequestMethod.POST)
    public JSON blogSearch(@RequestBody JSONObject request){
        String phone = request.getString("phone");
        String msg = request.getString("msg");
        ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "108928", "31190557-1e60-47e2-9d86-d64d797b4b9e");
        String msgcode = getMsgCode();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phone);
            params.put("templateId", 5042);
            String[] templateParams = new String[2];
            templateParams[0] = msgcode;
            templateParams[1] = "10分钟";
            params.put("templateParams", templateParams);
            String result = client.send(params);

        }catch (Exception e){
            e.printStackTrace();
        }

        if(msg.equals("注册")){
            int num = userDao.phoneFind(phone);
            if (num ==0){
                map.put("code",200);
                map.put("msg","获取验证码成功");
                User user = userDao.findLastData();
                //System.out.println(user);
                String token = TokenUtil.createJWT(String.valueOf(user.getId()+1),"ruijin",msgcode,(long)1000*60*10);
                map.put("data",token);
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }else{
                map.put("code",201);
                map.put("msg","该账号已存在");
                map.put("data"," ");
                JSONObject jsonp= new JSONObject(map);
                return jsonp;
            }
        }else{
            User user = userDao.accountFind(phone);
            String token = TokenUtil.createJWT(String.valueOf(user.getId()),"ruijin",msgcode,(long)1000*60*10);
            map.put("code",200);
            map.put("msg","获取验证码成功");
            map.put("data",token);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
    }

    /**
     * 生成随机的6位数，短信验证码
     * @return
     */
    private static String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }

}
