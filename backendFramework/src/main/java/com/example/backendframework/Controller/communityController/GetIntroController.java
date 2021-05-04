package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Dao.communityDao.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/blog")
public class GetIntroController {
    @Autowired
    private BlogDao blogDao;
    Map<String, Object> map = new HashMap<String, Object>();


    @RequestMapping(value = "/getIntro", method = RequestMethod.POST)
    public JSON GetIntro(@RequestBody JSONObject request){
        List<Map<String,Object>> listBlog = new ArrayList<>();
        //获取数组
        //String[]  blogIdList =request.getParameterValues("blogIdArray");
        String blogIdList = request.getString("blogIdArray");
        JSONArray blogIdArray= JSONArray.parseArray(blogIdList);
        for(int i=0;i<blogIdArray.size();i++){
            //System.out.println(blogIdArray.get(i));
            //object先转换成string才能转换成int
            List<Blog> list=blogDao.blogIdFindBlog(Integer.parseInt(blogIdArray.get(i).toString()));
            //在循环内定义，不然会因为map的特性导致存的五次都变成同一个
            Map<String, Object> mapBlog = new HashMap<String, Object>();
            mapBlog.put("blogId",i+1);
            mapBlog.put("blogTitle",list.get(0).getBlog_title());
            mapBlog.put("blogPic",list.get(0).getBlog_pic_path());
            mapBlog.put("userId",list.get(0).getUser_id());
            listBlog.add(mapBlog);
        }
        map.put("code",200);
        map.put("msg","操作成功");
        map.put("data",listBlog);
        JSONObject jsonp= new JSONObject(map);
        return jsonp;
    }
}
