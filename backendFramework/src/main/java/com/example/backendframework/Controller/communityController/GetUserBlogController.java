package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.util.PathUtil;
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
@RequestMapping("/user")
public class GetUserBlogController {
    @Autowired
    private BlogDao blogDao;
    Map<String, Object> map = new HashMap<String, Object>();

    static final String serverBlog = "http://121.5.100.116/static/blogPic/";

    @RequestMapping(value = "/getBlog", method = RequestMethod.POST)
    /**
     * 获取个人发布博客
     *
     * @param request
     * @param token
     * @return
     */
    public JSON GetAllBlog(@RequestBody JSONObject request, @RequestHeader(value = "token") String token) {
        List<Map<String, Object>> listBlog = new ArrayList<>();
        //String token = request.getString("token");
        int userId = request.getInteger("userId");
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            List<Blog> blogList = blogDao.userFindBlog(userId);
            for (int i = 0; i < blogList.size(); i++) {
                Map<String, Object> mapBlog = new HashMap<String, Object>();
                mapBlog.put("blogId", blogList.get(i).getId());
                mapBlog.put("blogTitle", blogList.get(i).getBlog_title());
                mapBlog.put("blogPic", PathUtil.getBlogPath(blogList.get(i).getBlog_pic_path()));
                mapBlog.put("userId", blogList.get(i).getUser_id());
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
