package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.NightDutyStatistics;

/**
 * 夜班统计Service接口
 * 
 * @author 267383
 * @date 2023-07-05
 */
public interface INightDutyStatisticsService 
{
    /**
     * 查询夜班统计
     * 
     * @param id 夜班统计主键
     * @return 夜班统计
     */
    public NightDutyStatistics selectNightDutyStatisticsById(String id);

    /**
     * 查询夜班统计列表
     *
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics 夜班统计
     * @return 夜班统计集合
     */
    public List<NightDutyStatistics> selectNightDutyStatisticsList(NightDutyStatistics nightDutyStatistics);

    /**
     * 新增夜班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics 夜班统计
     * @return 结果
     */
    public int insertNightDutyStatistics(NightDutyStatistics nightDutyStatistics);

    /**
     * 修改夜班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics 夜班统计
     * @return 结果
     */
    public int updateNightDutyStatistics(NightDutyStatistics nightDutyStatistics);

    /**
     * 批量删除夜班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param ids 需要删除的夜班统计主键集合
     * @return 结果
     */
    public int deleteNightDutyStatisticsByIds(String[] ids);

    /**
     * 删除夜班统计信息
     *
     * @author 267383
     * @date 2023-07-05
     * @param id 夜班统计主键
     * @return 结果
     */
    public int deleteNightDutyStatisticsById(String id);
}
