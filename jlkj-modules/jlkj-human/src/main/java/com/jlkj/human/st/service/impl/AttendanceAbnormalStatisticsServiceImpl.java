package com.jlkj.human.st.service.impl;

import com.jlkj.human.st.domain.AttendanceAbnormalStatistics;
import com.jlkj.human.st.mapper.AttendanceAbnormalStatisticsMapper;
import com.jlkj.human.st.service.IAttendanceAbnormalStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出勤异常统计Service业务层处理
 *
 * @author 267383
 * @date 2023-06-18
 */
@Service
public class AttendanceAbnormalStatisticsServiceImpl implements IAttendanceAbnormalStatisticsService
{
    @Autowired
    private AttendanceAbnormalStatisticsMapper attendanceAbnormalStatisticsMapper;

    /**
     * 查询出勤异常统计
     *
     * @param id 出勤异常统计主键
     * @return 出勤异常统计
     */
    @Override
    public AttendanceAbnormalStatistics selectAttendanceAbnormalStatisticsById(String id)
    {
        return attendanceAbnormalStatisticsMapper.selectAttendanceAbnormalStatisticsById(id);
    }

    /**
     * 查询出勤异常统计列表
     *
     * @param attendanceAbnormalStatistics 出勤异常统计
     * @return 出勤异常统计
     */
    @Override
    public List<AttendanceAbnormalStatistics> selectAttendanceAbnormalStatisticsList(AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        return attendanceAbnormalStatisticsMapper.selectAttendanceAbnormalStatisticsList(attendanceAbnormalStatistics);
    }

    /**
     * 查询缺勤统计列表
     *
     * @param attendanceAbnormalStatistics 缺勤统计
     * @return 出勤异常统计
     */
    @Override
    public List<AttendanceAbnormalStatistics> selectAbsenceStatisticsList(AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        return attendanceAbnormalStatisticsMapper.selectAbsenceStatisticsList(attendanceAbnormalStatistics);
    }

    /**
     * 新增出勤异常统计
     *
     * @param attendanceAbnormalStatistics 出勤异常统计
     * @return 结果
     */
    @Override
    public int insertAttendanceAbnormalStatistics(AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        return attendanceAbnormalStatisticsMapper.insertAttendanceAbnormalStatistics(attendanceAbnormalStatistics);
    }

    /**
     * 修改出勤异常统计
     *
     * @param attendanceAbnormalStatistics 出勤异常统计
     * @return 结果
     */
    @Override
    public int updateAttendanceAbnormalStatistics(AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        return attendanceAbnormalStatisticsMapper.updateAttendanceAbnormalStatistics(attendanceAbnormalStatistics);
    }

    /**
     * 批量删除出勤异常统计
     *
     * @param ids 需要删除的出勤异常统计主键
     * @return 结果
     */
    @Override
    public int deleteAttendanceAbnormalStatisticsByIds(String[] ids)
    {
        return attendanceAbnormalStatisticsMapper.deleteAttendanceAbnormalStatisticsByIds(ids);
    }

    /**
     * 删除出勤异常统计信息
     *
     * @param id 出勤异常统计主键
     * @return 结果
     */
    @Override
    public int deleteAttendanceAbnormalStatisticsById(String id)
    {
        return attendanceAbnormalStatisticsMapper.deleteAttendanceAbnormalStatisticsById(id);
    }
}
