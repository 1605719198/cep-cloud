package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPlanEnergyMonth;
import com.jlkj.product.oi.dto.productionplanenergy.GetProductionPlanEnergyMonthDTO;
import com.jlkj.product.oi.mapper.ProductionPlanEnergyMonthMapper;
import com.jlkj.product.oi.service.ProductionPlanEnergyMonthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
*@description: 针对表【product_oi_plan_energy_month(计划管理-能源月计划)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/11 8:17
*/
@Service
public class ProductionPlanEnergyMonthServiceImpl extends ServiceImpl<ProductionPlanEnergyMonthMapper, ProductionPlanEnergyMonth>
        implements ProductionPlanEnergyMonthService {

    /**
     * 查询能源月计划
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, String>> get(GetProductionPlanEnergyMonthDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        if ("".equals(dto.getPlanMonth())) {
            return getBaseMapper().getPlanEnergyMonthPage(page, dto);
        } else {
            return getBaseMapper().getPlanEnergyDayPage(page, dto);
        }

    }
}




