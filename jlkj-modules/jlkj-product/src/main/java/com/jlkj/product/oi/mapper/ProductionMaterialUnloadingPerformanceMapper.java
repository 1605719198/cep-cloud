package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionMaterialUnloadingPerformance;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_material_unloading_performance(物料卸车实绩)】的数据库操作Mapper
* @createDate 2022-05-10 08:43:09
* @Entity com.jlkj.product.oi.entity.ProductionMaterialUnloadingPerformance
*/
public interface ProductionMaterialUnloadingPerformanceMapper extends BaseMapper<ProductionMaterialUnloadingPerformance> {
    @Select(
            "select post_name, scheduling_date, case shift when '2201' then '白班' else '夜班' end as shift,\n" +
            "class_type, due_attendance_time_work, due_attendance_time_offduty \n" +
            "from v_humanresource_scheduling_work where post_name = '螺旋卸煤机操作员' and shift != '01'\n" +
            "and #{now} >= due_attendance_time_work and #{now} <= due_attendance_time_offduty"
    )
    Map<String, Object> getShift(Map<String, Object> condition);
}




