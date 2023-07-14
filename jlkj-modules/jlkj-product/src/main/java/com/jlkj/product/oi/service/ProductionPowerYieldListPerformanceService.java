package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionPowerListPerformanceEntity;
import com.jlkj.product.oi.dto.productionpowerlistperformance.ProductionPowerYieldListPerformanceDTO;

import java.util.Map;

/**
*@description: 针对表【product_oi_power_generation_performance(发电量实绩)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 9:06
*/
public interface ProductionPowerYieldListPerformanceService extends IService<ProductionPowerListPerformanceEntity> {

    /**
     * 发电量实绩列表查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(ProductionPowerYieldListPerformanceDTO dto);
}
