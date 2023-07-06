package com.jlkj.human.api.factory;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.api.RemoteHsApiService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description: 远程调用失败回调
 * @Author: 266861
 * @date: 2023/7/4 13:58
 */
@Component
public class RemoteHsApiFactory implements FallbackFactory<RemoteHsApiService> {

    @Override
    public RemoteHsApiService create(Throwable cause) {
        return new RemoteHsApiService() {

            @Override
            public AjaxResult setNewYearSocialSecurity(String source) {
                return AjaxResult.error("失败");
            }
        };
    }
}
