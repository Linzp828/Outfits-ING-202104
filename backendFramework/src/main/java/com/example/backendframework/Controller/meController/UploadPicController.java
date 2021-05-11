package com.example.backendframework.Controller.meController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.meDao.ModifyInfoDao;
import com.example.backendframework.util.PictureUrlUtil;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class UploadPicController {

    @Autowired
    private ModifyInfoDao modifyInfoDao;

    static final String headPath = "headPic";
    static final String serverIP = "http://121.5.100.116";


    /**
     * @author:  林龙星
     * @date:2021-5-9 15:20
     * @description: 获取用户上传的头像图片
     * @param:  image,token
     * @return: response
     */
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public JSONObject getSubscription(@RequestParam("avatar") MultipartFile image, @RequestHeader(value = "token") String token){
        JSONObject response = new JSONObject();

        try {
            int userId = Integer.parseInt(TokenUtil.parseJWT(token).get("ID").toString());
            PictureUrlUtil.writePicture(image,headPath);
            String headPicPath = PictureUrlUtil.getFilePath(image,headPath);

            modifyInfoDao.setHeadPic(userId,headPicPath);
            response.put("code",200);
            response.put("msg","操作成功");
            response.put("data",null);
        } catch (Exception e) {
            response.put("code", "500");
            response.put("msg", "系统异常，上传图片失败");
            response.put("data",null);
            return response;
        }
        return response;
    }

}

