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
*@description: 服务实现-出炉实绩测温记录
*@Author: 265823
*@date: 2023/7/10 17:08
*/
@Service
public class ProductionOutputPerformanceTemperatureServiceImpl extends ServiceImpl<ProductionOutputPerformanceTemperatureMapper, ProductionOutputPerformanceTemperature>
    implements ProductionOutputPerformanceTemperatureService {

    /**
     * 出炉实绩测温记录-查询-分页
     * @param pageProductionOutputPerformanceTemperatureDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionOutputPerformanceTemperatureVO> getProductionOutputPerformanceTemperaturePageData(PageProductionOutputPerformanceTemperatureDTO pageProductionOutputPerformanceTemperatureDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionOutputPerformanceTemperatureDTO.getCurrent(), pageProductionOutputPerformanceTemperatureDTO.getSize());
        return getBaseMapper().getProductionOutputPerformanceTemperaturePageData(page, pageProductionOutputPerformanceTemperatureDTO);
    }
}

