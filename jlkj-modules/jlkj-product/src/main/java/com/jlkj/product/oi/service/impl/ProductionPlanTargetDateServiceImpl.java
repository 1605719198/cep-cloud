package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionPlanTargetDate;
import com.jlkj.product.oi.mapper.ProductionPlanTargetDateMapper;
import com.jlkj.product.oi.service.ProductionPlanTargetDateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_target_date(计划管理--日指标计划)】的数据库操作Service实现
* @createDate 2022-04-21 14:46:03
*/
@Service
public class ProductionPlanTargetDateServiceImpl extends ServiceImpl<ProductionPlanTargetDateMapper, ProductionPlanTargetDate>
    implements ProductionPlanTargetDateService {

    public List<Map<String, String>> getColToRowList(String sql) {
        return getBaseMapper().getList(sql);
    };
}




