package com.jlkj.common.dto.aop.syslog;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.dto.security.token.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.jlkj.common.dto.aop.syslog.SysLogConstant.SYS_LOG_PARAM_KEY;
import static com.jlkj.common.dto.constant.PayloadConstant.*;
import static com.jlkj.common.dto.constant.RabbitConstant.SYSLOG_EXCHANGE;
import static com.jlkj.common.dto.constant.RabbitConstant.SYSLOG_ROUTE_KEY;

/**
 * @author zyf
 * 2022-04-11 10:01
 */
@Component
@Aspect
@Slf4j
public class SysLogAspect {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Pointcut("@annotation(com.jljl.common.aop.syslog.SystemLog))")
    public void logPointCut() {
    }

    @After("logPointCut()")
    public void doAfterEvent(JoinPoint joinPoint) {
        try {
            SysLogs sysLog = doLog(joinPoint);
            log.info("syslog: " + sysLog.toString());
            if (null != sysLog.getContent()) {
                rabbitTemplate.convertAndSend(SYSLOG_EXCHANGE, SYSLOG_ROUTE_KEY, sysLog);
            }
        } catch (Exception e) {
            log.info("syslogError");
        }
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterEventException(JoinPoint joinPoint, Exception e) {
        try {
            SysLogs sysLog = doLog(joinPoint);
            log.info("syslog: " + sysLog.toString());
            sysLog.setType(SysLogTypeEnum.TYPE_EXCEPTION.getType());
            sysLog.setContent(sysLog.getContent() + "\n" + e.getMessage());
            rabbitTemplate.convertAndSend(SYSLOG_EXCHANGE, SYSLOG_ROUTE_KEY, sysLog);
        } catch (Exception ex) {
            log.info("syslogError");
        }
    }

    public SysLogs doLog(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getDeclaringTypeName() + "" + methodSignature.getName();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        String userName = "";
        String userId = "5eb19e1c-ab56-43d4-ad75-08af912a911d";
        if (!httpServletRequest.getRequestURI().contains(PATH_LOGIN)) {
            String token = httpServletRequest.getHeader(TOKEN_HEADER);
            if (CLIENT_TEST.equals(token)) {
                userName = "system";
            } else if (token.contains(FAKE_TOKEN_PREFIX)) {
                userName = token.replace(FAKE_TOKEN_PREFIX, "");
            } else {
                JSONObject jsonObject = JwtTokenUtil.getPayloadFromToken(httpServletRequest.getHeader("token"));
                userName = jsonObject.getString(USER_NAME);
                userId = jsonObject.getString(USER_ID);
            }
        }

        SysLogs sysLog = new SysLogs();
        sysLog.setId(IdUtil.randomUUID());
        sysLog.setCreateTime(new Date());
        sysLog.setType(systemLog.type().getType());
        sysLog.setCreateUserId(userId);
        sysLog.setCreateUserName(userName);
        sysLog.setUri(httpServletRequest.getRequestURI());

        String contentType = httpServletRequest.getContentType();
        if (null != contentType) {
            if (contentType.contains(MediaType.APPLICATION_JSON.getSubtype())) {
                sysLog.setContent(httpServletRequest.getAttribute(SYS_LOG_PARAM_KEY) == null ? "" :
                        JSONObject.toJSON(httpServletRequest.getAttribute(SYS_LOG_PARAM_KEY)).toString());
            }
            if (contentType.contains(MediaType.APPLICATION_FORM_URLENCODED.getSubtype()) ||
                    contentType.contains(MediaType.MULTIPART_FORM_DATA.getSubtype())) {
                sysLog.setContent(getParam(httpServletRequest));
            }
        }
        else {
            if (null != httpServletRequest.getAttribute(SYS_LOG_PARAM_KEY)) {
                String paramStr = getParam(httpServletRequest);
                sysLog.setContent(paramStr);
            }
        }
        return sysLog;
    }

    private String getParam(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>(1);
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            map.put(paramName, paramValues[0]);
        }
        return map.toString();
    }
}
