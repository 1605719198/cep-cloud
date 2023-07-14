package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionSteamPerformance;
import com.jlkj.product.oi.dto.productionsteamperformance.ListHomeProductionSteamPerformanceYieldDTO;
import com.jlkj.product.oi.vo.productionsteamperformance.ListHomeProductionSteamPerformanceYieldVO;

import java.util.List;

/**
*@description: 服务接口-蒸汽实绩
*@Author: 265823
*@date: 2023/7/11 13:49
*/
public interface ProductionSteamPerformanceService extends IService<ProductionSteamPerformance> {

    /**
     * 首页-蒸汽产量
     * @param listHomeProductionSteamPerformanceYieldDTO 查询条件dto
     * @return 列表数据
     */
    List<ListHomeProductionSteamPerformanceYieldVO> getHomeProductionSteamPerformanceYieldListData(ListHomeProductionSteamPerformanceYieldDTO listHomeProductionSteamPerformanceYieldDTO);
}

