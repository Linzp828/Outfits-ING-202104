/**
 * projectName: BackendFrame
 * fileName: TransUtil.java
 * packageName: com.example.backendframework.util
 * date: 2021-06-16 21:35
 * copyright(c) 2020-2021 outfits
 */

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @version: V1.0
 * @author: outfits
 * @className: TransUtil
 * @packageName: com.example.backendframework.util
 * @description: 前端返回城市名转拼音脚本
 * @data: 2021-06-16 21:35
 **/
public class TransUtil {
    //    public static void main(String[] args) {
    public static String getLocation(String location) {
        Process proc;
        try {
            String pyAddr = "E:/GitHub Desktop/Outfits-ING-202104/Python/test2/transfomer.py";
//            String location = "福州市";
            String[] args1 = new  String[]{"python",pyAddr,location};
            proc = Runtime.getRuntime().exec(args1);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
//                System.out.println(line);
                break;
            }
            in.close();
            proc.waitFor();
            return line;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
//        return line;
    }
    //
    public static void main(String[] args) {
        String city = getLocation("福州市");
        System.out.println(city);
    }
}