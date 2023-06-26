package com.jlkj.finance.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.finance.api.bean.FinanceVoucherBean;
import com.jlkj.finance.api.factory.RemoteAaCompanyGroupFallbackFactory;
import com.jlkj.finance.api.factory.RemoteAaVoucherFallFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 财务模块远程服务
 *
 * @author jlkj
 */
@FeignClient(contextId = "remoteAaVoucherService", value = ServiceNameConstants.FINANCE_SERVICE, fallbackFactory = RemoteAaVoucherFallFactory.class)
public interface RemoteAaVoucherService
{


    /**
     * 新增凭证接口
     * @param source
     * @param financeVoucherBean
     * @return
     */
    @PostMapping("/voucher/doVoucher")
    public Map<String,Object> doVoucher(@RequestBody FinanceVoucherBean financeVoucherBean,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
