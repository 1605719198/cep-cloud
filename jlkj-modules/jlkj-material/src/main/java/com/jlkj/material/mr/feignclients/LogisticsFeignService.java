package com.jlkj.material.mr.feignclients;

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
     * @param mtrlNo
     * @param token
     * @return
     */
    @GetMapping("/logistics/transport/mrplan/getLogisticsMrPlan")
    Object getLogisticsMrPlan(
            @RequestParam(defaultValue = "", value = "reptDate",required = true) String reptDate,
            @RequestParam(defaultValue = "", value = "qty",required = true) BigDecimal qty,
            @RequestParam(defaultValue = "", value = "purposeId",required = true) String purposeId,
            @RequestParam(defaultValue = "", value = "seqNo",required = true) String seqNo,
            @RequestParam(defaultValue = "", value = "waterRate",required = true) BigDecimal waterRate,
            @RequestParam(defaultValue = "", value = "mtrlNo",required = true) String mtrlNo,
            @RequestParam(defaultValue = "", value = "carrierType",required = true) String carrierType,
            @RequestHeader("token") String token);
}
