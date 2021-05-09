package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.wardrobeDao.WardrobeDao;
import com.example.backendframework.Model.Occasion;
import com.example.backendframework.util.ClimateUtil;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class RecommandController {
    @Autowired
    private WardrobeDao wardrobeDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/recommand", method = RequestMethod.POST)
    public JSON RecommandMatch(@RequestHeader(value = "token") String token) {
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int userId = Integer.parseInt(code.get("ID").toString());
            int level=ClimateUtil.getLevel();
            map.put("code",200);
            map.put("msg","操作成功");
            map.put("data",Integer.toString(level));
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (ExpiredJwtException e) {
            map.put("code",500);
            map.put("msg","token错误1");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code",500);
            map.put("msg","token错误2");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code",500);
            map.put("msg","token错误3");
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
    }
}
