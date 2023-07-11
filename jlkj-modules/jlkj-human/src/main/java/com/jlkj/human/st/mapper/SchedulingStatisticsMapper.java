package com.jlkj.human.st.mapper;

import java.util.List;
import com.jlkj.human.st.domain.SchedulingStatistics;

/**
 * 年度排班统计Mapper接口
 * 
 * @author 267383
 * @date 2023-07-11
 */
public interface SchedulingStatisticsMapper 
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
     * @param schedulingStatistics 年度排班统计
     * @return 年度排班统计集合
     */
    public List<SchedulingStatistics> selectSchedulingStatisticsList(SchedulingStatistics schedulingStatistics);

    /**
     * 新增年度排班统计
     * 
     * @param schedulingStatistics 年度排班统计
     * @return 结果
     */
    public int insertSchedulingStatistics(SchedulingStatistics schedulingStatistics);

    /**
     * 修改年度排班统计
     * 
     * @param schedulingStatistics 年度排班统计
     * @return 结果
     */
    public int updateSchedulingStatistics(SchedulingStatistics schedulingStatistics);

    /**
     * 删除年度排班统计
     * 
     * @param id 年度排班统计主键
     * @return 结果
     */
    public int deleteSchedulingStatisticsById(String id);

    /**
     * 批量删除年度排班统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSchedulingStatisticsByIds(String[] ids);
}
