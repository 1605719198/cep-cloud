package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPowerListPerformanceEntity;
import com.jlkj.product.oi.dto.productionpowerlistperformance.ProductionPowerYieldListPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionPowerListPerformanceMapper;
import com.jlkj.product.oi.service.ProductionPowerYieldListPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
*@description: 针对表【product_oi_power_generation_performance(发电量实绩)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/11 9:07
*/
@Service
public class ProductionPowerYieldListPerformanceServiceImpl extends ServiceImpl<ProductionPowerListPerformanceMapper, ProductionPowerListPerformanceEntity> implements ProductionPowerYieldListPerformanceService {

    /**
     * 发电量实绩列表查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, Object>> get(ProductionPowerYieldListPerformanceDTO dto){
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getListPage(page, dto);
    }
}
