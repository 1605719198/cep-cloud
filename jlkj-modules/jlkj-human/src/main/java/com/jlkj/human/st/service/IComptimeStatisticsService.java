package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.ComptimeStatistics;

/**
 * 补休统计Service接口
 * 
 * @author 267383
 * @date 2023-07-04
 */
public interface IComptimeStatisticsService 
{
    /**
     * 查询补休统计
     * 
     * @param id 补休统计主键
     * @return 补休统计
     */
    public ComptimeStatistics selectComptimeStatisticsById(String id);

    /**
     * 查询补休统计列表
     *
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics 补休统计
     * @return 补休统计集合
     */
    public List<ComptimeStatistics> selectComptimeStatisticsList(ComptimeStatistics comptimeStatistics);

    /**
     * 新增补休统计
     *
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics 补休统计
     * @return 结果
     */
    public int insertComptimeStatistics(ComptimeStatistics comptimeStatistics);

    /**
     * 修改补休统计
     *
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics 补休统计
     * @return 结果
     */
    public int updateComptimeStatistics(ComptimeStatistics comptimeStatistics);

    /**
     * 批量删除补休统计
     *
     * @author 267383
     * @date 2023-07-04
     * @param ids 需要删除的补休统计主键集合
     * @return 结果
     */
    public int deleteComptimeStatisticsByIds(String[] ids);

    /**
     * 删除补休统计信息
     *
     * @author 267383
     * @date 2023-07-04
     * @param id 补休统计主键
     * @return 结果
     */
    public int deleteComptimeStatisticsById(String id);
}
