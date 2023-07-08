package com.jlkj.human.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.api.factory.RemoteHdApiFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @description: 人事模块远程调用
 * @Author: 266861
 * @date: 2023/7/4 13:57
 */
@FeignClient(contextId = "remoteHdApiService", value = ServiceNameConstants.HUMAN_SERVICE, fallbackFactory = RemoteHdApiFactory.class)
public interface RemoteHdApiService {

    /**
     * 年底定时排班
     * @return 定时排班结果
     * @param source 请求来源
     * @author 266861
     * @date 2023/7/4 9:40
     **/
    @GetMapping("/arrangeClassMaster/scheduledShifts")
    AjaxResult scheduledShifts(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}

