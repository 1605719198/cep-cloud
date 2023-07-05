package com.jlkj.human.st.mapper;

import java.util.List;
import com.jlkj.human.st.domain.SaveTimeStatistics;

/**
 * 存班统计Mapper接口
 * 
 * @author 267383
 * @date 2023-07-05
 */
public interface SaveTimeStatisticsMapper 
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
     * @param saveTimeStatistics 存班统计
     * @return 存班统计集合
     */
    public List<SaveTimeStatistics> selectSaveTimeStatisticsList(SaveTimeStatistics saveTimeStatistics);

    /**
     * 新增存班统计
     * 
     * @param saveTimeStatistics 存班统计
     * @return 结果
     */
    public int insertSaveTimeStatistics(SaveTimeStatistics saveTimeStatistics);

    /**
     * 修改存班统计
     * 
     * @param saveTimeStatistics 存班统计
     * @return 结果
     */
    public int updateSaveTimeStatistics(SaveTimeStatistics saveTimeStatistics);

    /**
     * 删除存班统计
     * 
     * @param id 存班统计主键
     * @return 结果
     */
    public int deleteSaveTimeStatisticsById(String id);

    /**
     * 批量删除存班统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSaveTimeStatisticsByIds(String[] ids);
}
