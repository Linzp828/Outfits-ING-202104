package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.util.PictureUrlUtil;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/blog")
public class PostBlogController {
    @Autowired
    private BlogDao blogDao;

    static final String blogPath = "blogPic";

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public JSONObject postBlog(@RequestHeader("token") String token,
                               @RequestParam("blogArticle") String blogArticle,
                               @RequestParam("blogTitle") String blogTitle,
                               @RequestParam("blogPic") MultipartFile file){
        int userId = Integer.parseInt(TokenUtil.parseJWT(token).get("ID").toString());     //将token解析为userId
        String blogPicPath = PictureUrlUtil.getFilePath(file,blogPath);
        PictureUrlUtil.writePicture(file,blogPath);

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        String releasedTime = sdf.format(new Date());

        blogDao.postBlog(blogArticle,blogPicPath,blogTitle,releasedTime,userId);
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("msg","操作成功");
        response.put("data",null);
        return response;
    }
}
