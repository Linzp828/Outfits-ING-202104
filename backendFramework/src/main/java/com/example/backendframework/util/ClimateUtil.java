package com.example.backendframework.util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;

import static java.lang.Math.*;

/**
 * @version: V1.0
 * @author: qzl
 * @className: climateCal
 * @packageName: PACKAGE_NAME
 * @description: 温度推荐算法
 * @data: 2021-05-02 17:57
 **/

public class ClimateUtil {
//    天气信息
    private static String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

//    生活指数
//    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/life/suggestion.json";

//    详细信息
//    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/pro/weather/grid/daily.json";

    private static String TIANQI_API_SECRET_KEY = "SZrzG9EI4E18kBmi5"; //

    private static String TIANQI_API_USER_ID = "PHTfjd8ncw5SppqMH"; //

    /**
     * Generate HmacSHA1 signature with given data string and key
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private static String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /**
     * Generate the URL to get diary weather
     * @param location
     * @param language
     * @param unit
     * @param start
     * @param days
     * @return
     */
    private static String generateGetDiaryWeatherURL(String location,String language,String unit,String start,String days) throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=1800&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }

    /**
     * 将API获取到的URL转化为JSON格式信息
     * Generate the URL to get diary weather
     * @return  json
     */
    private static String loadJson (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 由获取到的JSON信息计算当日穿衣等级
     * Generate the URL to get diary weather
     * @return  json
     */
    private static int levelCal(String url) {
        //获取json
        String jsonStr = loadJson(url);
        JSONObject json = JSONObject.parseObject(jsonStr);
        JSONArray jsonArray = json.getJSONArray("results");

        System.out.println(json);

        JSONArray daily=jsonArray.getJSONObject(0).getJSONArray("daily");
        JSONObject dailyObject = daily.getJSONObject(0);

        //计算穿衣等级
        double high = Double.parseDouble(dailyObject.getString("high"));
        double low = Double.parseDouble(dailyObject.getString("low"));
        double humidity = Double.parseDouble(dailyObject.getString("humidity"));
        double relaHumidity = 50.0;
        double windSpeed = Double.parseDouble(dailyObject.getString("wind_speed"));
        double airTemp = (high + low) / 2;
        int month = 5;
        double latitude = 26.25;

        double sensTemp;
        double radians1 = Math.toRadians( latitude - 23.5);
        double radians2 = Math.toRadians( 15 * ( month - 1 ) );
        double comforTemp = 22.7 * ( 1.0 - 0.3 * sin ( radians1 )) - abs( 0.3 * cos ( radians2 ) );

        if ( airTemp >= comforTemp ){
//            System.out.println(1);
            double C1 = 1, C2 = 0.05, C3 = -1, C4 = -0.03;  //论文参数，合理
            sensTemp = airTemp + 14 * C1 * ( exp ( C2 * ( airTemp - comforTemp ) * ( humidity - relaHumidity ) / 100 ) + C3 )                       + C4 * ( airTemp - comforTemp) * windSpeed;
        }
        else {
//            System.out.println(2);
            double C1 = 1, C2 = -0.05, C3 = -0.6, C4 = 0.01;    //初步微调结果
//            double C1 = -1, C2 = -0.013, C3 = 1, C4 = 0.01;   //论文参数，存在问题
            sensTemp = airTemp + 14 * C1 * ( exp ( C2 * ( airTemp - comforTemp ) * ( humidity - relaHumidity ) / 100.0 ) + C3                       ) + C4 * ( airTemp - comforTemp) * windSpeed;
        }

        double deviation = 22.7 - comforTemp;
        System.out.println("deviation:"+ deviation);
        System.out.println("sensTemp:" + sensTemp);

        int level;
        if (sensTemp > 32 - deviation )
            level = 4;
        else if ( sensTemp > 29 - deviation && sensTemp <= 32 - deviation )
            level = 3;
        else if ( sensTemp > 25 - deviation && sensTemp <= 29 - deviation )
            level = 2;
        else if ( sensTemp > 23 - deviation && sensTemp <= 25 - deviation )
            level = 1;
        else if ( sensTemp > 18 - deviation && sensTemp <= 23 - deviation )
            level = 0;
        else if ( sensTemp > 13 - deviation && sensTemp <= 18 - deviation )
            level = -1;
        else if ( sensTemp > 6 - deviation && sensTemp <= 13 - deviation )
            level = -2;
        else if ( sensTemp > -2 - deviation && sensTemp <= 6 - deviation )
            level = -3;
        else if ( sensTemp > -10 - deviation && sensTemp <= -2 - deviation )
            level = -4;
        else if ( sensTemp > -20 - deviation && sensTemp <= -10 - deviation )
            level = -5;
        else
            level = -6;
        return level;
    }

    public static int getLevel(){
        ClimateUtil climateUtil =new ClimateUtil();
        int level=0;
        try {
            String url = climateUtil.generateGetDiaryWeatherURL(
                    "fuzhou",
                    "zh-Hans",
                    "c",
                    "1",
                    "1"
            );
            level = levelCal(url);

            System.out.println("level:" + level);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }finally {
            return level;
        }
    }
}
