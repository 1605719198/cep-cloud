package com.jlkj.product.oi.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author sudeyou
 * @since 2022-11-22 15:03:49
 */
@Component
@FeignClient(value = "logistics-service-provider")
public interface LogisticsFeignService {

    /**
     *  物流管理-MR倒运计划条件查询
     * @param reptDate
     * @param qty
     * @param purposeId
     * @param seqNo
     * @param waterRate
     * @param carrierType
     * @param mtrlNo
     * @param token
     * @return
     */
    @GetMapping("/logistics/transport/mrplan/getLogisticsMrPlan")
    Object getLogisticsMrPlan(
            @RequestParam(value = "reptDate",defaultValue = "",required = true) String reptDate,
            @RequestParam(value = "qty",defaultValue = "",required = true) BigDecimal qty,
            @RequestParam(value = "purposeId",defaultValue = "",required = true) String purposeId,
            @RequestParam(value = "seqNo",defaultValue = "",required = true) String seqNo,
            @RequestParam(value = "waterRate",defaultValue = "",required = true) BigDecimal waterRate,
            @RequestParam(value = "mtrlNo",defaultValue = "",required = true) String mtrlNo,
            @RequestParam(value = "carrierType",defaultValue = "",required = true) String carrierType,
            @RequestHeader("token") String token);
}
