/**
 * projectName: BackendFrame
 * fileName: matting.java
 * packageName: com.example.backendframework.util
 * date: 2021-05-10 22:14
 * copyright(c) 2020-2021 outfits
 */
package com.example.backendframework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @version: V1.0
 * @author: outfits
 * @className: matting
 * @packageName: com.example.backendframework.util
 * @description:
 * @data: 2021-05-10 22:14
 **/
public class MattingUtil {
    public static void getImage(String imgUrl) {
        Process proc;
        try {
            String pyAddr = "E:\\DesktopFolder\\作业\\大三下\\软工\\α\\matting1.0\\test2\\main.py";
            String[] args1 = new String[] {"python",pyAddr,imgUrl};
            proc = Runtime.getRuntime().exec(args1);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

