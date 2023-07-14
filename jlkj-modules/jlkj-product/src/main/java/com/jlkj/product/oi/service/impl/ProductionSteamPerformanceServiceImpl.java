package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionSteamPerformance;
import com.jlkj.product.oi.dto.productionsteamperformance.ListHomeProductionSteamPerformanceYieldDTO;
import com.jlkj.product.oi.mapper.ProductionSteamPerformanceMapper;
import com.jlkj.product.oi.service.ProductionSteamPerformanceService;
import com.jlkj.product.oi.vo.productionsteamperformance.ListHomeProductionSteamPerformanceYieldVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*@description: 服务实现-蒸汽实绩
*@Author: 265823
*@date: 2023/7/11 13:49
*/
@Service
public class ProductionSteamPerformanceServiceImpl extends ServiceImpl<ProductionSteamPerformanceMapper, ProductionSteamPerformance>
    implements ProductionSteamPerformanceService {

    /**
     * 首页-蒸汽产量
     * @param listHomeProductionSteamPerformanceYieldDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListHomeProductionSteamPerformanceYieldVO> getHomeProductionSteamPerformanceYieldListData(ListHomeProductionSteamPerformanceYieldDTO listHomeProductionSteamPerformanceYieldDTO) {
        return getBaseMapper().getHomeProductionSteamPerformanceYieldListData(listHomeProductionSteamPerformanceYieldDTO);
    }
}

