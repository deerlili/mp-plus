package com.deerlili.mp.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JwtTokenUtils {

    // token的头部
    public static final String TOKEN_HEADER = "Authorization";
    // token的前缀
    public static final String TOKEN_PREFIX = "Bearer ";
    // 用户密钥
    private static final String SECRET = "deerlili_admin";
    // JWT的签发者
    private static final String ISS = "admin";
    // 角色的key
    private static final String ROLE_CLAIMS = "rol";
    // 过期时间是3600秒，既是24个小时
    private static final long EXPIRATION = 86400L;
    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 7 * EXPIRATION;

    // 创建token
    public static String createToken(Long id, String username, String role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map) // 角色的授权
                .setIssuer(ISS) // JWT的签发者
                .setSubject(id + "," + username) // JWT所面向的用户
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 过期时间
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        List<String> userInfo = Arrays.asList(getTokenBody(token).getSubject().split(","));
        return userInfo.get(1);
    }

    // 从token中获取用户名
    public static Integer getUserId(String token) {
        String s = JSON.toJSONString(getTokenBody(token).getSubject());
        List<String> userInfo = Arrays.asList(getTokenBody(token).getSubject().split(","));
        return Integer.parseInt(userInfo.get(0));
    }

    // 获取用户角色
    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }

    // 是否已过期
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}

