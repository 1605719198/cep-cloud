package com.jlkj.common.dto.security.token;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author zyf
 * 2022-03-28 10:52
 */
public class JwtTokenUtil {
    private static final long TOKEN_EXPERATION = 2 * 60 * 60 * 1000;
    private static final String TOKEN_SIGN_KEY = "iu&%IJnbnnjh";

    public static String createToken(Map<String, String> payload) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TOKEN_EXPERATION);
        JWT jwt = JWT.create();
        jwt.setPayload("client-data", payload);
        jwt.setPayload("timestamp", DateUtil.current());
        return jwt.setExpiresAt(expiration).setKey(TOKEN_SIGN_KEY.getBytes()).sign();
    }

    public static boolean validToken(String token) {
        return JWT.of(token).setKey(TOKEN_SIGN_KEY.getBytes()).verify();
    }

    public static boolean validTokenWithExpiration(String token) {
        return JWT.of(token).setKey(TOKEN_SIGN_KEY.getBytes()).validate(DateUtil.current());
    }

    public static JSONObject getPayloadFromToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return JSON.parseObject(jwt.getPayload("client-data").toString());
    }

    public static JSONObject getPayloadFromRequest(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        return getPayloadFromToken(token);
    }
}
