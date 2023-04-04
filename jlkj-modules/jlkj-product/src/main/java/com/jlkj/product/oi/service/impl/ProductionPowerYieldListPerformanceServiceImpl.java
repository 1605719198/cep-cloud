package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPowerListPerformanceEntity;
import com.jlkj.product.oi.dto.productionpowerlistperformance.ProductionPowerYieldListPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionPowerListPerformanceMapper;
import com.jlkj.product.oi.service.ProductionPowerYieldListPerformanceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zzh
 * @description 针对表【product_oi_power_generation_performance(发电量实绩)】的数据库操作Service实现
 * @createDate 2022年9月7日16:34:11
 */
@Service
public class ProductionPowerYieldListPerformanceServiceImpl extends ServiceImpl<ProductionPowerListPerformanceMapper, ProductionPowerListPerformanceEntity> implements ProductionPowerYieldListPerformanceService {

    public IPage<Map<String, Object>> get(ProductionPowerYieldListPerformanceDTO dto){
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getListPage(page, dto);
    }
}
