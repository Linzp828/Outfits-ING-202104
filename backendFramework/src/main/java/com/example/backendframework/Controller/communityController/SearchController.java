package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.login_registerDao.UserDao;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.Model.User;
import com.example.backendframework.util.StateUtil;
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

    @Autowired
    private UserDao userDao;

    Map<String, Object> map = new HashMap<String, Object>();
    static final String serverBlog = "http://121.5.100.116/static/blogPic/";
    static final String serverHead = "http://121.5.100.116/static/headPic/";

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public JSON blogSearch(@RequestBody JSONObject request,@RequestHeader(value = "token") String token){
        List<Map<String,Object>> listBlog = new ArrayList<>();
        List<Map<String,Object>> listBlog1 = new ArrayList<>();
        //String token = request.getString("token");
        String keyword = request.getString("keyword");
        try {
            //判断token是否正常
            Map<String, Object> code = TokenUtil.parseJWT(token);

            //搜article
            List<Blog> blogList1 = blogDao.articleSearch(keyword);
            for (int i=0;i<blogList1.size();i++){
                Map<String, Object> mapBlog1 = new HashMap<String, Object>();

                int num = blogDao.userBlogFind(Integer.parseInt(code.get("ID").toString()),blogList1.get(i).getId());
                if(num!=0){
                    mapBlog1.put("favorite",1);
                }else{
                    mapBlog1.put("favorite",0);
                }

                User user1= userDao.getIntro(blogList1.get(i).getUser_id());
                mapBlog1.put("userId",blogList1.get(i).getUser_id());
                mapBlog1.put("user_pic",serverHead+user1.getUser_pic_path());
                mapBlog1.put("user_nickname",user1.getUser_nickname());


                int t1 = userDao.userIdGetSubscribe(Integer.parseInt(code.get("ID").toString()),blogList1.get(i).getUser_id());
                if(t1==1){
                    mapBlog1.put("user_state",2);
                }else if (Integer.parseInt(code.get("ID").toString())==blogList1.get(i).getUser_id()){
                    mapBlog1.put("user_state",1);
                }else{
                    mapBlog1.put("user_state",3);
                }

                mapBlog1.put("blog_released_time",blogList1.get(i).getBlog_released_time());
                mapBlog1.put("blogId",blogList1.get(i).getId());
                mapBlog1.put("blogTitle",blogList1.get(i).getBlog_title());
                mapBlog1.put("blogPic",serverBlog+blogList1.get(i).getBlog_pic_path());
                mapBlog1.put("userId",blogList1.get(i).getUser_id());
                listBlog.add(mapBlog1);
            }

            //搜title
            List<Blog> blogList2 = blogDao.titleSearch(keyword);
            for (int i=0;i<blogList2.size();i++){
                Map<String, Object> mapBlog2 = new HashMap<String, Object>();

                int num = blogDao.userBlogFind(Integer.parseInt(code.get("ID").toString()),blogList2.get(i).getId());
                if(num!=0){
                    mapBlog2.put("favorite",1);
                }else{
                    mapBlog2.put("favorite",0);
                }

                User user1= userDao.getIntro(blogList2.get(i).getUser_id());
                mapBlog2.put("userId",blogList2.get(i).getUser_id());
                mapBlog2.put("user_pic",serverHead+user1.getUser_pic_path());
                mapBlog2.put("user_nickname",user1.getUser_nickname());


                int t1 = userDao.userIdGetSubscribe(Integer.parseInt(code.get("ID").toString()),blogList2.get(i).getUser_id());
                if(t1==1){
                    mapBlog2.put("user_state",2);
                }else if (Integer.parseInt(code.get("ID").toString())==blogList2.get(i).getUser_id()){
                    mapBlog2.put("user_state",1);
                }else{
                    mapBlog2.put("user_state",3);
                }

                mapBlog2.put("blog_released_time",blogList2.get(i).getBlog_released_time());
                mapBlog2.put("blogId",blogList2.get(i).getId());
                mapBlog2.put("blogTitle",blogList2.get(i).getBlog_title());
                mapBlog2.put("blogPic",serverBlog+blogList2.get(i).getBlog_pic_path());
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

            map.put("code",StateUtil.SC_OK);
            map.put("msg","操作成功");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }catch (ExpiredJwtException e) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误1");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (SignatureException e1) {
            map.put("code",StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误2");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code",StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg","token错误3");
            map.put("data",listBlog1);
            JSONObject jsonp= new JSONObject(map);
            return jsonp;
        }
    }
}
