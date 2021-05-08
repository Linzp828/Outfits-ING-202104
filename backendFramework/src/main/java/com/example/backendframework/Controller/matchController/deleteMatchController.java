package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.matchDao.MatchClothingDao;
import com.example.backendframework.Dao.matchDao.MatchDao;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/match")
public class deleteMatchController {
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private MatchClothingDao matchClothingDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/deleteMatch",method = RequestMethod.POST)
    public JSONObject DeleteMatch(@RequestBody JSONObject obj,@RequestHeader(value = "token") String token){
        //String token = obj.getString("token");
//      String newToken = TokenUtil.createJWT("2","ruijin", "15260011385",(long)1000*60*60*24*3);
//      System.out.println(newToken);
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
        int num1 = matchDao.deleteMatch(obj.getInteger("matchId"));
        int num2 = matchClothingDao.deleteMatchClothing(obj.getInteger("matchId"));
        System.out.println("搭配中包含了"+Integer.toString(num2)+"衣物");
        if(num1 == 1){
            map.put("code",200);
            map.put("msg","删除成功");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
        map.put("code",403);
        map.put("msg","删除失败");
        map.put("data","");
        JSONObject jsonp= new JSONObject(map);
        return jsonp;
    }
}
