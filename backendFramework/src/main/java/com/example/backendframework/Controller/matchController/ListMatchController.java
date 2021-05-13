package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.matchDao.MatchClothingDao;
import com.example.backendframework.Dao.matchDao.MatchDao;
import com.example.backendframework.Dao.wardrobeDao.WardrobeDao;
import com.example.backendframework.Model.Clothing;
import com.example.backendframework.Model.Match;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("match")
public class ListMatchController {
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private MatchClothingDao matchClothingDao;
    @Autowired
    private WardrobeDao wardrobeDao;
    static final String server = "http://121.5.100.116/static/clothingPic/";
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/listMatch",method = RequestMethod.POST)
    public JSONObject ListMatch(@RequestBody JSONObject obj,@RequestHeader(value = "token") String token){
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
        List<Map<String,Object>> data=new ArrayList<>();
        List<Match> matchList= matchDao.listMatch(obj.getInteger("occasionId"));
        for(Match match:matchList){
            Map<String,Object> matchMap=new HashMap<String,Object>();
            matchMap.put("matchId",match.getId());
            matchMap.put("introduce",match.getIntroduce());

            List<Clothing> clothingList=matchClothingDao.listClothing(match.getId());
            List<Map<String,Object>> clothingArray=new ArrayList<>();
            for(Clothing clothing:clothingList){
                Map<String,Object> clothingMap=new HashMap<String,Object>();
                clothingMap.put("clothingId",clothing.getId());
                clothingMap.put("clothingPic",server+clothing.getClothing_pic());
                clothingArray.add(clothingMap);
            }
            matchMap.put("clothing",clothingArray);
            data.add(matchMap);
        }
        map.put("code",200);
        map.put("msg","查找成功");
        map.put("data",data);
        JSONObject jsonp= new JSONObject(map);
        return jsonp;
    }
}
