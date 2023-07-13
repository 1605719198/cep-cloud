package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.SchedulingStatistics;

/**
 * 年度排班统计Service接口
 * 
 * @author 267383
 * @date 2023-07-11
 */
public interface ISchedulingStatisticsService 
{
    /**
     * 查询年度排班统计
     * 
     * @param id 年度排班统计主键
     * @return 年度排班统计
     */
    public SchedulingStatistics selectSchedulingStatisticsById(String id);

    /**
     * 查询年度排班统计列表
     *
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics 年度排班统计
     * @return 年度排班统计集合
     */
    public List<SchedulingStatistics> selectSchedulingStatisticsList(SchedulingStatistics schedulingStatistics);

    /**
     * 新增年度排班统计
     *
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics 年度排班统计
     * @return 结果
     */
    public int insertSchedulingStatistics(SchedulingStatistics schedulingStatistics);

    /**
     * 修改年度排班统计
     *
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics 年度排班统计
     * @return 结果
     */
    public int updateSchedulingStatistics(SchedulingStatistics schedulingStatistics);

    /**
     * 批量删除年度排班统计
     *
     * @author 267383
     * @date 2023-07-11
     * @param ids 需要删除的年度排班统计主键集合
     * @return 结果
     */
    public int deleteSchedulingStatisticsByIds(String[] ids);

    /**
     * 删除年度排班统计信息
     *
     * @author 267383
     * @date 2023-07-11
     * @param id 年度排班统计主键
     * @return 结果
     */
    public int deleteSchedulingStatisticsById(String id);
}
