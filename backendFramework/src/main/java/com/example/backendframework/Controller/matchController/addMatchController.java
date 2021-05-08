package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.matchDao.MatchClothingDao;
import com.example.backendframework.Dao.matchDao.MatchDao;
import com.example.backendframework.Model.Blog;
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
    /*
    @Autowired
    private MatchDao matchDao;
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
            int num = matchDao.matchInsert(request.getInteger("occasionId"),request.getString("introduce"),
                    userId);

            //新建搭配成功
            if(num == 1){
                for(int i=0;i<clothingIdArray.size();i++){
                    //忽略
                    int flag = matchClothingDao.MatchClothingInsert(clothingIdArray.get(i),);
                    //在循环内定义，不然会因为map的特性导致存的五次都变成同一个
                    Map<String, Object> mapBlog = new HashMap<String, Object>();
                    if(num!=0){
                        mapBlog.put("favorite",1);
                    }else{
                        mapBlog.put("favorite",0);
                    }
                    mapBlog.put("blogId",list.get(0).getId());
                    mapBlog.put("blogTitle",list.get(0).getBlog_title());
                    mapBlog.put("blogPic",list.get(0).getBlog_pic_path());
                    mapBlog.put("userId",list.get(0).getUser_id());
                    listBlog.add(mapBlog);
                }
            }

            map.put("code",200);
            map.put("msg","操作成功");
            map.put("data",listBlog);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (ExpiredJwtException e) {
            map.put("code",500);
            map.put("msg","token错误1");
            map.put("data",listBlog);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code",500);
            map.put("msg","token错误2");
            map.put("data",listBlog);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code",500);
            map.put("msg","token错误3");
            map.put("data",listBlog);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }

    }
    */
}
