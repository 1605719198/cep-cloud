package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.YearHolidayStatisticsMapper;
import com.jlkj.human.st.domain.YearHolidayStatistics;
import com.jlkj.human.st.service.IYearHolidayStatisticsService;

/**
 * 年休假统计Service业务层处理
 *
 * @author 267383
 * @date 2023-06-25
 */
@Service
public class YearHolidayStatisticsServiceImpl implements IYearHolidayStatisticsService
{
    @Autowired
    private YearHolidayStatisticsMapper yearHolidayStatisticsMapper;

    /**
     * 查询年休假统计
     *
     * @param id 年休假统计主键
     * @return 年休假统计
     */
    @Override
    public YearHolidayStatistics selectYearHolidayStatisticsById(String id)
    {
        return yearHolidayStatisticsMapper.selectYearHolidayStatisticsById(id);
    }

    /**
     * 查询年休假统计列表
     *
     * @param yearHolidayStatistics 年休假统计
     * @return 年休假统计
     */
    @Override
    public List<YearHolidayStatistics> selectYearHolidayStatisticsList(YearHolidayStatistics yearHolidayStatistics)
    {
        return yearHolidayStatisticsMapper.selectYearHolidayStatisticsList(yearHolidayStatistics);
    }

    /**
     * 新增年休假统计
     *
     * @param yearHolidayStatistics 年休假统计
     * @return 结果
     */
    @Override
    public int insertYearHolidayStatistics(YearHolidayStatistics yearHolidayStatistics)
    {
        return yearHolidayStatisticsMapper.insertYearHolidayStatistics(yearHolidayStatistics);
    }

    /**
     * 修改年休假统计
     *
     * @param yearHolidayStatistics 年休假统计
     * @return 结果
     */
    @Override
    public int updateYearHolidayStatistics(YearHolidayStatistics yearHolidayStatistics)
    {
        return yearHolidayStatisticsMapper.updateYearHolidayStatistics(yearHolidayStatistics);
    }

    /**
     * 批量删除年休假统计
     *
     * @param ids 需要删除的年休假统计主键
     * @return 结果
     */
    @Override
    public int deleteYearHolidayStatisticsByIds(String[] ids)
    {
        return yearHolidayStatisticsMapper.deleteYearHolidayStatisticsByIds(ids);
    }

    /**
     * 删除年休假统计信息
     *
     * @param id 年休假统计主键
     * @return 结果
     */
    @Override
    public int deleteYearHolidayStatisticsById(String id)
    {
        return yearHolidayStatisticsMapper.deleteYearHolidayStatisticsById(id);
    }
}
