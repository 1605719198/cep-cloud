package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionPlanOutputYear;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_plan_output_year(计划管理--年产量计划)】的数据库操作Mapper
* @createDate 2022-04-21 14:46:03
* @Entity com.jlkj.product.oi.entity.ProductionPlanOutputYear
*/
public interface ProductionPlanOutputYearMapper extends BaseMapper<ProductionPlanOutputYear> {

    /**
     * 获取数据
     * @param sql
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getList(String sql);
}




