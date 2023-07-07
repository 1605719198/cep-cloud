package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationYear;
import com.jlkj.product.oi.dto.productionplantarget.*;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_power_generation_year(计划管理--发电年指标计划)】的数据库操作Service
* @createDate 2022-04-29 13:46:55
*/
public interface ProductionPlanPowerGenerationYearService extends IService<ProductionPlanPowerGenerationYear> {

    /**
     * 查询日发电计划
     * @param dto
     * @return
     */
    Object get(GetProductionPlanMonthDTO dto);

    /**
     * 查询单条月发电计划
     * @param dto
     * @return
     */
    Object getOneCustom(GetProductionPlanOneMonthDTO dto);

    /**
     * 修改月发电计划
     * @param dto
     * @return
     */
    Object updateCustom(UpdateProductionPlanMonthDTO dto);

    /**
     * 查询日发电计划
     * @param dto
     * @return
     */
    Object getDate(GetProductionPlanDayDTO dto);

    /**
     * 年发电计划查询
     * @param s
     * @return
     */
    List<Map<String, String>> getColToRowList(String s);

    /**
     * 新增年发电计划
     * @param productionPlanYearDTO
     * @return
     */
    Object saveCustom(AddProductionPowerPlanYearDTO productionPlanYearDTO);

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    AjaxResult delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO);
}
