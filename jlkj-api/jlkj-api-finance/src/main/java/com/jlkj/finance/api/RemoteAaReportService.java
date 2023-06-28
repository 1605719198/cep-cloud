package com.jlkj.finance.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.finance.api.factory.RemoteAaReportFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * 财务模块远程服务
 *
 * @author jlkj
 */
@FeignClient(contextId = "remoteAaReportService", value = ServiceNameConstants.FINANCE_SERVICE, fallbackFactory = RemoteAaReportFactory.class)
public interface RemoteAaReportService
{



    /**
     * 新增凭证接口
     * @param source 来源
     * @param parameters  凭证数据
     * @return 执行信息
     */
    @PostMapping("/accountSteel/reportDetailIfSteel")
    public Map<String,Object> accountBalanceTonSteelData(@RequestBody  Map<String, Object> parameters,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);


   

}
