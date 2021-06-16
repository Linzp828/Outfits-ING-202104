package com.example.backendframework.util;
import java.util.Date;
import java.text.SimpleDateFormat;
public class PathUtil {
    static final String serverBlog = "http://121.5.100.116/static/blogPic/";
    static final String serverHead = "http://121.5.100.116/static/headPic/";
    static final String serverClothing = "http://121.5.100.116/static/clothingPic/";


    public static String getBlogPath(String filePath)
    {
        return serverBlog+filePath;
    }

    public static String getHeadPath(String filePath){
        return serverHead+filePath;
    }

    public static String getClothingPath(String filePath){
        return serverClothing+filePath;
    }

    public static String getDate(){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
    * @author:  LinZipeng
    * @description: 解决图片重命名问题
    * @params: filePath，userId
    * @return: String
    */
    public String poccessPath(String filePath,int userId){
        return Integer.toString(userId)+getDate()+filePath;
    }
}
