package com.jlkj.human.st.mapper;

import java.util.List;
import com.jlkj.human.st.domain.HomeLeaveStatistics;

/**
 * 探亲假统计Mapper接口
 * 
 * @author 267383
 * @date 2023-06-25
 */
public interface HomeLeaveStatisticsMapper 
{
    /**
     * 查询探亲假统计
     * 
     * @param id 探亲假统计主键
     * @return 探亲假统计
     */
    public HomeLeaveStatistics selectHomeLeaveStatisticsById(String id);

    /**
     * 查询探亲假统计列表
     * 
     * @param homeLeaveStatistics 探亲假统计
     * @return 探亲假统计集合
     */
    public List<HomeLeaveStatistics> selectHomeLeaveStatisticsList(HomeLeaveStatistics homeLeaveStatistics);

    /**
     * 新增探亲假统计
     * 
     * @param homeLeaveStatistics 探亲假统计
     * @return 结果
     */
    public int insertHomeLeaveStatistics(HomeLeaveStatistics homeLeaveStatistics);

    /**
     * 修改探亲假统计
     * 
     * @param homeLeaveStatistics 探亲假统计
     * @return 结果
     */
    public int updateHomeLeaveStatistics(HomeLeaveStatistics homeLeaveStatistics);

    /**
     * 删除探亲假统计
     * 
     * @param id 探亲假统计主键
     * @return 结果
     */
    public int deleteHomeLeaveStatisticsById(String id);

    /**
     * 批量删除探亲假统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomeLeaveStatisticsByIds(String[] ids);
}
