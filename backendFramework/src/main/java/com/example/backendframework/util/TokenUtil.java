package com.example.backendframework.util;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    public  static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        //id，issuer，subject，ttlMillis都是放在payload中的，可根据自己的需要修改
        //签名的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //当前的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        System.out.println(now);

        //签名算法的秘钥，解析token时的秘钥需要和此时的一样
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("miyao");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //构造
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        System.out.println("---token生成---");
        //给token设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            System.out.println("过期时间：" + exp);
            builder.setExpiration(exp);
        }
        //压缩
        return builder.compact();
    }

    public  static  Map<String, Object> parseJWT(String jwt) {
        Map<String, Object> map = new HashMap<String, Object>();
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("miyao")).parseClaimsJws(jwt).getBody();
        System.out.println("------解析token----");
        map.put("ID",claims.getId());
        map.put("Subject",claims.getSubject());
        map.put("Issuer",claims.getIssuer());
        map.put("IssuerAt",claims.getIssuedAt());
        map.put("Expiration",claims.getExpiration());

        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());//发行人
        System.out.println("IssuerAt:   " + claims.getIssuedAt());//前时间
        System.out.println("Expiration: " + claims.getExpiration());
        try {
            /*
            检验token是或否即将过期，如快要过期，就提前更新token。如果已经过期的，会抛出ExpiredJwtException 的异常

            */
            Long exp = claims.getExpiration().getTime(); //过期的时间
            long nowMillis = System.currentTimeMillis();//现在的时间
            Date now = new Date(nowMillis);
            System.out.println("currentTime:" + now);
            long seconds = exp - nowMillis;//剩余的时间 ，若剩余的时间小与48小时，就返回一个新的token给APP
            long days = seconds / (1000 * 60 * 60 * 24);
            long hour = (seconds - days * 1000 * 60 * 60 * 24) / 3600000;
            long minutes = (seconds - days * 1000 * 60 * 60 * 24 - hour * 3600000) / 60000;
            long remainingSeconds = seconds % 60;
            System.out.println(seconds + " seconds is " + days + " days " + hour + " hours " + minutes + " minutes and " + remainingSeconds + " seconds");
            if (seconds <= 1000 * 60 * 60 * 48) {
                System.out.println("token的有效期小与48小时，请更新token！");
                map.put("code","update");
                return map;
            }
            map.put("code","success");
            return map;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            map.put("code",ExpiredJwtException.class.getName());
            return map;
        } catch (SignatureException e1) {
            e1.printStackTrace();
            map.put("code",SignatureException.class.getName());
            return map;
        } catch (MalformedJwtException e2) {
            e2.printStackTrace();
            map.put("code",MalformedJwtException.class.getName());
            return map;
        }

    }
}
