package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.Model.SubscribeUser;
import com.example.backendframework.util.StateUtil;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/blog")
public class GetIntroController {
    @Autowired
    private BlogDao blogDao;
    Map<String, Object> map = new HashMap<String, Object>();

    static final String serverBlog = "http://121.5.100.116/static/blogPic/";

    @RequestMapping(value = "/getIntro", method = RequestMethod.POST)
    /**
     * 获取单页博客
     *
     * @param request
     * @param token
     * @return
     */
    public JSON GetIntro(@RequestBody JSONObject request, @RequestHeader(value = "token") String token) {
        List<Map<String, Object>> listBlog = new ArrayList<>();
        //获取数组
        //String[]  blogIdList =request.getParameterValues("blogIdArray");
        //String token = request.getString("token");
        String blogIdList = request.getString("blogIdArray");
        JSONArray blogIdArray = JSONArray.parseArray(blogIdList);
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);

            for (int i = 0; i < blogIdArray.size(); i++) {
                //System.out.println(blogIdArray.get(i));
                //object先转换成string才能转换成int
                List<Blog> list = blogDao.blogIdFindBlog(Integer.parseInt(blogIdArray.get(i).toString()));
                int num = blogDao.userBlogFind(Integer.parseInt(code.get("ID").toString()), Integer.parseInt(blogIdArray.get(i).toString()));
                //在循环内定义，不然会因为map的特性导致存的五次都变成同一个
                Map<String, Object> mapBlog = new HashMap<String, Object>();
                if (num != 0) {
                    mapBlog.put("favorite", 1);
                } else {
                    mapBlog.put("favorite", 0);
                }
                mapBlog.put("blogId", list.get(0).getId());
                mapBlog.put("blogTitle", list.get(0).getBlog_title());
                mapBlog.put("blogPic", serverBlog + list.get(0).getBlog_pic_path());
                mapBlog.put("userId", list.get(0).getUser_id());
                listBlog.add(mapBlog);
            }
            map.put("code", StateUtil.SC_OK);
            map.put("msg", "操作成功");
            map.put("data", listBlog);
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (ExpiredJwtException e) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误1");
            map.put("data", listBlog);
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误2");
            map.put("data", listBlog);
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误3");
            map.put("data", listBlog);
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        }

    }
}
