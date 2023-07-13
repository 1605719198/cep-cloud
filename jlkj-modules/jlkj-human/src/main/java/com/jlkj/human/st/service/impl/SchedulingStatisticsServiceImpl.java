package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.SchedulingStatisticsMapper;
import com.jlkj.human.st.domain.SchedulingStatistics;
import com.jlkj.human.st.service.ISchedulingStatisticsService;

/**
 * 年度排班统计Service业务层处理
 *
 * @author 267383
 * @date 2023-07-11
 * @version 1.0
 */
@Service
public class SchedulingStatisticsServiceImpl implements ISchedulingStatisticsService
{
    @Autowired
    private SchedulingStatisticsMapper schedulingStatisticsMapper;

    /**
     * 查询年度排班统计
     *
     * @author 267383
     * @date 2023-07-11
     * @param id 年度排班统计主键
     * @return 年度排班统计
     */
    @Override
    public SchedulingStatistics selectSchedulingStatisticsById(String id)
    {
        return schedulingStatisticsMapper.selectSchedulingStatisticsById(id);
    }

    /**
     * 查询年度排班统计列表
     *
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics 年度排班统计
     * @return 年度排班统计
     */
    @Override
    public List<SchedulingStatistics> selectSchedulingStatisticsList(SchedulingStatistics schedulingStatistics)
    {
        return schedulingStatisticsMapper.selectSchedulingStatisticsList(schedulingStatistics);
    }

    /**
     * 新增年度排班统计
     *
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics 年度排班统计
     * @return 结果
     */
    @Override
    public int insertSchedulingStatistics(SchedulingStatistics schedulingStatistics)
    {
        return schedulingStatisticsMapper.insertSchedulingStatistics(schedulingStatistics);
    }

    /**
     * 修改年度排班统计
     *
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics 年度排班统计
     * @return 结果
     */
    @Override
    public int updateSchedulingStatistics(SchedulingStatistics schedulingStatistics)
    {
        return schedulingStatisticsMapper.updateSchedulingStatistics(schedulingStatistics);
    }

    /**
     * 批量删除年度排班统计
     *
     * @author 267383
     * @date 2023-07-11
     * @param ids 需要删除的年度排班统计主键
     * @return 结果
     */
    @Override
    public int deleteSchedulingStatisticsByIds(String[] ids)
    {
        return schedulingStatisticsMapper.deleteSchedulingStatisticsByIds(ids);
    }

    /**
     * 删除年度排班统计信息
     *
     * @author 267383
     * @date 2023-07-11
     * @param id 年度排班统计主键
     * @return 结果
     */
    @Override
    public int deleteSchedulingStatisticsById(String id)
    {
        return schedulingStatisticsMapper.deleteSchedulingStatisticsById(id);
    }
}
