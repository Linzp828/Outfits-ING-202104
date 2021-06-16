package com.example.backendframework.util;

import java.util.Date;
import java.text.SimpleDateFormat;

public class PathUtil {
    static final String serverBlog = "http://121.5.100.116/static/blogPic/";
    static final String serverHead = "http://121.5.100.116/static/headPic/";
    static final String serverClothing = "http://121.5.100.116/static/clothingPic/";

    /**
     * @author: LinZipeng
     * @description: 返回处理后的完整服务器绝对路径
     * @params: filePath（图片名），userId（该图片的上传者）
     * @return: String
     */
    public static String getBlogPath(String filePath, int userId) {
        return serverBlog + getDate() + Integer.toString(userId) + filePath;

    }

    /**
     * @author: LinZipeng
     * @description: 返回处理后的完整服务器绝对路径
     * @params: filePath（图片名），userId（该图片的上传者）
     * @return: String
     */
    public static String getHeadPath(String filePath, int userId) {
        return serverHead + getDate() + Integer.toString(userId) + filePath;
    }

    /**
     * @author: LinZipeng
     * @description: 返回处理后的完整服务器绝对路径
     * @params: filePath（图片名），userId（该图片的上传者）
     * @return: String
     */
    public static String getClothingPath(String filePath, int userId) {
        return serverClothing + getDate() + Integer.toString(userId) + filePath;
    }

    /**
     * @author: LinZipeng
     * @description: 获取系统时间，以字符串形式返回
     * @params: null
     * @return: String
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getDate());
    }
}
