package com.jlkj.common.dto.aop.permission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.dto.feignclients.RolePermissionFeignClient;
import com.jlkj.common.dto.resp.Result;
import com.jlkj.common.dto.resp.ResultCode;
import com.jlkj.common.dto.security.token.JwtTokenUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.jlkj.common.dto.constant.CommonConstant.PERMISSION_KEY;
import static com.jlkj.common.dto.constant.PayloadConstant.*;

/**
 * @author zyf
 * @Description
 * @create 2022-05-30 13:34
 */
@Component
@Aspect
@Slf4j
public class PermissionAspect {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    RolePermissionFeignClient rolePermissionFeignClient;

    @Pointcut("@annotation(com.jljl.common.aop.permission.RoleAndPermission))")
    public void permissionPointCut() { };

    @Before("permissionPointCut()")
    public void doBeforeEvent(JoinPoint joinPoint) throws Exception {
        @Cleanup Jedis jedis = jedisPool.getResource();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RoleAndPermission roleAndPermission = methodSignature.getMethod().getAnnotation(RoleAndPermission.class);
        if (roleAndPermission.andRelation()) {
            if (roleAndPermission.role().isEmpty() || roleAndPermission.permission().isEmpty()) {
                throw new Exception("Permission Annotation Error: role or permission missiong when andRelation true.");
            }
        }
        String token = httpServletRequest.getHeader("token");
        if (token.isEmpty()) {
            throw new PermissionDenidedException();
        }
        if (!CLIENT_TEST.equals(token)) {
            boolean hasRole = false, hasPermission = false;
            String userId = "";
            JSONObject jsonObject = JwtTokenUtil.getPayloadFromToken(token);
            userId = jsonObject.getString(USER_ID);
            if (!jsonObject.containsKey(USER_NAME) || !jsonObject.containsKey(USER_ID)) {
                throw new PermissionDenidedException();
            }
            Map<String, Object> params = new HashMap<>(1);
            params.put("user_id", userId);
            Result res = rolePermissionFeignClient.getRoleAndPermission(params, token);
            if (res.getCode().equals(ResultCode.SUCCESS.getCode())) {
                JSONArray rolepermissions = JSONArray.parseArray(JSON.toJSONString(res.getData()));
                if (rolepermissions.size() > 0) {
                    for (Object rolepermission : rolepermissions) {
                        JSONObject role = (JSONObject) rolepermission;
                        if (role.containsKey("role_name") && role.getString("role_name").equals(roleAndPermission.role())) {
                            hasRole = true;
                        }
                        for (Object o : role.getJSONArray(PERMISSION_KEY)) {
                            JSONObject permission = (JSONObject) o;
                            if (permission.containsKey("url") && permission.getString("url").equals(roleAndPermission.permission())) {
                                hasPermission = true;
                            }
                        }
                    }
                    if (!roleAndPermission.role().isEmpty() && !hasRole) {
                        throw new PermissionDenidedException();
                    }
                    if (!roleAndPermission.permission().isEmpty() && !hasPermission) {
                        throw new PermissionDenidedException();
                    }
                } else {
                    throw new PermissionDenidedException();
                }
            } else {
                throw new PermissionDenidedException();
            }
        }
    }
}
