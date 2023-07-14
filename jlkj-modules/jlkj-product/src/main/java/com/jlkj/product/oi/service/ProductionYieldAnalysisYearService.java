package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisYear;
import com.jlkj.product.oi.dto.productionyieldanalysisyear.ListProductionYieldAnalysisYearDTO;

import java.util.List;
import java.util.Map;

/**
*@description: 服务接口-产量分析年表
*@Author: 265823
*@date: 2023/7/11 14:22
*/
public interface ProductionYieldAnalysisYearService extends IService<ProductionYieldAnalysisYear> {

    /**
     * 生产管理-产量跟踪-查询(年)
     * @param listProductionYieldAnalysisYearDTO 查询条件dto
     * @return 列表数据
     */
    List<Map<String, Object>> getProductionYieldAnalysisYearList(ListProductionYieldAnalysisYearDTO listProductionYieldAnalysisYearDTO);
}

