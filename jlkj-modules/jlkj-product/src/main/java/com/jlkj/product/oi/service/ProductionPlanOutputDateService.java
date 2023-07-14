package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanOutputDate;
import com.jlkj.product.oi.dto.productionplanoutputdate.ListProductionPlanOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanDayDTO;
import com.jlkj.product.oi.vo.productionplanoutputdate.ListProductionPlanOutputDateTargetItemVO;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_plan_output_date(计划管理--日产量计划表)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 8:20
*/
public interface ProductionPlanOutputDateService extends IService<ProductionPlanOutputDate> {

    /**
     * 生产管理-指标跟踪-图表-指标项(日计划)
     * @param listProductionPlanOutputDateTargetItemDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionPlanOutputDateTargetItemVO> getProductionPlanOutputDateTargetItemChartData(ListProductionPlanOutputDateTargetItemDTO listProductionPlanOutputDateTargetItemDTO);

    /**
     * 查询日生产产量计划
     * @param dto
     * @return
     */
    List<Map<String, String>> get(GetProductionPlanDayDTO dto);
}
