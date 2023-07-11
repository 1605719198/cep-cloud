package com.jlkj.human.st.service.impl;

import com.jlkj.human.st.domain.HolidayOvertimeStatistics;
import com.jlkj.human.st.mapper.HolidayOvertimeStatisticsMapper;
import com.jlkj.human.st.service.IHolidayOvertimeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 倒班人员法定假日加班统计Service业务层处理
 *
 * @author 267383
 * @date 2023-07-10
 * @version 1.0
 */
@Service
public class HolidayOvertimeStatisticsServiceImpl implements IHolidayOvertimeStatisticsService
{
    @Autowired
    private HolidayOvertimeStatisticsMapper holidayOvertimeStatisticsMapper;

    /**
     * 查询倒班人员法定假日加班统计
     *
     * @author 267383
     * @date 2023-07-10
     * @param id 倒班人员法定假日加班统计主键
     * @return 倒班人员法定假日加班统计
     */
    @Override
    public HolidayOvertimeStatistics selectHolidayOvertimeStatisticsById(String id)
    {
        return holidayOvertimeStatisticsMapper.selectHolidayOvertimeStatisticsById(id);
    }

    /**
     * 查询倒班人员法定假日加班统计列表
     *
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics 倒班人员法定假日加班统计
     * @return 倒班人员法定假日加班统计
     */
    @Override
    public List<HolidayOvertimeStatistics> selectHolidayOvertimeStatisticsList(HolidayOvertimeStatistics holidayOvertimeStatistics)
    {
        return holidayOvertimeStatisticsMapper.selectHolidayOvertimeStatisticsList(holidayOvertimeStatistics);
    }

    /**
     * 新增倒班人员法定假日加班统计
     *
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics 倒班人员法定假日加班统计
     * @return 结果
     */
    @Override
    public int insertHolidayOvertimeStatistics(HolidayOvertimeStatistics holidayOvertimeStatistics)
    {
        return holidayOvertimeStatisticsMapper.insertHolidayOvertimeStatistics(holidayOvertimeStatistics);
    }

    /**
     * 修改倒班人员法定假日加班统计
     *
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics 倒班人员法定假日加班统计
     * @return 结果
     */
    @Override
    public int updateHolidayOvertimeStatistics(HolidayOvertimeStatistics holidayOvertimeStatistics)
    {
        return holidayOvertimeStatisticsMapper.updateHolidayOvertimeStatistics(holidayOvertimeStatistics);
    }

    /**
     * 批量删除倒班人员法定假日加班统计
     *
     * @author 267383
     * @date 2023-07-10
     * @param ids 需要删除的倒班人员法定假日加班统计主键
     * @return 结果
     */
    @Override
    public int deleteHolidayOvertimeStatisticsByIds(String[] ids)
    {
        return holidayOvertimeStatisticsMapper.deleteHolidayOvertimeStatisticsByIds(ids);
    }

    /**
     * 删除倒班人员法定假日加班统计信息
     *
     * @author 267383
     * @date 2023-07-10
     * @param id 倒班人员法定假日加班统计主键
     * @return 结果
     */
    @Override
    public int deleteHolidayOvertimeStatisticsById(String id)
    {
        return holidayOvertimeStatisticsMapper.deleteHolidayOvertimeStatisticsById(id);
    }
}
