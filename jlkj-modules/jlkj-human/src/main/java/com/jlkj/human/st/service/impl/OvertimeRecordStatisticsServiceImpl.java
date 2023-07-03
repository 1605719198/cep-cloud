package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.OvertimeRecordStatisticsMapper;
import com.jlkj.human.st.domain.OvertimeRecordStatistics;
import com.jlkj.human.st.service.IOvertimeRecordStatisticsService;

/**
 * 加班统计Service业务层处理
 *
 * @author 267383
 * @date 2023-07-03
 * @version 1.0
 */
@Service
public class OvertimeRecordStatisticsServiceImpl implements IOvertimeRecordStatisticsService
{
    @Autowired
    private OvertimeRecordStatisticsMapper overtimeRecordStatisticsMapper;

    /**
     * 查询加班统计
     *
     * @author 267383
     * @date 2023-07-03
     * @param id 加班统计主键
     * @return 加班统计
     */
    @Override
    public OvertimeRecordStatistics selectOvertimeRecordStatisticsById(String id)
    {
        return overtimeRecordStatisticsMapper.selectOvertimeRecordStatisticsById(id);
    }

    /**
     * 查询加班统计列表
     *
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics 加班统计
     * @return 加班统计
     */
    @Override
    public List<OvertimeRecordStatistics> selectOvertimeRecordStatisticsList(OvertimeRecordStatistics overtimeRecordStatistics)
    {
        return overtimeRecordStatisticsMapper.selectOvertimeRecordStatisticsList(overtimeRecordStatistics);
    }

    /**
     * 新增加班统计
     *
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics 加班统计
     * @return 结果
     */
    @Override
    public int insertOvertimeRecordStatistics(OvertimeRecordStatistics overtimeRecordStatistics)
    {
        return overtimeRecordStatisticsMapper.insertOvertimeRecordStatistics(overtimeRecordStatistics);
    }

    /**
     * 修改加班统计
     *
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics 加班统计
     * @return 结果
     */
    @Override
    public int updateOvertimeRecordStatistics(OvertimeRecordStatistics overtimeRecordStatistics)
    {
        return overtimeRecordStatisticsMapper.updateOvertimeRecordStatistics(overtimeRecordStatistics);
    }

    /**
     * 批量删除加班统计
     *
     * @author 267383
     * @date 2023-07-03
     * @param ids 需要删除的加班统计主键
     * @return 结果
     */
    @Override
    public int deleteOvertimeRecordStatisticsByIds(String[] ids)
    {
        return overtimeRecordStatisticsMapper.deleteOvertimeRecordStatisticsByIds(ids);
    }

    /**
     * 删除加班统计信息
     *
     * @author 267383
     * @date 2023-07-03
     * @param id 加班统计主键
     * @return 结果
     */
    @Override
    public int deleteOvertimeRecordStatisticsById(String id)
    {
        return overtimeRecordStatisticsMapper.deleteOvertimeRecordStatisticsById(id);
    }
}
