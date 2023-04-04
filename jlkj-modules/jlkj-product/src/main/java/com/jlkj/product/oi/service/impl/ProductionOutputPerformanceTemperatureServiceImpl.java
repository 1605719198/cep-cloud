package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionOutputPerformanceTemperature;
import com.jlkj.product.oi.dto.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureDTO;
import com.jlkj.product.oi.mapper.ProductionOutputPerformanceTemperatureMapper;
import com.jlkj.product.oi.service.ProductionOutputPerformanceTemperatureService;
import com.jlkj.product.oi.vo.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 服务实现-出炉实绩测温记录
 * @author sudeyou
 * @since 2022-08-11 14:06:15
 */
@Service
public class ProductionOutputPerformanceTemperatureServiceImpl extends ServiceImpl<ProductionOutputPerformanceTemperatureMapper, ProductionOutputPerformanceTemperature>
    implements ProductionOutputPerformanceTemperatureService {

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionOutputPerformanceTemperatureVO> getProductionOutputPerformanceTemperaturePageData(PageProductionOutputPerformanceTemperatureDTO pageProductionOutputPerformanceTemperatureDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionOutputPerformanceTemperatureDTO.getCurrent(), pageProductionOutputPerformanceTemperatureDTO.getSize());
        return getBaseMapper().getProductionOutputPerformanceTemperaturePageData(page, pageProductionOutputPerformanceTemperatureDTO);
    }
}

