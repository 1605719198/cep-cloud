package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionPlanTargetYear;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_plan_target_year(计划管理--年指标计划表)】的数据库操作Mapper
*@Author: 265823
*@date: 2023/7/13 16:56
*/
public interface ProductionPlanTargetYearMapper extends BaseMapper<ProductionPlanTargetYear> {

    /**
     * 获取数据
     * @param sql
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getList(String sql);
}




