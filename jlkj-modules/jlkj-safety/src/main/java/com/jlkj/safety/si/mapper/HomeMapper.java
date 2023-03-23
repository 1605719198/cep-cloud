package com.jlkj.safety.si.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.safety.si.entity.Empty;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 安环看板 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
public interface HomeMapper extends BaseMapper<Empty> {

    /**
     * 信息-作业票数
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select sum(job_count) as job_count from (\n" +
            "select COUNT(*) as job_count from safe_si_job_fire where start_time <= now() and end_time >= now() and status != 2\n" +
            "union all\n" +
            "select COUNT(*) as job_count from safe_si_job_space where start_time <= now() and end_time >= now() and status != 2\n" +
            "union all\n" +
            "select COUNT(*) as job_count from safe_si_job_high where start_time <= now() and end_time >= now() and status != 2\n" +
            "union all\n" +
            "select COUNT(*) as job_count from safe_si_job_hoisting where start_time <= now() and end_time >= now() and status != 2\n" +
            "union all\n" +
            "select COUNT(*) as job_count from safe_si_job_temporary_electricity where start_time <= now() and end_time >= now() and status != 2\n" +
            "union all\n" +
            "select COUNT(*) as job_count from safe_si_job_blind_plate_plugging where implementation_time_plugging <= now() and implementation_time_take_out >= now() and status != 2\n" +
            "union all\n" +
            "select COUNT(*) as job_count from safe_si_job_soil where start_time <= now() and end_time >= now() and status != 2\n" +
            "union all\n" +
            "select COUNT(*) as job_count from safe_si_job_cut_road where start_time <= now() and end_time >= now() and status != 2\n" +
            ") as  a"
    )
    Map<String, Object> getSafeJobCount(Map<String, Object> condition);

    /**
     * 信息-施工单位数
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select count(construction_unit_name) as construction_unit_count from (\n" +
            "select b.construction_unit_name from safe_si_job_fire a INNER JOIN safe_si_job_fire_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 group by b.construction_unit_name\n" +
            "union\n" +
            "select b.construction_unit_name from safe_si_job_space a INNER JOIN safe_si_job_space_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 group by b.construction_unit_name\n" +
            "union\n" +
            "select b.construction_unit_name from safe_si_job_high a INNER JOIN safe_si_job_high_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 group by b.construction_unit_name\n" +
            "union\n" +
            "select b.construction_unit_name from safe_si_job_hoisting a INNER JOIN safe_si_job_hoisting_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 group by b.construction_unit_name\n" +
            "union\n" +
            "select b.construction_unit_name from safe_si_job_temporary_electricity a INNER JOIN safe_si_job_temporary_electricity_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 group by b.construction_unit_name\n" +
            "union\n" +
            "select b.construction_unit_name from safe_si_job_blind_plate_plugging a INNER JOIN safe_si_job_blind_plate_plugging_persons b ON a.id = b.job_id where a.implementation_time_plugging <= now() and a.implementation_time_take_out >= now() and status != 2 group by b.construction_unit_name\n" +
            "union\n" +
            "select b.construction_unit_name from safe_si_job_soil a INNER JOIN safe_si_job_soil_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 group by b.construction_unit_name\n" +
            "union\n" +
            "select b.construction_unit_name from safe_si_job_cut_road a INNER JOIN safe_si_job_cut_road_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 group by b.construction_unit_name\n" +
            ") as  a"
    )
    Map<String, Object> getConstructionUnitCount(Map<String, Object> condition);

    /**
     * 信息-外委人数
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select count(person_name) as construction_unit_persons_count from (\n" +
            "select b.person_name from safe_si_job_fire a INNER JOIN safe_si_job_fire_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            "union\n" +
            "select b.person_name from safe_si_job_space a INNER JOIN safe_si_job_space_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            "union\n" +
            "select b.person_name from safe_si_job_high a INNER JOIN safe_si_job_high_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            "union\n" +
            "select b.person_name from safe_si_job_hoisting a INNER JOIN safe_si_job_hoisting_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            "union\n" +
            "select b.person_name from safe_si_job_temporary_electricity a INNER JOIN safe_si_job_temporary_electricity_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            "union\n" +
            "select b.person_name from safe_si_job_blind_plate_plugging a INNER JOIN safe_si_job_blind_plate_plugging_persons b ON a.id = b.job_id where a.implementation_time_plugging <= now() and a.implementation_time_take_out >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            "union\n" +
            "select b.person_name from safe_si_job_soil a INNER JOIN safe_si_job_soil_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            "union\n" +
            "select b.person_name from safe_si_job_cut_road a INNER JOIN safe_si_job_cut_road_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by b.person_name\n" +
            ") as  a"
    )
    Map<String, Object> getConstructionUnitPersonsCount(Map<String, Object> condition);

    /**
     * 信息-巡检人数
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select COUNT(*) as check_persons_count from (select check_person_name from safe_si_check_record where check_time >= (DATE_FORMAT(now(),'%Y-%m-%d') + ' 00:00:00') and check_time <= (DATE_FORMAT(now(),'%Y-%m-%d') + ' 23:59:59') group by check_person_name) as a"
    )
    Map<String, Object> getCheckPersonsCount(Map<String, Object> condition);

    /**
     * 安全作业列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select safe_area_name,sum(job_count) as job_count from (\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_fire where start_time <= now() and end_time >= now() and status != 2 group by safe_area_name\n" +
            "union all\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_space where start_time <= now() and end_time >= now() and status != 2 group by safe_area_name\n" +
            "union all\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_high where start_time <= now() and end_time >= now() and status != 2 group by safe_area_name\n" +
            "union all\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_hoisting where start_time <= now() and end_time >= now() and status != 2 group by safe_area_name\n" +
            "union all\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_temporary_electricity where start_time <= now() and end_time >= now() and status != 2 group by safe_area_name\n" +
            "union all\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_blind_plate_plugging where implementation_time_plugging <= now() and implementation_time_take_out >= now() and status != 2 group by safe_area_name\n" +
            "union all\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_soil where start_time <= now() and end_time >= now() and status != 2 group by safe_area_name\n" +
            "union all\n" +
            "select safe_area_name,COUNT(*) as job_count from safe_si_job_cut_road where start_time <= now() and end_time >= now() and status != 2 group by safe_area_name\n" +
            ") as  a group by safe_area_name"
    )
    List<Map<String, Object>> getSafeAreaJobList(Map<String, Object> condition);

    /**
     * 安全巡检列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select safe_area_name,COUNT(*) as check_persons_count from (select safe_area_name,check_person_name from safe_si_check_record where check_time >= (DATE_FORMAT(now(),'%Y-%m-%d') + ' 00:00:00') and check_time <= (DATE_FORMAT(now(),'%Y-%m-%d') + ' 23:59:59') group by safe_area_name,check_person_name) as a group by safe_area_name"
    )
    List<Map<String, Object>> getSafeAreaCheckList(Map<String, Object> condition);

    /**
     * 区域人数列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select safe_area_name,count(person_name) as construction_unit_persons_count from (\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_fire a INNER JOIN safe_si_job_fire_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            "union\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_space a INNER JOIN safe_si_job_space_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            "union\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_high a INNER JOIN safe_si_job_high_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            "union\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_hoisting a INNER JOIN safe_si_job_hoisting_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            "union\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_temporary_electricity a INNER JOIN safe_si_job_temporary_electricity_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            "union\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_blind_plate_plugging a INNER JOIN safe_si_job_blind_plate_plugging_persons b ON a.id = b.job_id where a.implementation_time_plugging <= now() and a.implementation_time_take_out >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            "union\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_soil a INNER JOIN safe_si_job_soil_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            "union\n" +
            "select a.safe_area_name,b.person_name from safe_si_job_cut_road a INNER JOIN safe_si_job_cut_road_persons b ON a.id = b.job_id where a.start_time <= now() and a.end_time >= now() and status != 2 and b.type=2 group by a.safe_area_name,b.person_name\n" +
            ") as  a group by safe_area_name"
    )
    List<Map<String, Object>> getSafeAreaPersonList(Map<String, Object> condition);

    /**
     * 安环采集-安全报警配置列表
     * @param condition 查询条件
     * @return 结果列表
     */
    @Select(
            "select PositionNumber,Area,Range,LL,L,H,HH,Position,Unit from T_SE_DataCollectionPoint"
    )
    List<Map<String, Object>> getSafeAreaAlarmsList(Map<String, Object> condition);
}
