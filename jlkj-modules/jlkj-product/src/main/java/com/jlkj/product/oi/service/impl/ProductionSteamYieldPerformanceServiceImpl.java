package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionSteamYieldPerformanceEntity;
import com.jlkj.product.oi.dto.productionsteamyieldperformance.ProductionSteamYieldPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionSteamYieldPerformanceMapper;
import com.jlkj.product.oi.service.ProductionSteamYieldPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
*@description: 发电实绩中的蒸汽产量实绩服务实现类
*@Author: 265823
*@date: 2023/7/11 13:53
*/
@Service
public class ProductionSteamYieldPerformanceServiceImpl extends ServiceImpl<ProductionSteamYieldPerformanceMapper, ProductionSteamYieldPerformanceEntity> implements ProductionSteamYieldPerformanceService {

    /**
     * 蒸汽产量实绩
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String , Object>> get(ProductionSteamYieldPerformanceDTO dto){
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getListPage(page, dto);
    }
}
