package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionPlanOutputDate;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationDate;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_power_generation_date(计划管理-发电日指标计划)】的数据库操作Mapper
* @createDate 2022-04-29 13:46:55
* @Entity com.jlkj.product.oi.entity.ProductionPlanPowerGenerationDate
*/
public interface ProductionPlanPowerGenerationDateMapper extends BaseMapper<ProductionPlanPowerGenerationDate> {

    /**
     * 获取数据
     * @param sql
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getList(String sql);

    /**
     * 获取数据
     * @param year
     * @return
     */
    @MapKey("id")
    List<ProductionPlanOutputDate> getPowerDateRelationRepair(int year);

    /**
     * 获取数据
     * @param year
     * @param month
     * @return
     */
    @MapKey("id")
    List<ProductionPlanOutputDate> getPowerDateWithMonthRelationRepair(int year, int month);
}




