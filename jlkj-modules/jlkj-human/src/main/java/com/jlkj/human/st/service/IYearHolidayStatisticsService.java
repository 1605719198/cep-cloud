package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.YearHolidayStatistics;

/**
 * 年休假统计Service接口
 * 
 * @author 267383
 * @date 2023-06-25
 */
public interface IYearHolidayStatisticsService 
{
    /**
     * 查询年休假统计
     * 
     * @param id 年休假统计主键
     * @return 年休假统计
     */
    public YearHolidayStatistics selectYearHolidayStatisticsById(String id);

    /**
     * 查询年休假统计列表
     * 
     * @param yearHolidayStatistics 年休假统计
     * @return 年休假统计集合
     */
    public List<YearHolidayStatistics> selectYearHolidayStatisticsList(YearHolidayStatistics yearHolidayStatistics);

    /**
     * 新增年休假统计
     * 
     * @param yearHolidayStatistics 年休假统计
     * @return 结果
     */
    public int insertYearHolidayStatistics(YearHolidayStatistics yearHolidayStatistics);

    /**
     * 修改年休假统计
     * 
     * @param yearHolidayStatistics 年休假统计
     * @return 结果
     */
    public int updateYearHolidayStatistics(YearHolidayStatistics yearHolidayStatistics);

    /**
     * 批量删除年休假统计
     * 
     * @param ids 需要删除的年休假统计主键集合
     * @return 结果
     */
    public int deleteYearHolidayStatisticsByIds(String[] ids);

    /**
     * 删除年休假统计信息
     * 
     * @param id 年休假统计主键
     * @return 结果
     */
    public int deleteYearHolidayStatisticsById(String id);
}
