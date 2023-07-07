package com.jlkj.human.api.factory;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.api.RemoteHsApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 远程调用失败回调
 * @Author: 266861
 * @date: 2023/7/4 13:58
 */
@Component
public class RemoteHsApiFactory implements FallbackFactory<RemoteHsApiService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteHsApiFactory.class);

    @Override
    public RemoteHsApiService create(Throwable throwable) {
        return new RemoteHsApiService()
        {
            @Override
            public AjaxResult setNewYearSocialSecurity(String source) {
                Map<String,String> errMap = new HashMap<>(2);
                errMap.put("errMsg","操作失败:" + throwable.getMessage());
                return AjaxResult.error(String.valueOf(errMap));
            }
        };
    }
}
