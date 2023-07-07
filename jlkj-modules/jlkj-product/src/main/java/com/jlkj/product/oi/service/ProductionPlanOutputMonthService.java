package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanOutputMonth;
import com.jlkj.product.oi.dto.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.*;
import com.jlkj.product.oi.vo.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemVO;

import java.util.List;

/**
* @author zyf
* @description 针对表【product_oi_plan_output_month(计划管理--月产量计划表)】的数据库操作Service
* @createDate 2022-04-21 14:46:03
*/
public interface ProductionPlanOutputMonthService extends IService<ProductionPlanOutputMonth> {

    /**
     * 生产管理-指标跟踪-图表-指标项(月计划)
     * @param listProductionPlanOutputMonthTargetItemDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionPlanOutputMonthTargetItemVO> getProductionPlanOutputMonthTargetItemChartData(ListProductionPlanOutputMonthTargetItemDTO listProductionPlanOutputMonthTargetItemDTO);

    /**
     * 查询月产量计划
     * @param dto
     * @param itemlist
     * @return
     */
    Object get(GetProductionPlanMonthDTO dto, List<ProductionParameterTargetItem> itemlist);

    /**
     * 查询单条月生产产量计划
     * @param dto
     * @param itemlist
     * @return
     */
    Object getOneCustom(GetProductionPlanOneMonthDTO dto, List<ProductionParameterTargetItem> itemlist);

    /**
     * 修改月产量计划
     * @param dto
     * @return
     */
    Object updateCustom(UpdateProductionPlanMonthDTO dto);

    /**
     * 新增年生产产量计划
     * @param productionPlanYearDTO
     * @return
     */
    Object saveCustom(AddProductionPlanYearDTO productionPlanYearDTO);

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    AjaxResult delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO);
}
