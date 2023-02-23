package com.jlkj.common.dto.tools.user;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

import static com.jlkj.common.dto.constant.PayloadConstant.*;
import static com.jlkj.common.dto.constant.PayloadConstant.JOB_NUMBER;
import static com.jlkj.common.dto.security.token.JwtTokenUtil.getPayloadFromRequest;

/**
 * @author zyf
 * @Description 后台通过HttpServletRequest获取token解密获取保存的用户信息
 * @create 2022-07-04 16:54
 */
public class UserInfoKit {
    public static String getUserId(HttpServletRequest httpServletRequest) throws Exception {
        JSONObject userInfo = getPayloadFromRequest(httpServletRequest);
        if (!userInfo.containsKey(USER_ID) || "".equals(userInfo.getString(USER_ID))) {
            throw new BadUserInfoException();
        }
        return userInfo.getString(USER_ID);
    }

    public static String getUserName(HttpServletRequest httpServletRequest) throws Exception {
        JSONObject userInfo = getPayloadFromRequest(httpServletRequest);
        if (!userInfo.containsKey(USER_NAME) || "".equals(userInfo.getString(USER_NAME))) {
            throw new BadUserInfoException();
        }
        return userInfo.getString(USER_NAME);
    }

    public static String getUserJobNumber(HttpServletRequest httpServletRequest) throws Exception {
        JSONObject userInfo = getPayloadFromRequest(httpServletRequest);
        if (!userInfo.containsKey(JOB_NUMBER) || "".equals(userInfo.getString(JOB_NUMBER))) {
            throw new BadUserInfoException();
        }
        return userInfo.getString(JOB_NUMBER);
    }
}
