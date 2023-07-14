package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPlanTargetMonth;
import com.jlkj.product.oi.mapper.ProductionPlanTargetMonthMapper;
import com.jlkj.product.oi.service.ProductionPlanTargetMonthService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_plan_target_month(计划管理-月指标计划)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/13 16:31
*/
@Service
public class ProductionPlanTargetMonthServiceImpl extends ServiceImpl<ProductionPlanTargetMonthMapper, ProductionPlanTargetMonth>
    implements ProductionPlanTargetMonthService{

    /**
     * 查询月生产指标计划
     * @param sql
     * @return
     */
    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    };
}




