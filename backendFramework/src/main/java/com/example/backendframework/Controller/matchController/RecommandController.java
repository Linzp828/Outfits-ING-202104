package com.example.backendframework.Controller.matchController;

import com.alibaba.fastjson.JSONObject;
import com.example.backendframework.Dao.wardrobeDao.WardrobeDao;
import com.example.backendframework.Model.Clothing;

import com.example.backendframework.util.ClimateUtil;
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


@RestController
@RequestMapping("/match")
public class RecommandController {
    @Autowired
    private WardrobeDao wardrobeDao;
    Map<String, Object> map = new HashMap<String, Object>();
    static final String server = "http://121.5.100.116/static/clothingPic/";

    @RequestMapping(value = "/recommand", method = RequestMethod.POST)
    public JSONObject RecommandMatch(@RequestBody JSONObject obj, @RequestHeader(value = "token") String token) {
        Map<String, Object> code;
        try {
            code = TokenUtil.parseJWT(token);
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
        int userId = Integer.parseInt(code.get("ID").toString());
        int offset = obj.getInteger("offset");
        String location=obj.getString("location");
        List<Map<String,Object>> data=new ArrayList<>();

        //根据用户反馈和天气接口的穿衣等级，筛选出符合的衣物类别
        List<Clothing> clothingList= wardrobeDao.listLevelClothing(userId,ClimateUtil.getLevel(location)-offset);
//        List<Clothing> clothingList= wardrobeDao.listLevelClothing(userId,3+offset);
        Boolean upFlag,downFlag;
        Map<String,Object> upMap=new HashMap<String,Object>();
        Map<String,Object> downMap=new HashMap<String,Object>();
        System.out.println("筛选衣物成功");
        upFlag=downFlag=false;
        for(Clothing clothing:clothingList){
            //挑选完毕
            int typeId= wardrobeDao.getTypeId(clothing.getSubtype_id());
            String subtypeName=wardrobeDao.getSubtypeName(clothing.getSubtype_id());
//            System.out.println(clothing.getId()+ " "+subtypeId);
            if(upFlag && downFlag){
                break;
            }
            //挑选一件上装
            if(!upFlag && typeId==1){
                upMap.put("clothingId",clothing.getId());
                upMap.put("clothingPic",server+clothing.getClothing_pic());
                upMap.put("subtypeName",subtypeName);
                upMap.put("typeName",wardrobeDao.getTypeName(typeId));
                data.add(upMap);
                upFlag=true;
            }
            //挑选一件下装
            if(!downFlag && typeId==2){
                downMap.put("clothingId",clothing.getId());
                downMap.put("clothingPic",server+clothing.getClothing_pic());
                downMap.put("subtypeName",subtypeName);
                downMap.put("typeName",wardrobeDao.getTypeName(typeId));
                data.add(downMap);
                downFlag=true;
            }
        }

        String msg="操作成功";
        if(!upFlag){
            msg+=" 缺少上装";
        }
        if(!downFlag){
            msg+=" 缺少下装";
        }

        map.put("code", StateUtil.SC_OK);
        map.put("msg", msg);
        map.put("data", data);
        JSONObject jsonp = new JSONObject(map);
        return jsonp;
    }
}
