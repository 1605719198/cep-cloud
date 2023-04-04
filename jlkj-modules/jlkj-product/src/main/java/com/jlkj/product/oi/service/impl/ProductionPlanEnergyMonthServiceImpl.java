package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.product.oi.domain.ProductionPlanEnergyMonth;
import com.jlkj.product.oi.dto.productionplanenergy.GetProductionPlanEnergyMonthDTO;
import com.jlkj.product.oi.mapper.ProductionPlanEnergyMonthMapper;
import com.jlkj.product.oi.service.ProductionPlanEnergyMonthService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zyf
 * @description 针对表【product_oi_plan_energy_month(计划管理-能源月计划)】的数据库操作Service实现
 * @createDate 2022-05-05 10:22:34
 */
@Service
public class ProductionPlanEnergyMonthServiceImpl extends ServiceImpl<ProductionPlanEnergyMonthMapper, ProductionPlanEnergyMonth>
        implements ProductionPlanEnergyMonthService {

    public Object get(GetProductionPlanEnergyMonthDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        if ("".equals(dto.getPlanMonth())) {
            return AjaxResult.success(getBaseMapper().getPlanEnergyMonthPage(page, dto));
        } else {
            return AjaxResult.success(getBaseMapper().getPlanEnergyDayPage(page, dto));
        }

    }
}




