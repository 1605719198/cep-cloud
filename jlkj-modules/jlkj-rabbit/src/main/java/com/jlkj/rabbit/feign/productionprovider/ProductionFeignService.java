package com.jlkj.rabbit.feign.productionprovider;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.rabbit.dto.production.AddProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.rabbit.dto.production.UpdateProductionMaterialUnloadingPerformanceWeightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author zyf
 * @Description
 * @create 2022-05-20 8:36
 */
@Component
@FeignClient(value = "production-service-provider")
public interface ProductionFeignService {

    /**
     * 物料卸货实绩新增
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/performance/saveMaterialUnloadingPerformance")
    AjaxResult saveMaterialUnloadingPerformance(AddProductionMaterialUnloadingPerformanceDTO dto, @RequestHeader("token") String token);

    /**
     * 物料卸货实绩删除
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/performance/delMaterialUnloadingPerformance")
    AjaxResult delMaterialUnloadingPerformance(AddProductionMaterialUnloadingPerformanceDTO dto, @RequestHeader("token") String token);

    /**
     * 物料卸货实绩修改重量
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/performance/updateMaterialUnloadingPerformanceWeight")
    AjaxResult updateMaterialUnloadingPerformanceWeight(UpdateProductionMaterialUnloadingPerformanceWeightDTO dto, @RequestHeader("token") String token);
}
