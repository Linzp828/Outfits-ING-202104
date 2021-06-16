package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Model.CollectedBlog;
import com.example.backendframework.Model.SubscribeUser;
import com.example.backendframework.Model.User;
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
@RequestMapping("/blog")
public class GetCollectionController {
    @Autowired
    private BlogDao blogDao;

    @Autowired
    private UserDao userDao;
    Map<String, Object> map = new HashMap<String, Object>();

    static final String serverBlog = "http://121.5.100.116/static/blogPic/";
    static final String serverHead = "http://121.5.100.116/static/headPic/";

    /**
     * 获取收藏博客
     *
     * @param request
     * @param token
     * @return
     */
    @RequestMapping(value = "/getCollection", method = RequestMethod.POST)
    public JSON GetAllBlog(@RequestBody JSONObject request, @RequestHeader(value = "token") String token) {
        List<Map<String, Object>> listBlog = new ArrayList<>();
        //String token = request.getString("token");
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            List<CollectedBlog> collectedBlogList = blogDao.userFindBlogId(Integer.parseInt(code.get("ID").toString()));

            for (int i = 0; i < collectedBlogList.size(); i++) {
                List<Blog> blogList = blogDao.IdFindBlog(collectedBlogList.get(i).getBlog_id());
                for (int j = 0; j < blogList.size(); j++) {
                    Map<String, Object> mapBlog = new HashMap<String, Object>();
                    mapBlog.put("blogId", blogList.get(j).getId());
                    mapBlog.put("blogTitle", blogList.get(j).getBlog_title());
                    mapBlog.put("blogPic", PathUtil.getBlogPath(blogList.get(j).getBlog_pic_path()));
                    mapBlog.put("userId", blogList.get(j).getUser_id());
                    mapBlog.put("blog_released_time", blogList.get(j).getBlog_released_time());

                    User user1 = userDao.getIntro(blogList.get(j).getUser_id());
                    mapBlog.put("userId", blogList.get(j).getUser_id());
                    mapBlog.put("user_pic", PathUtil.getHeadPath(user1.getUser_pic_path()));
                    mapBlog.put("user_nickname", user1.getUser_nickname());

                    int t1 = userDao.userIdGetSubscribe(Integer.parseInt(code.get("ID").toString()), blogList.get(j).getUser_id());
                    if (t1 == 1) {
                        mapBlog.put("user_state", 2);
                    } else if (Integer.parseInt(code.get("ID").toString()) == blogList.get(j).getUser_id()) {
                        mapBlog.put("user_state", 1);
                    } else {
                        mapBlog.put("user_state", 3);
                    }
                    listBlog.add(mapBlog);
                }
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
