package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.matchDao.MatchClothingDao;
import com.example.backendframework.Dao.matchDao.MatchDao;
import com.example.backendframework.Dao.matchDao.OccasionDao;
import com.example.backendframework.Model.Occasion;
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
public class DeleteOccasionController {
    @Autowired
    private OccasionDao occasionDao;
    @Autowired
    private MatchDao matchDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/deleteOccasion",method = RequestMethod.POST)
    public JSONObject DeleteOccasion(@RequestBody JSONObject obj,@RequestHeader(value = "token") String token){
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
        Occasion occasion= occasionDao.getOccasion(obj.getInteger("occasionId"));
        int num1 = matchDao.existOccasion(occasion.getId());
//        System.out.println("存在场合"+Integer.toString(num1));
        //该场合下有搭配，不让删除
        if(num1==1){
            map.put("code",403);
            map.put("msg","该场合下有搭配");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }

        //删除场合
        int num2 = occasionDao.deleteOccasion(occasion.getId());
//        System.out.println("删除场合"+Integer.toString(num2));

        if(num2>0){
            map.put("code",200);
            map.put("msg","删除成功");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
        map.put("code",400);
        map.put("msg","删除失败");
        map.put("data","");
        JSONObject jsonp= new JSONObject(map);
        return jsonp;
    }
}
