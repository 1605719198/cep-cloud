package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanTargetYear;
import com.jlkj.product.oi.dto.productionplantarget.*;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_plan_target_year(计划管理--年指标计划表)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 8:48
*/
public interface ProductionPlanTargetYearService extends IService<ProductionPlanTargetYear> {

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    void delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO);

    /**
     * 年计划指标查询
     * @return
     */
    List<Map<String, String>> getList();

    /**
     * 新增年生产指标计划
     * @param productionPlanTargetYearDTO
     */
    void saveBy(AddProductionPlanYearDTO productionPlanTargetYearDTO);

    /**
     * 查询月生产指标计划
     * @param dto
     * @return
     */
    List<Map<String, String>> getMonth(GetProductionPlanMonthDTO dto);

    /**
     * 查询单条月生产指标计划
     * @param dto
     * @return
     */
    List<Map<String, String>> getOneCustom(GetProductionPlanOneMonthDTO dto);

    /**
     * 修改月指标计划
     * @param dto
     */
    void updateCustom(UpdateProductionPlanMonthDTO dto);

    /**
     * 查询日生产指标计划
     * @param dto
     * @return
     */
    List<Map<String, String>> getDate(GetProductionPlanDayDTO dto);
}
