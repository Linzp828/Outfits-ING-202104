package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.PathUtil;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/blog")
public class GetDetailController {
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private UserDao userDao;

    static final String serverBlog = "http://121.5.100.116/static/blogPic/";
    static final String serverHead = "http://121.5.100.116/static/headPic/";

    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> mapData = new HashMap<String, Object>();

    /**
     * 获取博客详情
     *
     * @param request
     * @param token
     * @return
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public JSON GetDetail(@RequestBody JSONObject request, @RequestHeader(value = "token") String token) {
        //String token = request.getString("token");

        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);

            int blogId = request.getInteger("blogId");
            List<Blog> list = blogDao.blogIdFindBlog(blogId);

            int num = blogDao.userBlogFind(Integer.parseInt(code.get("ID").toString()), blogId);
            if (num == 1) {
                mapData.put("favorite", 1);
            } else {
                mapData.put("favorite", 0);
            }
            User user1 = userDao.getIntro(list.get(0).getUser_id());
            mapData.put("userId", list.get(0).getUser_id());
            mapData.put("user_pic", PathUtil.getHeadPath(user1.getUser_pic_path()));
            mapData.put("user_nickname", user1.getUser_nickname());


            int t1 = userDao.userIdGetSubscribe(Integer.parseInt(code.get("ID").toString()), list.get(0).getUser_id());
            if (t1 == 1) {
                mapData.put("user_state", 2);
            } else if (Integer.parseInt(code.get("ID").toString()) == list.get(0).getUser_id()) {
                mapData.put("user_state", 1);
            } else {
                mapData.put("user_state", 3);
            }
            mapData.put("blogId", blogId);
            mapData.put("title", list.get(0).getBlog_title());
            mapData.put("article", list.get(0).getBlog_article());
            mapData.put("picture", PathUtil.getBlogPath(list.get(0).getBlog_pic_path()));
            mapData.put("time", list.get(0).getBlog_released_time());
            mapData.put("userId", list.get(0).getUser_id());
            map.put("code", StateUtil.SC_OK);
            map.put("msg", "操作成功");
            map.put("data", mapData);
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (ExpiredJwtException e) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误1");
            map.put("data", "");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误2");
            map.put("data", " ");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误3");
            map.put("data", " ");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (IndexOutOfBoundsException e3) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "不存在这个博客");
            map.put("data", " ");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        }


    }
}
