package com.jlkj.human.st.mapper;

import com.jlkj.human.st.domain.AttendanceAbnormalStatistics;

import java.util.List;

/**
 * 出勤异常统计Mapper接口
 * 
 * @author 267383
 * @date 2023-06-18
 */
public interface AttendanceAbnormalStatisticsMapper 
{
    /**
     * 查询出勤异常统计
     * 
     * @param id 出勤异常统计主键
     * @return 出勤异常统计
     */
    public AttendanceAbnormalStatistics selectAttendanceAbnormalStatisticsById(String id);

    /**
     * 查询出勤异常统计列表
     * 
     * @param attendanceAbnormalStatistics 出勤异常统计
     * @return 出勤异常统计集合
     */
    public List<AttendanceAbnormalStatistics> selectAttendanceAbnormalStatisticsList(AttendanceAbnormalStatistics attendanceAbnormalStatistics);

    /**
     * 新增出勤异常统计
     * 
     * @param attendanceAbnormalStatistics 出勤异常统计
     * @return 结果
     */
    public int insertAttendanceAbnormalStatistics(AttendanceAbnormalStatistics attendanceAbnormalStatistics);

    /**
     * 修改出勤异常统计
     * 
     * @param attendanceAbnormalStatistics 出勤异常统计
     * @return 结果
     */
    public int updateAttendanceAbnormalStatistics(AttendanceAbnormalStatistics attendanceAbnormalStatistics);

    /**
     * 删除出勤异常统计
     * 
     * @param id 出勤异常统计主键
     * @return 结果
     */
    public int deleteAttendanceAbnormalStatisticsById(String id);

    /**
     * 批量删除出勤异常统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttendanceAbnormalStatisticsByIds(String[] ids);
}
