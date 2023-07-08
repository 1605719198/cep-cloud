package com.jlkj.human.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.api.factory.RemoteHsApiFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @description: 人事模块远程调用
 * @Author: 266861
 * @date: 2023/7/4 13:57
 */
@FeignClient(contextId = "remoteHsApiService", value = ServiceNameConstants.HUMAN_SERVICE, fallbackFactory = RemoteHsApiFactory.class)
public interface RemoteHsApiService {
    /**
     * 社保核定基数年底核定下年初最新数据
     * @return 新增数据结果
     * @param source 请求来源
     * @author 266861
     * @date 2023/7/4 9:40
     **/
    @GetMapping("/socialSecurityBasis/setNewYearData")
    AjaxResult setNewYearSocialSecurity(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}

