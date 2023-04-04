package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionCokeFactoryPerformance;
import com.jlkj.product.oi.dto.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionCokeFactoryPerformanceMapper;
import com.jlkj.product.oi.service.ProductionCokeFactoryPerformanceService;
import com.jlkj.product.oi.vo.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 服务实现-焦炭出厂实绩
 * @author sudeyou
 * @since 2022-08-11 14:02:21
 */
@Service
public class ProductionCokeFactoryPerformanceServiceImpl extends ServiceImpl<ProductionCokeFactoryPerformanceMapper, ProductionCokeFactoryPerformance>
    implements ProductionCokeFactoryPerformanceService {

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionCokeFactoryPerformanceVO> getProductionCokeFactoryPerformancePageData(PageProductionCokeFactoryPerformanceDTO pageProductionCokeFactoryPerformanceDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionCokeFactoryPerformanceDTO.getCurrent(), pageProductionCokeFactoryPerformanceDTO.getSize());
        return getBaseMapper().getProductionCokeFactoryPerformancePageData(page, pageProductionCokeFactoryPerformanceDTO);
    }
}

