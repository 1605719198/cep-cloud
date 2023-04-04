package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationMonth;
import com.jlkj.product.oi.mapper.ProductionPlanPowerGenerationMonthMapper;
import com.jlkj.product.oi.service.ProductionPlanPowerGenerationMonthService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_power_generation_month(计划管理--发电月指标计划)】的数据库操作Service实现
* @createDate 2022-04-29 13:46:55
*/
@Service
public class ProductionPlanPowerGenerationMonthServiceImpl extends ServiceImpl<ProductionPlanPowerGenerationMonthMapper, ProductionPlanPowerGenerationMonth>
    implements ProductionPlanPowerGenerationMonthService {

    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    };
}




