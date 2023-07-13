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

    /**
     * 月度存班
     * @param source 请求来源
     * @return 定时排班结果
     * @author 266861
     * @date 2023/7/4 9:40
     **/
    @GetMapping("/saveTime/scheduledZeroing")
    public AjaxResult scheduledZeroing(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 月初出勤汇总
     *
     * @param source 请求来源
     * @return 出勤汇总结果
     * @author 266861
     * @Date 2023/7/12 13:34
     **/
    @GetMapping("/attendanceGather/summary")
    public AjaxResult attendanceSummary(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
