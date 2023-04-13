package com.jlkj.system.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-09-02 20:57:47.825649
 */
public interface MonitorDataMapper {


    /**
     * 获取当前工作班组
     *
     * @param id
     * @return
     */
    @Select(" select * from v_humanresource_scheduling_work where post_id = #{id} and shift != '01' " +
            " AND due_attendance_time_work < date_sub(NOW(),interval 2 HOUR) and due_attendance_time_offduty >= date_sub(NOW(),interval 2 HOUR) " +
            " LIMIT 1 ")
    Map<String, Object> getSchedulingWork(String id);

    /**
     * 将当前实时数据保存到 出炉实绩测温记录
     *
     * @param condition
     * @return
     */
    @Insert("insert into t_production_output_performance_temperature" +
            "(id,measure_time,shift_name,class_name,coke_oven_number,measure_type,standard_temp,maximum_temp,minimum_temp,average_temp) values \n" +
            "(#{id},NOW(),#{shift_name},#{class_name} ,#{coke_oven_number},#{measure_type},#{standard_temp},#{max},#{min},#{avg} )")
    int insertOutputPerformanceTemperature(Map<String, Object> condition);

    /**
     * 查询环保采集点位
     *
     * @param type
     * @return
     */
    @Select(" select collection_point from t_environmental_collection_points where pollutant_name = #{type} ")
    List<Map<String, Object>> getEnvironmentalCollectionPoints(String type);
}
