package com.jlkj.human.st.service;

import com.jlkj.human.st.domain.HolidayOvertimeStatistics;

import java.util.List;

/**
 * 倒班人员法定假日加班统计Service接口
 * 
 * @author 267383
 * @date 2023-07-10
 */
public interface IHolidayOvertimeStatisticsService 
{
    /**
     * 查询倒班人员法定假日加班统计
     * 
     * @param id 倒班人员法定假日加班统计主键
     * @return 倒班人员法定假日加班统计
     */
    public HolidayOvertimeStatistics selectHolidayOvertimeStatisticsById(String id);

    /**
     * 查询倒班人员法定假日加班统计列表
     *
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics 倒班人员法定假日加班统计
     * @return 倒班人员法定假日加班统计集合
     */
    public List<HolidayOvertimeStatistics> selectHolidayOvertimeStatisticsList(HolidayOvertimeStatistics holidayOvertimeStatistics);

    /**
     * 新增倒班人员法定假日加班统计
     *
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics 倒班人员法定假日加班统计
     * @return 结果
     */
    public int insertHolidayOvertimeStatistics(HolidayOvertimeStatistics holidayOvertimeStatistics);

    /**
     * 修改倒班人员法定假日加班统计
     *
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics 倒班人员法定假日加班统计
     * @return 结果
     */
    public int updateHolidayOvertimeStatistics(HolidayOvertimeStatistics holidayOvertimeStatistics);

    /**
     * 批量删除倒班人员法定假日加班统计
     *
     * @author 267383
     * @date 2023-07-10
     * @param ids 需要删除的倒班人员法定假日加班统计主键集合
     * @return 结果
     */
    public int deleteHolidayOvertimeStatisticsByIds(String[] ids);

    /**
     * 删除倒班人员法定假日加班统计信息
     *
     * @author 267383
     * @date 2023-07-10
     * @param id 倒班人员法定假日加班统计主键
     * @return 结果
     */
    public int deleteHolidayOvertimeStatisticsById(String id);
}
