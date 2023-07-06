package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.SaveTimeStatistics;

/**
 * 存班统计Service接口
 * 
 * @author 267383
 * @date 2023-07-05
 */
public interface ISaveTimeStatisticsService 
{
    /**
     * 查询存班统计
     * 
     * @param id 存班统计主键
     * @return 存班统计
     */
    public SaveTimeStatistics selectSaveTimeStatisticsById(String id);

    /**
     * 查询存班统计列表
     *
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics 存班统计
     * @return 存班统计集合
     */
    public List<SaveTimeStatistics> selectSaveTimeStatisticsList(SaveTimeStatistics saveTimeStatistics);

    /**
     * 新增存班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics 存班统计
     * @return 结果
     */
    public int insertSaveTimeStatistics(SaveTimeStatistics saveTimeStatistics);

    /**
     * 修改存班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics 存班统计
     * @return 结果
     */
    public int updateSaveTimeStatistics(SaveTimeStatistics saveTimeStatistics);

    /**
     * 批量删除存班统计
     *
     * @author 267383
     * @date 2023-07-05
     * @param ids 需要删除的存班统计主键集合
     * @return 结果
     */
    public int deleteSaveTimeStatisticsByIds(String[] ids);

    /**
     * 删除存班统计信息
     *
     * @author 267383
     * @date 2023-07-05
     * @param id 存班统计主键
     * @return 结果
     */
    public int deleteSaveTimeStatisticsById(String id);
}
