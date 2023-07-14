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
*@description: 服务实现-焦炭出厂实绩
*@Author: 265823
*@date: 2023/7/10 11:34
*/
@Service
public class ProductionCokeFactoryPerformanceServiceImpl extends ServiceImpl<ProductionCokeFactoryPerformanceMapper, ProductionCokeFactoryPerformance>
    implements ProductionCokeFactoryPerformanceService {

    /**
     * 焦炭出厂实绩-查询-分页
     * @param pageProductionCokeFactoryPerformanceDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionCokeFactoryPerformanceVO> getProductionCokeFactoryPerformancePageData(PageProductionCokeFactoryPerformanceDTO pageProductionCokeFactoryPerformanceDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionCokeFactoryPerformanceDTO.getCurrent(), pageProductionCokeFactoryPerformanceDTO.getSize());
        return getBaseMapper().getProductionCokeFactoryPerformancePageData(page, pageProductionCokeFactoryPerformanceDTO);
    }
}

