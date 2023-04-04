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
 * 服务实现-蒸汽实绩
 * @author sudeyou
 * @since 2022-09-01 09:40:55
 */
@Service
public class ProductionSteamPerformanceServiceImpl extends ServiceImpl<ProductionSteamPerformanceMapper, ProductionSteamPerformance>
    implements ProductionSteamPerformanceService {

    @Override
    @Transactional(readOnly = true)
    public List<ListHomeProductionSteamPerformanceYieldVO> getHomeProductionSteamPerformanceYieldListData(ListHomeProductionSteamPerformanceYieldDTO listHomeProductionSteamPerformanceYieldDTO) {
        return getBaseMapper().getHomeProductionSteamPerformanceYieldListData(listHomeProductionSteamPerformanceYieldDTO);
    }
}

