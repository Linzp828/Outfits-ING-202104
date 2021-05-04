package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Model.Blog;
import com.example.backendframework.Dao.communityDao.BlogDao;
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
    public JSON GetDetail(@RequestBody JSONObject request){
        int blogId = request.getInteger("blogId");
        List<Blog> list=blogDao.blogIdFindBlog(blogId);
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
    }
}
