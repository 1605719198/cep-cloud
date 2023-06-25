package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.HomeLeaveStatisticsMapper;
import com.jlkj.human.st.domain.HomeLeaveStatistics;
import com.jlkj.human.st.service.IHomeLeaveStatisticsService;

/**
 * 探亲假统计Service业务层处理
 *
 * @author 267383
 * @date 2023-06-25
 */
@Service
public class HomeLeaveStatisticsServiceImpl implements IHomeLeaveStatisticsService
{
    @Autowired
    private HomeLeaveStatisticsMapper homeLeaveStatisticsMapper;

    /**
     * 查询探亲假统计
     *
     * @param id 探亲假统计主键
     * @return 探亲假统计
     */
    @Override
    public HomeLeaveStatistics selectHomeLeaveStatisticsById(String id)
    {
        return homeLeaveStatisticsMapper.selectHomeLeaveStatisticsById(id);
    }

    /**
     * 查询探亲假统计列表
     *
     * @param homeLeaveStatistics 探亲假统计
     * @return 探亲假统计
     */
    @Override
    public List<HomeLeaveStatistics> selectHomeLeaveStatisticsList(HomeLeaveStatistics homeLeaveStatistics)
    {
        return homeLeaveStatisticsMapper.selectHomeLeaveStatisticsList(homeLeaveStatistics);
    }

    /**
     * 新增探亲假统计
     *
     * @param homeLeaveStatistics 探亲假统计
     * @return 结果
     */
    @Override
    public int insertHomeLeaveStatistics(HomeLeaveStatistics homeLeaveStatistics)
    {
        return homeLeaveStatisticsMapper.insertHomeLeaveStatistics(homeLeaveStatistics);
    }

    /**
     * 修改探亲假统计
     *
     * @param homeLeaveStatistics 探亲假统计
     * @return 结果
     */
    @Override
    public int updateHomeLeaveStatistics(HomeLeaveStatistics homeLeaveStatistics)
    {
        return homeLeaveStatisticsMapper.updateHomeLeaveStatistics(homeLeaveStatistics);
    }

    /**
     * 批量删除探亲假统计
     *
     * @param ids 需要删除的探亲假统计主键
     * @return 结果
     */
    @Override
    public int deleteHomeLeaveStatisticsByIds(String[] ids)
    {
        return homeLeaveStatisticsMapper.deleteHomeLeaveStatisticsByIds(ids);
    }

    /**
     * 删除探亲假统计信息
     *
     * @param id 探亲假统计主键
     * @return 结果
     */
    @Override
    public int deleteHomeLeaveStatisticsById(String id)
    {
        return homeLeaveStatisticsMapper.deleteHomeLeaveStatisticsById(id);
    }
}
