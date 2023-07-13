package com.jlkj.finance.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.finance.api.bean.FinanceVoucherBean;
import com.jlkj.finance.api.factory.RemoteAaApiFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    *查询财务厂商编码(下拉选单用)
    *@author 265799
    *@date 2023/7/10  15:43
     * @param companyId :
     * @param source :
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    @GetMapping("/finance/manufacturer/getManufacturerList/{companyId}")
    public List<Map<String,String>> selectManufacturerList( @PathVariable("companyId") String companyId,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    /**
     * 新增凭证接口
     * @param source 来源
     * @param financeVoucherBean  凭证数据
     * @return 执行信息
     */
    @PostMapping("/voucher/doVoucher")
    public Map<String,Object> doVoucher(@RequestBody FinanceVoucherBean financeVoucherBean,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);




}
