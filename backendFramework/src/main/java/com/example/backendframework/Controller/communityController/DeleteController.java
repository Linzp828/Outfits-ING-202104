package com.example.backendframework.Controller.communityController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.communityDao.BlogDao;
import com.example.backendframework.util.StateUtil;
import com.example.backendframework.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/blog")
public class DeleteController {
    @Autowired
    private BlogDao blogDao;
    Map<String, Object> map = new HashMap<String, Object>();

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    /**
     * 删除博客
     *
     * @param request
     * @param token
     * @return
     */
    public JSON DeleteBlog(@RequestBody JSONObject request, @RequestHeader(value = "token") String token) {
        //String token = request.getString("token");
        int blogId = request.getInteger("blogId");
        try {
            Map<String, Object> code = TokenUtil.parseJWT(token);
            int num = blogDao.blogDelete(Integer.parseInt(code.get("ID").toString()), blogId);
            if (num == 1) {
                map.put("code", StateUtil.SC_OK);
                map.put("msg", "删除成功");
                map.put("data", "");
                JSONObject jsonp = new JSONObject(map);
                return jsonp;
            }
            map.put("code", StateUtil.SC_MULTIPLE_CHOICES);
            map.put("msg", "删除失败");
            map.put("data", "");
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
            map.put("data", "");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        } catch (MalformedJwtException e2) {
            map.put("code", StateUtil.SC_NOT_ACCEPTABLE);
            map.put("msg", "token错误3");
            map.put("data", "");
            JSONObject jsonp = new JSONObject(map);
            return jsonp;
        }
    }
}
