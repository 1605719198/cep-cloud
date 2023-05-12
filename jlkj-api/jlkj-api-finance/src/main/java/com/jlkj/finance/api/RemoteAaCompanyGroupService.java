package com.jlkj.finance.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.common.core.domain.R;

import com.jlkj.finance.api.factory.RemoteAaCompanyGroupFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 财务模块远程服务
 *
 * @author jlkj
 */
@FeignClient(contextId = "remoteAaCompanyGroupService", value = ServiceNameConstants.FINANCE_SERVICE, fallbackFactory = RemoteAaCompanyGroupFallbackFactory.class)
public interface RemoteAaCompanyGroupService
{
    /**
     * 查询核算项目类别(下拉选单用)
     *
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/companyGroup/companyList")
    public List<Map<String,String>> selectCompanyList( @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
