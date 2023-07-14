package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionSteamYieldPerformanceEntity;
import com.jlkj.product.oi.dto.productionsteamyieldperformance.ProductionSteamYieldPerformanceDTO;

import java.util.Map;

/**
*@description: 针对表【product_oi_steam_performance(发电量实绩)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/11 13:52
*/
public interface ProductionSteamYieldPerformanceService extends IService<ProductionSteamYieldPerformanceEntity> {
    /**
     * 蒸汽产量实绩
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(ProductionSteamYieldPerformanceDTO dto);

}
