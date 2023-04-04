package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionSteamPerformance;
import com.jlkj.product.oi.dto.productionsteamperformance.ListHomeProductionSteamPerformanceYieldDTO;
import com.jlkj.product.oi.vo.productionsteamperformance.ListHomeProductionSteamPerformanceYieldVO;

import java.util.List;

/**
 * 服务接口-蒸汽实绩
 * @author sudeyou
 * @since 2022-09-01 09:40:55
 */
public interface ProductionSteamPerformanceService extends IService<ProductionSteamPerformance> {

    /**
     * 首页-蒸汽产量
     * @param listHomeProductionSteamPerformanceYieldDTO 查询条件dto
     * @return 列表数据
     */
    List<ListHomeProductionSteamPerformanceYieldVO> getHomeProductionSteamPerformanceYieldListData(ListHomeProductionSteamPerformanceYieldDTO listHomeProductionSteamPerformanceYieldDTO);
}

