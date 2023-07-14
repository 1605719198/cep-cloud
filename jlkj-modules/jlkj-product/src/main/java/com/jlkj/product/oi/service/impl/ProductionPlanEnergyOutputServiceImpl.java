package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.EnergyMonthPlanOutput;
import com.jlkj.product.oi.dto.productionplanenergyoutput.GetProductionPlanEnergyOutputDTO;
import com.jlkj.product.oi.mapper.ProductionPlanEnergyOutputMapper;
import com.jlkj.product.oi.service.ProductionPlanEnergyOutputService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
*@description: 发电产量
*@Author: 265823
*@date: 2023/7/11 8:20
*/
@Service
public class ProductionPlanEnergyOutputServiceImpl extends ServiceImpl<ProductionPlanEnergyOutputMapper, EnergyMonthPlanOutput>
        implements ProductionPlanEnergyOutputService {

    /**
     * 查询-分页-发电产量
     * @param dto 查询条件dto
     * @return
     */
    @Override
    public IPage<Map<String, String>> getPageData(GetProductionPlanEnergyOutputDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        if ("".equals(dto.getPlanMonth())) {
            return getBaseMapper().getPlanEnergyOutputMonthPage(page, dto);
        } else {
            return getBaseMapper().getPlanEnergyOutputDayPage(page, dto);
        }

    }

}
