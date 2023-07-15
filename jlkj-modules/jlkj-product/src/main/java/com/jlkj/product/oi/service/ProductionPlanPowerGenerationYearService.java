package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationYear;
import com.jlkj.product.oi.dto.productionplantarget.*;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_plan_power_generation_year(计划管理--发电年指标计划)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 8:28
*/
public interface ProductionPlanPowerGenerationYearService extends IService<ProductionPlanPowerGenerationYear> {

    /**
     * 查询日发电计划
     * @param dto
     * @return
     */
    List<Map<String, String>> get(GetProductionPlanMonthDTO dto);

    /**
     * 查询单条月发电计划
     * @param dto
     * @return
     */
    List<Map<String, String>> getOneCustom(GetProductionPlanOneMonthDTO dto);

    /**
     * 修改月发电计划
     * @param dto
     */
    void updateCustom(UpdateProductionPlanMonthDTO dto);

    /**
     * 查询日发电计划
     * @param dto
     * @return
     */
    List<Map<String, String>> getDate(GetProductionPlanDayDTO dto);

    /**
     * 年发电计划查询
     * @param s
     * @return
     */
    List<Map<String, String>> getColToRowList(String s);

    /**
     * 新增年发电计划
     * @param productionPlanYearDTO
     */
    void saveCustom(AddProductionPowerPlanYearDTO productionPlanYearDTO);

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    void delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO);
}
