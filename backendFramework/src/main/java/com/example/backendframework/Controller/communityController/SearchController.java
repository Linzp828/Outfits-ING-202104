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

import java.util.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/blog")
public class SearchController {
    @Autowired
    private BlogDao blogDao;
    Map<String, Object> map = new HashMap<String, Object>();
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public JSON blogSearch(@RequestBody JSONObject request){
        List<Map<String,Object>> listBlog = new ArrayList<>();
        List<Map<String,Object>> listBlog1 = new ArrayList<>();
        String token = request.getString("token");
        String keyword = request.getString("keyword");
        try {
            //判断token是否正常
            Map<String, Object> code = TokenUtil.parseJWT(token);

            //搜article
            List<Blog> blogList1 = blogDao.articleSearch(keyword);
            for (int i=0;i<blogList1.size();i++){
                Map<String, Object> mapBlog1 = new HashMap<String, Object>();
                mapBlog1.put("blogId",blogList1.get(i).getId());
                mapBlog1.put("blogTitle",blogList1.get(i).getBlog_title());
                mapBlog1.put("blogPic",blogList1.get(i).getBlog_pic_path());
                mapBlog1.put("userId",blogList1.get(i).getUser_id());
                listBlog.add(mapBlog1);
            }

            //搜title
            List<Blog> blogList2 = blogDao.titleSearch(keyword);
            for (int i=0;i<blogList2.size();i++){
                Map<String, Object> mapBlog2 = new HashMap<String, Object>();
                mapBlog2.put("blogId",blogList2.get(i).getId());
                mapBlog2.put("blogTitle",blogList2.get(i).getBlog_title());
                mapBlog2.put("blogPic",blogList2.get(i).getBlog_pic_path());
                mapBlog2.put("userId",blogList2.get(i).getUser_id());
                listBlog.add(mapBlog2);
            }

            //去重
            Set<Map<String,Object>> setBlog = new HashSet<>();
            for(Map<String,Object> map1 : listBlog){
                if(setBlog.add(map1)){
                    listBlog1.add(map1);
                }
            }
            //System.out.println(listBlog1);
            //System.out.println(listBlog);

            map.put("code",200);
            map.put("msg","操作成功");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (ExpiredJwtException e) {
            map.put("code",500);
            map.put("msg","token错误1");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code",500);
            map.put("msg","token错误2");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code",500);
            map.put("msg","token错误3");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
    }
}
