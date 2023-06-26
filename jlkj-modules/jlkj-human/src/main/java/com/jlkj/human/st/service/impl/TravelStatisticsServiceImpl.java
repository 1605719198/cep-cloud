package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.TravelStatisticsMapper;
import com.jlkj.human.st.domain.TravelStatistics;
import com.jlkj.human.st.service.ITravelStatisticsService;

/**
 * 出差统计Service业务层处理
 *
 * @author 267383
 * @date 2023-06-24
 */
@Service
public class TravelStatisticsServiceImpl implements ITravelStatisticsService
{
    @Autowired
    private TravelStatisticsMapper travelStatisticsMapper;

    /**
     * 查询出差统计
     *
     * @param id 出差统计主键
     * @return 出差统计
     */
    @Override
    public TravelStatistics selectTravelStatisticsById(String id)
    {
        return travelStatisticsMapper.selectTravelStatisticsById(id);
    }

    /**
     * 查询出差统计列表
     *
     * @param travelStatistics 出差统计
     * @return 出差统计
     */
    @Override
    public List<TravelStatistics> selectTravelStatisticsList(TravelStatistics travelStatistics)
    {
        return travelStatisticsMapper.selectTravelStatisticsList(travelStatistics);
    }

    /**
     * 新增出差统计
     *
     * @param travelStatistics 出差统计
     * @return 结果
     */
    @Override
    public int insertTravelStatistics(TravelStatistics travelStatistics)
    {
        return travelStatisticsMapper.insertTravelStatistics(travelStatistics);
    }

    /**
     * 修改出差统计
     *
     * @param travelStatistics 出差统计
     * @return 结果
     */
    @Override
    public int updateTravelStatistics(TravelStatistics travelStatistics)
    {
        return travelStatisticsMapper.updateTravelStatistics(travelStatistics);
    }

    /**
     * 批量删除出差统计
     *
     * @param ids 需要删除的出差统计主键
     * @return 结果
     */
    @Override
    public int deleteTravelStatisticsByIds(String[] ids)
    {
        return travelStatisticsMapper.deleteTravelStatisticsByIds(ids);
    }

    /**
     * 删除出差统计信息
     *
     * @param id 出差统计主键
     * @return 结果
     */
    @Override
    public int deleteTravelStatisticsById(String id)
    {
        return travelStatisticsMapper.deleteTravelStatisticsById(id);
    }
}
