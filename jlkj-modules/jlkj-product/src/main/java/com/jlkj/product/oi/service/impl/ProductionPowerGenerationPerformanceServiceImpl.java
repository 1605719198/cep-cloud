package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPowerGenerationPerformance;
import com.jlkj.product.oi.dto.productionpowergenerationperformance.ListHomeProductionPowerGenerationYieldDTO;
import com.jlkj.product.oi.mapper.ProductionPowerGenerationPerformanceMapper;
import com.jlkj.product.oi.service.ProductionPowerGenerationPerformanceService;
import com.jlkj.product.oi.vo.productionpowergenerationperformance.ListHomeProductionPowerGenerationYieldVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*@description: 服务实现-发电实绩
*@Author: 265823
*@date: 2023/7/11 8:56
*/
@Service
public class ProductionPowerGenerationPerformanceServiceImpl extends ServiceImpl<ProductionPowerGenerationPerformanceMapper, ProductionPowerGenerationPerformance>
    implements ProductionPowerGenerationPerformanceService {

    /**
     * 首页-发电量
     * @param listHomeProductionPowerGenerationYieldDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListHomeProductionPowerGenerationYieldVO> getHomeProductionPowerGenerationYieldListData(ListHomeProductionPowerGenerationYieldDTO listHomeProductionPowerGenerationYieldDTO) {
        return getBaseMapper().getHomeProductionPowerGenerationYieldListData(listHomeProductionPowerGenerationYieldDTO);
    }
}

