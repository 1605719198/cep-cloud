package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.NightDutyStatisticsMapper;
import com.jlkj.human.st.domain.NightDutyStatistics;
import com.jlkj.human.st.service.INightDutyStatisticsService;

/**
 * 夜班统计Service业务层处理
 *
 * @author 267383
 * @date 2023-07-05
 * @version 1.0
 */
@Service
public class NightDutyStatisticsServiceImpl implements INightDutyStatisticsService
{
    @Autowired
    private NightDutyStatisticsMapper nightDutyStatisticsMapper;

    /**
     * 查询夜班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param id 夜班统计主键
     * @return 夜班统计
     */
    @Override
    public NightDutyStatistics selectNightDutyStatisticsById(String id)
    {
        return nightDutyStatisticsMapper.selectNightDutyStatisticsById(id);
    }

    /**
     * 查询夜班统计列表
     *
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics 夜班统计
     * @return 夜班统计
     */
    @Override
    public List<NightDutyStatistics> selectNightDutyStatisticsList(NightDutyStatistics nightDutyStatistics)
    {
        return nightDutyStatisticsMapper.selectNightDutyStatisticsList(nightDutyStatistics);
    }

    /**
     * 新增夜班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics 夜班统计
     * @return 结果
     */
    @Override
    public int insertNightDutyStatistics(NightDutyStatistics nightDutyStatistics)
    {
        return nightDutyStatisticsMapper.insertNightDutyStatistics(nightDutyStatistics);
    }

    /**
     * 修改夜班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics 夜班统计
     * @return 结果
     */
    @Override
    public int updateNightDutyStatistics(NightDutyStatistics nightDutyStatistics)
    {
        return nightDutyStatisticsMapper.updateNightDutyStatistics(nightDutyStatistics);
    }

    /**
     * 批量删除夜班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param ids 需要删除的夜班统计主键
     * @return 结果
     */
    @Override
    public int deleteNightDutyStatisticsByIds(String[] ids)
    {
        return nightDutyStatisticsMapper.deleteNightDutyStatisticsByIds(ids);
    }

    /**
     * 删除夜班统计信息
     *
     * @author 267383
     * @date 2023-07-05
     * @param id 夜班统计主键
     * @return 结果
     */
    @Override
    public int deleteNightDutyStatisticsById(String id)
    {
        return nightDutyStatisticsMapper.deleteNightDutyStatisticsById(id);
    }
}
