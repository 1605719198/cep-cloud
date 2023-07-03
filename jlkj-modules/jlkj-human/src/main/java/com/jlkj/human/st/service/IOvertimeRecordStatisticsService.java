package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.OvertimeRecordStatistics;

/**
 * 加班统计Service接口
 * 
 * @author 267383
 * @date 2023-07-03
 */
public interface IOvertimeRecordStatisticsService 
{
    /**
     * 查询加班统计
     * 
     * @param id 加班统计主键
     * @return 加班统计
     */
    public OvertimeRecordStatistics selectOvertimeRecordStatisticsById(String id);

    /**
     * 查询加班统计列表
     *
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics 加班统计
     * @return 加班统计集合
     */
    public List<OvertimeRecordStatistics> selectOvertimeRecordStatisticsList(OvertimeRecordStatistics overtimeRecordStatistics);

    /**
     * 新增加班统计
     *
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics 加班统计
     * @return 结果
     */
    public int insertOvertimeRecordStatistics(OvertimeRecordStatistics overtimeRecordStatistics);

    /**
     * 修改加班统计
     *
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics 加班统计
     * @return 结果
     */
    public int updateOvertimeRecordStatistics(OvertimeRecordStatistics overtimeRecordStatistics);

    /**
     * 批量删除加班统计
     *
     * @author 267383
     * @date 2023-07-03
     * @param ids 需要删除的加班统计主键集合
     * @return 结果
     */
    public int deleteOvertimeRecordStatisticsByIds(String[] ids);

    /**
     * 删除加班统计信息
     *
     * @author 267383
     * @date 2023-07-03
     * @param id 加班统计主键
     * @return 结果
     */
    public int deleteOvertimeRecordStatisticsById(String id);
}
