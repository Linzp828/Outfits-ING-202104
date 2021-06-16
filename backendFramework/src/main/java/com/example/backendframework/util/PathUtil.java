package com.example.backendframework.util;

public class PathUtil {
    static final String serverBlog = "http://121.5.100.116/static/blogPic/";
    static final String serverHead = "http://121.5.100.116/static/headPic/";
    static final String serverClothing = "http://121.5.100.116/static/clothingPic/";


    public static String getBlogPath(){
        return serverBlog;
    }

    public static String getHeadPath(){
        return serverHead;
    }

    public static String getClothingPath(){
        return serverClothing;
    }

    public static String getFilePath(String path,String fileName){
        return path + fileName;
    }
}
