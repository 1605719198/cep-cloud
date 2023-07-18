package com.jlkj.human.api.factory;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.api.RemoteHdApiService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description: 远程调用失败回调
 * @Author: 266861
 * @date: 2023/7/4 13:58
 */
@Component
public class RemoteHdApiFactory implements FallbackFactory<RemoteHdApiService> {

    @Override
    public RemoteHdApiService create(Throwable cause) {
        return new RemoteHdApiService()
        {
            @Override
            public AjaxResult scheduledShifts(String source){ return AjaxResult.error("操作失败"); }

            @Override
            public AjaxResult scheduledZeroing(String source){
                return AjaxResult.error("操作失败");
            }

            @Override
            public AjaxResult attendanceSummary(String source) { return AjaxResult.error("操作失败"); }

            @Override
            public AjaxResult absenteeism(String source) { return AjaxResult.error("操作失败"); }
        };
    }
}
