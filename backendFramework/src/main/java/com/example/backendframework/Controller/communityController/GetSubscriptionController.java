package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Model.SubscribeUser;
import com.example.backendframework.Dao.communityDao.BlogDao;
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

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/blog")
public class GetSubscriptionController {
    @Autowired
    private BlogDao blogDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/getSubscription", method = RequestMethod.POST)
    public JSON GetSubscription(@RequestBody JSONObject request/*,@RequestHeader("token") String token*/){
        List<Map<String,Object>> listBlog = new ArrayList<>();
        String token = request.getString("token");
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            List<SubscribeUser> subscribeUserList = blogDao.userFindSubscribe(Integer.parseInt(code.get("ID").toString()));

            for(int i=0;i<subscribeUserList.size();i++){
                List<Blog> blogList = blogDao.userFindBlog(subscribeUserList.get(i).getSubscribe_user_id());
                for (int j=0;j<blogList.size();j++){
                    Map<String, Object> mapBlog = new HashMap<String, Object>();

                    int num = blogDao.userBlogFind(Integer.parseInt(code.get("ID").toString()),blogList.get(j).getId());
                    if(num!=0){
                        mapBlog.put("favorite",1);
                    }else{
                        mapBlog.put("favorite",0);
                    }

                    mapBlog.put("blogId",blogList.get(j).getId());
                    mapBlog.put("blogTitle",blogList.get(j).getBlog_title());
                    mapBlog.put("blogPic",blogList.get(j).getBlog_pic_path());
                    mapBlog.put("userId",blogList.get(j).getUser_id());
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
}
