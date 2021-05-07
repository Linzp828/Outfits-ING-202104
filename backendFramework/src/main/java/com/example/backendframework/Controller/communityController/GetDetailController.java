package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Dao.communityDao.BlogDao;
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
@RequestMapping("/blog")
public class GetDetailController {
    @Autowired
    private BlogDao blogDao;
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> mapData = new HashMap<String, Object>();
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public JSON GetDetail(@RequestBody JSONObject request,@RequestHeader(value = "token") String token){
        //String token = request.getString("token");

        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);

            int blogId = request.getInteger("blogId");
            List<Blog> list=blogDao.blogIdFindBlog(blogId);

            int num = blogDao.userBlogFind(Integer.parseInt(code.get("ID").toString()),blogId);
            if (num==0){
                mapData.put("favorite",0);
            }else{
                mapData.put("favorite",1);
            }

            mapData.put("blogId",blogId);
            mapData.put("title",list.get(0).getBlog_title());
            mapData.put("article",list.get(0).getBlog_article());
            mapData.put("picture",list.get(0).getBlog_pic_path());
            mapData.put("time",list.get(0).getBlog_released_time());
            mapData.put("userId",list.get(0).getUser_id());
            map.put("code",200);
            map.put("msg","操作成功");
            map.put("data",mapData);
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
            map.put("data"," ");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code",500);
            map.put("msg","token错误3");
            map.put("data"," ");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (IndexOutOfBoundsException e3){
            map.put("code",500);
            map.put("msg","不存在这个博客");
            map.put("data"," ");
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }



    }
}
