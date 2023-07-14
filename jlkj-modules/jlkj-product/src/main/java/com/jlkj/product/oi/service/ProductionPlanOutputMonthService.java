package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanOutputMonth;
import com.jlkj.product.oi.dto.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.*;
import com.jlkj.product.oi.vo.productionplanoutputmonth.ListProductionPlanOutputMonthTargetItemVO;

import java.util.List;
import java.util.Map;

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
     * @return
     */
    List<Map<String, String>> get(GetProductionPlanMonthDTO dto);

    /**
     * 查询单条月生产产量计划
     * @param dto
     * @return
     */
    List<Map<String, String>> getOneCustom(GetProductionPlanOneMonthDTO dto);

    /**
     * 修改月产量计划
     * @param dto
     */
    void updateCustom(UpdateProductionPlanMonthDTO dto);

    /**
     * 新增年生产产量计划
     * @param productionPlanYearDTO
     */
    void saveCustom(AddProductionPlanYearDTO productionPlanYearDTO);

    /**
     * 删除年计划
     * @param deleteProductionPlanYearDTO
     */
    void delete(DeleteProductionPlanYearDTO deleteProductionPlanYearDTO);
}
