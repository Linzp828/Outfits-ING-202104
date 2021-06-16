package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.matchDao.MatchClothingDao;
import com.example.backendframework.Dao.matchDao.MatchDao;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Model.Match;
import com.example.backendframework.util.StateUtil;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/match")
public class AddMatchController {

    @Autowired
    private MatchDao matchDao;
    @Autowired
    private MatchClothingDao matchClothingDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/addMatch",method = RequestMethod.POST)
    public JSON AddMatch(@RequestBody JSONObject request,@RequestHeader(value = "token") String token){
        String clothingIdList = request.getString("clothingIdArray");
        JSONArray clothingIdArray= JSONArray.parseArray(clothingIdList);
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int userId = Integer.parseInt(code.get("ID").toString());

            Match match=new Match(-1,request.getInteger("occasionId"),request.getString("introduce"),userId);
            int num = matchDao.insertMatch(match);
            int matchId=match.getId();
            //新建搭配成功
//            System.out.println("衣物id数组大小："+clothingIdArray.size());
            if(num == 1 && matchId != -1){
//                System.out.println("进入match——clothing关系建立");
                for(int i=0;i<clothingIdArray.size();i++){
                    //忽略
                    int clothingId=Integer.parseInt(clothingIdArray.get(i).toString());
                    System.out.println(clothingId+"  "+matchId);
                    matchClothingDao.insertMatchClothing(clothingId,matchId);
//                    System.out.println("新增搭配-插入成功");
                }
            }

            map.put("code",StateUtil.SC_OK);
            map.put("msg","操作成功");
        }catch (ExpiredJwtException e) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误1");
        } catch (SignatureException e1) {
            map.put("code",StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误2");
        } catch (MalformedJwtException e2) {
            map.put("code",StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误3");
        }finally {
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }

    }
}
