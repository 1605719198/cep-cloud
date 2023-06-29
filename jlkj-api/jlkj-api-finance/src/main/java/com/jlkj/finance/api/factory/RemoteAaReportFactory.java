package com.jlkj.finance.api.factory;

import com.jlkj.finance.api.RemoteAaReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * 财务模块远程服务
 *
 * @author jlkj
 */
@Component
public class RemoteAaReportFactory implements FallbackFactory<RemoteAaReportService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteAaReportFactory.class);

    @Override
    public RemoteAaReportService create(Throwable throwable)
    {

        return new RemoteAaReportService()
        {


            @Override
            public Map<String, Object> accountBalanceTonSteelData(Map<String, Object> parameter, String source) {
                log.info("RemoteAaApiService doVoucher 失败 接收到参数：{}",parameter);
                return null;
            }

        };


    }
}
