package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.SaveTimeStatisticsMapper;
import com.jlkj.human.st.domain.SaveTimeStatistics;
import com.jlkj.human.st.service.ISaveTimeStatisticsService;

/**
 * 存班统计Service业务层处理
 *
 * @author 267383
 * @date 2023-07-05
 * @version 1.0
 */
@Service
public class SaveTimeStatisticsServiceImpl implements ISaveTimeStatisticsService
{
    @Autowired
    private SaveTimeStatisticsMapper saveTimeStatisticsMapper;

    /**
     * 查询存班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param id 存班统计主键
     * @return 存班统计
     */
    @Override
    public SaveTimeStatistics selectSaveTimeStatisticsById(String id)
    {
        return saveTimeStatisticsMapper.selectSaveTimeStatisticsById(id);
    }

    /**
     * 查询存班统计列表
     *
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics 存班统计
     * @return 存班统计
     */
    @Override
    public List<SaveTimeStatistics> selectSaveTimeStatisticsList(SaveTimeStatistics saveTimeStatistics)
    {
        return saveTimeStatisticsMapper.selectSaveTimeStatisticsList(saveTimeStatistics);
    }

    /**
     * 新增存班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics 存班统计
     * @return 结果
     */
    @Override
    public int insertSaveTimeStatistics(SaveTimeStatistics saveTimeStatistics)
    {
        return saveTimeStatisticsMapper.insertSaveTimeStatistics(saveTimeStatistics);
    }

    /**
     * 修改存班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics 存班统计
     * @return 结果
     */
    @Override
    public int updateSaveTimeStatistics(SaveTimeStatistics saveTimeStatistics)
    {
        return saveTimeStatisticsMapper.updateSaveTimeStatistics(saveTimeStatistics);
    }

    /**
     * 批量删除存班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param ids 需要删除的存班统计主键
     * @return 结果
     */
    @Override
    public int deleteSaveTimeStatisticsByIds(String[] ids)
    {
        return saveTimeStatisticsMapper.deleteSaveTimeStatisticsByIds(ids);
    }

    /**
     * 删除存班统计信息
     *
     * @author 267383
     * @date 2023-07-05
     * @param id 存班统计主键
     * @return 结果
     */
    @Override
    public int deleteSaveTimeStatisticsById(String id)
    {
        return saveTimeStatisticsMapper.deleteSaveTimeStatisticsById(id);
    }
}
