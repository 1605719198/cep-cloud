package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.safety.si.entity.AlertRecord;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-14 09:36:40.869348
 */
public interface AlertRecordMapper extends BaseMapper<AlertRecord> {

    /**
     * 环保看板 - 查询环保报警
     * @return
     */
    @Select("SELECT a.* FROM t_alert_record a INNER JOIN safe_si_environmental_collection_points b on a.sourceh_id=b.id AND a.alarm_value > b.emission_limit where is_handle = 0 ")
    List<Map<String, String>> getAlertRecord();

    /**
     * 环保看板 - 超标次数分析
     * @return
     */
    @Select("SELECT " +
            " SUM(CASE sourceh_name WHEN '颗粒物' THEN 1 ELSE 0 END) AS ParticulateMatter, " +
            " SUM(CASE sourceh_name WHEN '二氧化硫' THEN 1 ELSE 0 END) AS SO2, " +
            " SUM(CASE sourceh_name WHEN '氮氧化物' THEN 1 ELSE 0 END) AS NitrogenOxides " +
            "FROM t_alert_record a INNER JOIN safe_si_environmental_collection_points b ON a.sourceh_id = b.id AND a.alarm_value > b.emission_limit  " +
            "WHERE is_handle = 0 AND DATE_FORMAT(a.create_time, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d')")
    Map<String, String> getAlertRecordExceedance();

    /**
     * 首页 - 环保指标
     * @return
     */
    @Select("SELECT a.sourceh_name,alarm_value,emission_limit,(alarm_value-emission_limit) as exceedance FROM t_alert_record a " +
            "INNER JOIN safe_si_environmental_collection_points b ON a.sourceh_id = b.id AND a.alarm_value > b.emission_limit WHERE is_handle = 0")
    List<Map<String, String>> getHomeAlertRecord();

}


