package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPowerPerformance;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;

import java.util.Map;

/**
*@description: 针对表【product_oi_power_performance(发电量实绩)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 9:00
*/
public interface ProductionPowerPerformanceService extends IService<ProductionPowerPerformance> {

    /**
     * 发电量实绩查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(GetProductionCoefficientRecordDTO dto);
}
