package com.example.backendframework.util;

public class PathUtil {
    static final String serverBlog = "http://121.5.100.116/static/blogPic/";
    static final String serverHead = "http://121.5.100.116/static/headPic/";
    static final String serverClothing = "http://121.5.100.116/static/clothingPic/";
    static final String blogPath = "static/blogPic";
    static final String headPath = "static/headPic";
    static final String clothingPath = "static/clothingPic";

    public static String getServerBlog(){
        return serverBlog;
    }

    public static String getServerHead(){
        return serverHead;
    }

    public static String getServerClothing(){
        return serverClothing;
    }

    public static String getBlogPath(){
        return blogPath;
    }

    public static String getHeadPath(){
        return headPath;
    }

    public static String getClothingPath(){
        return clothingPath;
    }
}
