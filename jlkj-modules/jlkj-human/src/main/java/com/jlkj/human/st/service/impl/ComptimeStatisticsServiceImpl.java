package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.ComptimeStatisticsMapper;
import com.jlkj.human.st.domain.ComptimeStatistics;
import com.jlkj.human.st.service.IComptimeStatisticsService;

/**
 * 补休统计Service业务层处理
 *
 * @author 267383
 * @date 2023-07-04
 * @version 1.0
 */
@Service
public class ComptimeStatisticsServiceImpl implements IComptimeStatisticsService
{
    @Autowired
    private ComptimeStatisticsMapper comptimeStatisticsMapper;

    /**
     * 查询补休统计
     *
     * @author 267383
     * @date 2023-07-04
     * @param id 补休统计主键
     * @return 补休统计
     */
    @Override
    public ComptimeStatistics selectComptimeStatisticsById(String id)
    {
        return comptimeStatisticsMapper.selectComptimeStatisticsById(id);
    }

    /**
     * 查询补休统计列表
     *
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics 补休统计
     * @return 补休统计
     */
    @Override
    public List<ComptimeStatistics> selectComptimeStatisticsList(ComptimeStatistics comptimeStatistics)
    {
        return comptimeStatisticsMapper.selectComptimeStatisticsList(comptimeStatistics);
    }

    /**
     * 新增补休统计
     *
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics 补休统计
     * @return 结果
     */
    @Override
    public int insertComptimeStatistics(ComptimeStatistics comptimeStatistics)
    {
        return comptimeStatisticsMapper.insertComptimeStatistics(comptimeStatistics);
    }

    /**
     * 修改补休统计
     *
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics 补休统计
     * @return 结果
     */
    @Override
    public int updateComptimeStatistics(ComptimeStatistics comptimeStatistics)
    {
        return comptimeStatisticsMapper.updateComptimeStatistics(comptimeStatistics);
    }

    /**
     * 批量删除补休统计
     *
     * @author 267383
     * @date 2023-07-04
     * @param ids 需要删除的补休统计主键
     * @return 结果
     */
    @Override
    public int deleteComptimeStatisticsByIds(String[] ids)
    {
        return comptimeStatisticsMapper.deleteComptimeStatisticsByIds(ids);
    }

    /**
     * 删除补休统计信息
     *
     * @author 267383
     * @date 2023-07-04
     * @param id 补休统计主键
     * @return 结果
     */
    @Override
    public int deleteComptimeStatisticsById(String id)
    {
        return comptimeStatisticsMapper.deleteComptimeStatisticsById(id);
    }
}
