package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationYear;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_power_generation_year(计划管理--发电年指标计划)】的数据库操作Mapper
* @createDate 2022-04-29 13:46:55
* @Entity com.jlkj.product.oi.entity.ProductionPlanPowerGenerationYear
*/
public interface ProductionPlanPowerGenerationYearMapper extends BaseMapper<ProductionPlanPowerGenerationYear> {

    /**
     * 获取数据
     * @param sql
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getList(String sql);
}




