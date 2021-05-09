package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.matchDao.MatchClothingDao;
import com.example.backendframework.Dao.matchDao.MatchDao;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Model.Match;
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
public class addMatchController {

    @Autowired
    private MatchDao matchDao;
    @Autowired
    private MatchClothingDao matchClothingDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/addMatch",method = RequestMethod.POST)
    public JSON AddMatch(@RequestBody JSONObject request){
        String token = request.getString("token");
        String clothingIdList = request.getString("clothingIdArray");
        JSONArray clothingIdArray= JSONArray.parseArray(clothingIdList);
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int userId = Integer.parseInt(code.get("ID").toString());

            Match match=new Match(request.getInteger("occasionId"),request.getString("introduce"),userId);
            int num = matchDao.insertMatch(match);
            int matchId=match.getId();
            //新建搭配成功
            if(num == 1 && matchId != -1){
                for(int i=0;i<clothingIdArray.size();i++){
                    //忽略
                    int flag = matchClothingDao.insertMatchClothing(Integer.parseInt(clothingIdArray.get(i).toString()),matchId);
                    if(flag!=1){
                        System.out.println("新增搭配-插入失败");
                    }
                }
            }

            map.put("code",200);
            map.put("msg","操作成功");
        }catch (ExpiredJwtException e) {
            map.put("code",500);
            map.put("msg","token错误1");
        } catch (SignatureException e1) {
            map.put("code",500);
            map.put("msg","token错误2");
        } catch (MalformedJwtException e2) {
            map.put("code",500);
            map.put("msg","token错误3");
        }finally {
            map.put("data","");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }

    }
}
