package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.TravelStatistics;

/**
 * 出差统计Service接口
 * 
 * @author 267383
 * @date 2023-06-24
 */
public interface ITravelStatisticsService 
{
    /**
     * 查询出差统计
     * 
     * @param id 出差统计主键
     * @return 出差统计
     */
    public TravelStatistics selectTravelStatisticsById(String id);

    /**
     * 查询出差统计列表
     * 
     * @param travelStatistics 出差统计
     * @return 出差统计集合
     */
    public List<TravelStatistics> selectTravelStatisticsList(TravelStatistics travelStatistics);

    /**
     * 新增出差统计
     * 
     * @param travelStatistics 出差统计
     * @return 结果
     */
    public int insertTravelStatistics(TravelStatistics travelStatistics);

    /**
     * 修改出差统计
     * 
     * @param travelStatistics 出差统计
     * @return 结果
     */
    public int updateTravelStatistics(TravelStatistics travelStatistics);

    /**
     * 批量删除出差统计
     * 
     * @param ids 需要删除的出差统计主键集合
     * @return 结果
     */
    public int deleteTravelStatisticsByIds(String[] ids);

    /**
     * 删除出差统计信息
     * 
     * @param id 出差统计主键
     * @return 结果
     */
    public int deleteTravelStatisticsById(String id);
}
