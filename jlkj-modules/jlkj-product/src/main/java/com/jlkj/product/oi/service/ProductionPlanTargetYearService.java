package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanTargetYear;
import com.jlkj.product.oi.dto.productionplantarget.*;

import java.util.List;

/**
* @author zyf
* @description 针对表【product_oi_plan_target_year(计划管理--年指标计划表)】的数据库操作Service
* @createDate 2022-04-21 14:46:03
*/
public interface ProductionPlanTargetYearService extends IService<ProductionPlanTargetYear> {

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    AjaxResult delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO);

    /**
     * 年计划指标查询
     * @param itemlist
     * @return
     */
    Object get(List<ProductionParameterTargetItem> itemlist);

    /**
     * 新增年生产指标计划
     * @param productionPlanTargetYearDTO
     * @return
     */
    Object saveBy(AddProductionPlanYearDTO productionPlanTargetYearDTO);

    /**
     * 查询月生产指标计划
     * @param dto
     * @param itemlist
     * @return
     */
    Object getMonth(GetProductionPlanMonthDTO dto, List<ProductionParameterTargetItem> itemlist);

    /**
     * 查询单条月生产指标计划
     * @param dto
     * @param itemlist
     * @return
     */
    Object getOneCustom(GetProductionPlanOneMonthDTO dto, List<ProductionParameterTargetItem> itemlist);

    /**
     * 修改月指标计划
     * @param dto
     * @return
     */
    Object updateCustom(UpdateProductionPlanMonthDTO dto);

    /**
     * 查询日生产指标计划
     * @param dto
     * @param itemlist
     * @return
     */
    Object getDate(GetProductionPlanDayDTO dto, List<ProductionParameterTargetItem> itemlist);
}
