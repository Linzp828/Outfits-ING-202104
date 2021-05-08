package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.matchDao.MatchClothingDao;
import com.example.backendframework.Dao.matchDao.MatchDao;
import com.example.backendframework.Dao.matchDao.OccasionDao;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/match")
public class addOccasionController {
    @Autowired
    private OccasionDao occasionDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/addOccasion",method = RequestMethod.POST)
    public JSONObject AddOccasion(@RequestBody JSONObject obj,@RequestHeader(value = "token") String token){
        //String token = obj.getString("token");
        Map<String, Object> code;
        try{
            code = TokenUtil.parseJWT(token);
        }catch(ExpiredJwtException e) {
            map.put("code",500);
            map.put("msg","token错误1");
            map.put("data", "");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (SignatureException e1) {
            map.put("code",500);
            map.put("msg","token错误2");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (MalformedJwtException e2) {
            map.put("code",500);
            map.put("msg","token错误3");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
        int userId = Integer.parseInt(code.get("ID").toString());
        int num = occasionDao.insertOccasion(obj.getString("occasionName"),userId);

        if(num == 1){
            map.put("code",200);
            map.put("msg","添加成功");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
        map.put("code",400);
        map.put("msg","添加失败");
        map.put("data","");
        JSONObject jsonp= new JSONObject(map);
        return jsonp;
    }
}
