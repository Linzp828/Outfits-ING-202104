/**
 * projectName: BackendFrame
 * fileName: matting.java
 * packageName: com.example.backendframework.util
 * date: 2021-05-10 22:14
 * copyright(c) 2020-2021 outfits
 */
package com.example.backendframework.util;

import org.apache.tomcat.jni.Proc;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.python.core.PyFunction;
import org.python.core.PyObject;

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
public class matting {
    public static void main(String[] args) {
        Process proc;
        try {
            String[] args1 = new String[] {"python","E:\\DesktopFolder\\作业\\大三下\\软工\\α\\matting1.0\\test2\\main.py","E:\\GitHub Desktop\\Outfits-ING-202104\\backendFramework\\src\\main\\img\\2.jpg"};
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

