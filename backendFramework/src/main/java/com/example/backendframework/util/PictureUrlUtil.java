package com.example.backendframework.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;

public class PictureUrlUtil {

    /**
     * 将图片输出到与项目同一级的文件夹下
     * @param file   图片
     * @param fileFolderName  文件夹名称
     */
    public static void writePicture(MultipartFile file,String fileFolderName){
        try{
            String fileName =file.getOriginalFilename();    //获取图片的名称
            FileOutputStream picOutput = new FileOutputStream("/outfits/" + fileFolderName + "/" + fileName);   //设置文件路径
            picOutput.write(file.getBytes());   //获取字节流直接写入到磁盘内
            picOutput.close();
        }
        catch (Exception e){
        }
    }


    /**
     * 获取图片本地路径
     * @param multipartFile   图片
     * @param fileFolderName     文件夹名称
     * @return
     */
    public static String getFilePath(MultipartFile multipartFile,String fileFolderName){
        String realfilePath = "";
        try{
            /*File file = new File("");
            String filePath = System.getProperty("java.class.path");
            int i = file.getCanonicalPath().length() - 1;
            for(;i >= 0;i--){
                if(filePath.charAt(i) == '\\' || filePath.charAt(i) == '/')
                    break;
            }
            realfilePath = filePath.substring(0,i);*/
            realfilePath += "/";
            realfilePath += "outfits";
            realfilePath += "/";
            realfilePath += fileFolderName;
            realfilePath += "/";
            realfilePath += multipartFile.getOriginalFilename();
        }
        catch (Exception e){
        }
        return realfilePath;
    }


}
