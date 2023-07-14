package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisMonth;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthDTO;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialDTO;
import com.jlkj.product.oi.vo.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialVO;

import java.util.List;
import java.util.Map;

/**
*@description: 服务接口-产量分析月表
*@Author: 265823
*@date: 2023/7/11 14:18
*/
public interface ProductionYieldAnalysisMonthService extends IService<ProductionYieldAnalysisMonth> {

    /**
     * 生产管理-产量跟踪-查询(月)
     * @param listProductionYieldAnalysisMonthDTO 查询条件dto
     * @return 列表数据
     */
    List<Map<String, Object>> getProductionYieldAnalysisMonthList(ListProductionYieldAnalysisMonthDTO listProductionYieldAnalysisMonthDTO);

    /**
     * 生产管理-产量跟踪-图表-物料名称(月)
     * @param listProductionYieldAnalysisMonthMaterialDTO 查询条件dto
     * @return 列表数据
     */
    List<ListProductionYieldAnalysisMonthMaterialVO> getProductionYieldAnalysisChartMonth(ListProductionYieldAnalysisMonthMaterialDTO listProductionYieldAnalysisMonthMaterialDTO);
}

