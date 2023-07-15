package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionConveyingCoalPerformance;
import com.jlkj.product.oi.dto.productionconveyingcoalperformance.GetProductionConveyingCoalPerformanceDTO;

import java.util.Map;

/**
*@description:  针对表【product_oi_conveying_coal_performance(上煤实绩)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/10 14:37
*/
public interface ProductionConveyingCoalPerformanceService extends IService<ProductionConveyingCoalPerformance> {

    /**
     * 上料实绩查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(GetProductionConveyingCoalPerformanceDTO dto);
}
