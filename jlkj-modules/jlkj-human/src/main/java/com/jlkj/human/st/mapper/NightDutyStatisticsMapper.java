package com.jlkj.human.st.mapper;

import java.util.List;
import com.jlkj.human.st.domain.NightDutyStatistics;

/**
 * 夜班统计Mapper接口
 * 
 * @author 267383
 * @date 2023-07-05
 */
public interface NightDutyStatisticsMapper 
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
     * @param nightDutyStatistics 夜班统计
     * @return 夜班统计集合
     */
    public List<NightDutyStatistics> selectNightDutyStatisticsList(NightDutyStatistics nightDutyStatistics);

    /**
     * 新增夜班统计
     * 
     * @param nightDutyStatistics 夜班统计
     * @return 结果
     */
    public int insertNightDutyStatistics(NightDutyStatistics nightDutyStatistics);

    /**
     * 修改夜班统计
     * 
     * @param nightDutyStatistics 夜班统计
     * @return 结果
     */
    public int updateNightDutyStatistics(NightDutyStatistics nightDutyStatistics);

    /**
     * 删除夜班统计
     * 
     * @param id 夜班统计主键
     * @return 结果
     */
    public int deleteNightDutyStatisticsById(String id);

    /**
     * 批量删除夜班统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNightDutyStatisticsByIds(String[] ids);
}
