package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Model.Occasion;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import com.example.backendframework.Dao.matchDao.OccasionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class ListOccasionController {
    @Autowired
    private OccasionDao occasionDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/listOccasion", method = RequestMethod.POST)
    public JSON ListOccasion(@RequestHeader(value = "token") String token){
        List<Map<String,Object>> listOccasion = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int userId = Integer.parseInt(code.get("ID").toString());
            List<Occasion> list=occasionDao.listOccasion(userId);
            for(Occasion occasion:list) {
                Map<String,Object> occasionMap=new HashMap<String,Object>();
                occasionMap.put("occasionId",occasion.getId());
                occasionMap.put("occasionName",occasion.getOccasion_name());
                listOccasion.add(occasionMap);
            }

            map.put("code",200);
            map.put("msg","操作成功");
            map.put("data",listOccasion);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (ExpiredJwtException e) {
            map.put("code",500);
            map.put("msg","token错误1");
            map.put("data",listOccasion);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code",500);
            map.put("msg","token错误2");
            map.put("data",listOccasion);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code",500);
            map.put("msg","token错误3");
            map.put("data",listOccasion);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }

    }
}
