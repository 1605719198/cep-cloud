package com.jlkj.rabbit.feign.materialprovider;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.rabbit.dto.material.AddMaterialsStorageDTO;
import com.jlkj.rabbit.dto.material.AddMaterialsWeighingPerformanceDTO;
import com.jlkj.rabbit.dto.material.AddMaterialsWeighingPlanDTO;
import com.jlkj.rabbit.dto.material.DelMaterialsWeighingPlanDTO;
import com.jlkj.rabbit.dto.material.materialscokesaledeliverplan.DeleteMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.rabbit.dto.material.materialscokesaledeliverplan.InsertMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.rabbit.dto.material.materialscokesalewriteoffreal.DeleteMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.rabbit.dto.material.materialscokesalewriteoffreal.InsertMaterialsCokeSaleWriteOffRealDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author zyf
 * @Description
 * @create 2022-05-19 14:57
 */
@Component
@FeignClient(value = "material-service-provider")
public interface MaterialFeignService {

    /**
     * 原料检斤计划
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/addMaterialsWeighingPlan")
    AjaxResult addMaterialsWeighingPlan(AddMaterialsWeighingPlanDTO dto, @RequestHeader("token") String token);

    /**
     * 原料检斤计划
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/delMaterialsWeighingPlanByNo")
    AjaxResult delMaterialsWeighingPlan(AddMaterialsWeighingPlanDTO dto, @RequestHeader("token") String token);

    /**
     * 原料检斤实绩新增
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/addMaterialsWeighingPerformance")
    AjaxResult addMaterialsWeighingPerformance(AddMaterialsWeighingPerformanceDTO dto, @RequestHeader("token") String token);

    /**
     * 原料检斤实绩删除
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/delMaterialsWeighingPerformance")
    AjaxResult delMaterialsWeighingPerformance(AddMaterialsWeighingPerformanceDTO dto, @RequestHeader("token") String token);

    /**
     * 原料入储信息
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/addMaterialsStorage")
    AjaxResult addMaterialsStorage(AddMaterialsStorageDTO dto, @RequestHeader("token") String token);

    /**
     * 原料入储信息删除
     * @param dto dto
     * @param token token
     * @return Result
     */
    @PostMapping("/delMaterialsStorage")
    AjaxResult delMaterialsStorage(DelMaterialsWeighingPlanDTO dto, @RequestHeader("token") String token);

    /**
     * 焦炭外售发货计划-新增
     * @param dto dto参数
     * @param token token
     * @return 结果
     */
    @PostMapping("/cokesaledeliverplan/insertMaterialsCokeSaleDeliverPlan")
    AjaxResult insertMaterialsCokeSaleDeliverPlanData(InsertMaterialsCokeSaleDeliverPlanDTO dto, @RequestHeader("token") String token);

    /**
     * 焦炭外售发货计划-删除
     * @param dto dto参数
     * @param token token
     * @return 结果
     */
    @PostMapping("/cokesaledeliverplan/deleteMaterialsCokeSaleDeliverPlan")
    AjaxResult deleteMaterialsCokeSaleDeliverPlanData(DeleteMaterialsCokeSaleDeliverPlanDTO dto, @RequestHeader("token") String token);

    /**
     * 焦炭外售销账实际-新增
     * @param dto dto参数
     * @param token token
     * @return 结果
     */
    @PostMapping("/cokesalewriteoffreal/insertMaterialsCokeSaleWriteOffReal")
    AjaxResult insertMaterialsCokeSaleWriteOffRealData(InsertMaterialsCokeSaleWriteOffRealDTO dto, @RequestHeader("token") String token);

    /**
     * 焦炭外售销账实际-删除
     * @param dto dto参数
     * @param token token
     * @return 结果
     */
    @PostMapping("/cokesalewriteoffreal/deleteMaterialsCokeSaleWriteOffReal")
    AjaxResult deleteMaterialsCokeSaleWriteOffReal(DeleteMaterialsCokeSaleWriteOffRealDTO dto, @RequestHeader("token") String token);
}
