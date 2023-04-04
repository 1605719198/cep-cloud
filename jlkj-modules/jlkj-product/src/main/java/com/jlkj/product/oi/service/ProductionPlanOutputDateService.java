package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPlanOutputDate;
import com.jlkj.product.oi.dto.productionplanoutputdate.ListProductionPlanOutputDateTargetItemDTO;
import com.jlkj.product.oi.vo.productionplanoutputdate.ListProductionPlanOutputDateTargetItemVO;

import java.util.List;

/**
* @author zyf
* @description 针对表【product_oi_plan_output_date(计划管理--日产量计划表)】的数据库操作Service
* @createDate 2022-04-21 14:46:03
*/
public interface ProductionPlanOutputDateService extends IService<ProductionPlanOutputDate> {

    /**
     * 生产管理-指标跟踪-图表-指标项(日计划)
     * @param listProductionPlanOutputDateTargetItemDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionPlanOutputDateTargetItemVO> getProductionPlanOutputDateTargetItemChartData(ListProductionPlanOutputDateTargetItemDTO listProductionPlanOutputDateTargetItemDTO);
}
