package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.EnergyMonthPlanOutput;
import com.jlkj.product.oi.dto.productionplanenergyoutput.GetProductionPlanEnergyOutputDTO;
import com.jlkj.product.oi.mapper.ProductionPlanEnergyOutputMapper;
import com.jlkj.product.oi.service.ProductionPlanEnergyOutputService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yzl
 * @description 发电产量
 * @createDate 2022年9月6日 09:14:40
 */
@Service
public class ProductionPlanEnergyOutputServiceImpl extends ServiceImpl<ProductionPlanEnergyOutputMapper, EnergyMonthPlanOutput>
        implements ProductionPlanEnergyOutputService {

    @Override
    public Object getPageData(GetProductionPlanEnergyOutputDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        if ("".equals(dto.getPlanMonth())) {
            return AjaxResult.success(getBaseMapper().getPlanEnergyOutputMonthPage(page, dto));
        } else {
            return AjaxResult.success(getBaseMapper().getPlanEnergyOutputDayPage(page, dto));
        }

    }

}
