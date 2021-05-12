package com.example.backendframework.Controller.wardrobeController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.wardrobeDao.WardrobeDao;
import com.example.backendframework.Model.Clothing;
import com.example.backendframework.Model.MatchClothing;
import com.example.backendframework.Model.Subtype;
import com.example.backendframework.Model.Type;
import com.example.backendframework.util.PictureUrlUtil;
import com.example.backendframework.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/wardrobe")
public class WardrobeController {
    @Autowired
    private WardrobeDao wardrobeDao;

    static final String clothingPath = "static/clothingPic";
    static final String server = "121.5.100.116/static/clothingPic/";

    /**
     * 获取某类别下的衣物
     * @param request
     * @return
     */
    @RequestMapping("/getClothing")
    public JSONObject getClothing(@RequestBody JSONObject request,@RequestHeader(value = "token")String token){
        JSONObject response = new JSONObject();
        JSONArray subtypeArray = new JSONArray();
        int typeId = request.getInteger("typeId");

        response.put("code",200);
        response.put("msg","操作成功");
        List<Subtype> subtypeList = wardrobeDao.getSubtype(typeId);

        for(int i = 0;i < subtypeList.size();i++){
            JSONObject subtypeObject = new JSONObject();
            JSONArray clothingArray = new JSONArray();
            Subtype subtype = subtypeList.get(i);
            int subtypeId = subtype.getId();

            subtypeObject.put("subtypeId",subtypeId);
            subtypeObject.put("subtypeName",subtype.getSubtype_name());

            int userId = Integer.parseInt(TokenUtil.parseJWT(token).get("ID").toString());     //将token解析为userId
            List<Clothing> clothingList = wardrobeDao.getClothing(userId,subtypeId);
            for(int j = 0;j <clothingList.size();j++){
                JSONObject clothingObject = new JSONObject();
                Clothing clothing = clothingList.get(j);
                clothingObject.put("clothingId",clothing.getId());
                clothingObject.put("clothingPic",server+clothing.getClothing_pic());
                clothingArray.add(clothingObject);
            }
            subtypeObject.put("clothing",clothingArray);

            subtypeArray.add(subtypeObject);
        }

        response.put("data",subtypeArray);
        return response;
    }

    /**
     * 导入图片
     * @param token
     * @param subtypeIdArray
     * @param files
     * @return
     */
    @RequestMapping("/importClothing")
    public JSONObject importClothing(@RequestHeader(value = "token")String token,@RequestParam("subtypeId") JSONArray subtypeIdArray,
                                     @RequestParam("clothingPic") MultipartFile files[]) {
        try{
            int userId = Integer.parseInt(TokenUtil.parseJWT(token).get("ID").toString());     //将token解析为userId

            for(int i = 0;i < files.length;i++){
                PictureUrlUtil.writePicture(files[i],clothingPath);     //将图片输出到clothingPic文件夹
                String filePath = PictureUrlUtil.getFilePath(files[i]);
                int subtypeId = subtypeIdArray.getInteger(i);
                System.out.println(filePath+"  "+userId+"  "+subtypeId);
                wardrobeDao.importPic(filePath,userId,subtypeId);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("msg","操作成功");
        response.put("data",null);
        return response;
    }


    /**
     * 获取类别静态表
     * @return
     */
    @RequestMapping("/getType")
    public JSONObject getType(){
        JSONArray typeArray = new JSONArray();

        List<Type> typeList = wardrobeDao.getType();
        for(int i = 0;i < typeList.size();i++){
            Type type = typeList.get(i);
            List<Subtype> subtypeList = wardrobeDao.getSubtype(type.getId());
            JSONArray subtypeArray = new JSONArray();
            for(int j = 0;j < subtypeList.size();j++){
                Subtype subtype = subtypeList.get(j);

                JSONObject subtypeObject = new JSONObject();
                subtypeObject.put("subtypeId",subtype.getId());
                subtypeObject.put("subtypeName",subtype.getSubtype_name());
                subtypeArray.add(subtypeObject);
            }

            JSONObject typeObject = new JSONObject();
            typeObject.put("typeId",type.getId());
            typeObject.put("typeName",type.getType_name());
            typeObject.put("subtype",subtypeArray);
            typeArray.add(typeObject);
        }

        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("msg","操作成功");
        response.put("data",typeArray);
        return response;
    }

    /**
     * 修改衣物
     * @param request
     * @return
     */
    @RequestMapping("/modifyClothing")
    public JSONObject modifyClothing(@RequestBody JSONObject request){
        int clothingId = request.getInteger("clothingId");
        int subtypeId = request.getInteger("subtypeId");
        wardrobeDao.modifyClothing(clothingId,subtypeId);
        JSONObject response = new JSONObject();
        response.put("code",200);
        response.put("msg","操作成功");
        response.put("data",null);
        return response;
    }

    /**
     * 删除衣物，搭配里有的衣物不能删除
     * @param request
     * @return
     */
    @RequestMapping("/deleteClothing")
    public JSONObject deleteClothing(@RequestBody JSONObject request){
        int clothingId = request.getInteger("clothingId");
        JSONObject response = new JSONObject();

        List<MatchClothing> matchClothingList = wardrobeDao.getMatchClothing(clothingId);
        if(matchClothingList.isEmpty()){
            wardrobeDao.deleteClothing(clothingId);
            response.put("code",200);
            response.put("msg","删除成功");
        }
        else{
            response.put("code",200);
            response.put("msg","衣物被搭配引用，删除失败");
        }

        response.put("data",null);
        return response;
    }

}
