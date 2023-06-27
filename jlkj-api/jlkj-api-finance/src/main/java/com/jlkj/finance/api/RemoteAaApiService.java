package com.jlkj.finance.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.finance.api.bean.FinanceVoucherBean;
import com.jlkj.finance.api.factory.RemoteAaApiFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

/**
 * 财务模块远程服务
 *
 * @author jlkj
 */
@FeignClient(contextId = "remoteAaApiService", value = ServiceNameConstants.FINANCE_SERVICE, fallbackFactory = RemoteAaApiFactory.class)
public interface RemoteAaApiService
{


    /**
     * 查询财务公司(下拉选单用)
     *
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/companyGroup/getCompanyFeign")
    public List<Map<String,String>> selectCompanyList(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 新增凭证接口
     * @param source
     * @param financeVoucherBean
     * @return
     */
    @PostMapping("/voucher/doVoucher")
    public Map<String,Object> doVoucher(@RequestBody FinanceVoucherBean financeVoucherBean,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
