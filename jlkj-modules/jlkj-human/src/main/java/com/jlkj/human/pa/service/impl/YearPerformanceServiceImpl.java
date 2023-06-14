package com.jlkj.human.pa.service.impl;

import com.jlkj.human.pa.domain.YearPerformance;
import com.jlkj.human.pa.mapper.YearPerformanceMapper;
import com.jlkj.human.pa.service.IYearPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 年度绩效主档Service业务层处理
 *
 * @author 267383
 * @date 2023-06-09
 */
@Service
public class YearPerformanceServiceImpl implements IYearPerformanceService
{
    @Autowired
    private YearPerformanceMapper yearPerformanceMapper;

    /**
     * 查询年度绩效主档
     *
     * @param id 年度绩效主档主键
     * @return 年度绩效主档
     */
    @Override
    public YearPerformance selectYearPerformanceById(String id)
    {
        return yearPerformanceMapper.selectYearPerformanceById(id);
    }

    /**
     * 查询年度绩效主档列表
     *
     * @param yearPerformance 年度绩效主档
     * @return 年度绩效主档
     */
    @Override
    public List<YearPerformance> selectYearPerformanceList(YearPerformance yearPerformance)
    {
        return yearPerformanceMapper.selectYearPerformanceList(yearPerformance);
    }

    /**
     * 新增年度绩效主档
     *
     * @param yearPerformance 年度绩效主档
     * @return 结果
     */
    @Override
    public int insertYearPerformance(YearPerformance yearPerformance)
    {
        return yearPerformanceMapper.insertYearPerformance(yearPerformance);
    }

    /**
     * 修改年度绩效主档
     *
     * @param yearPerformance 年度绩效主档
     * @return 结果
     */
    @Override
    public int updateYearPerformance(YearPerformance yearPerformance)
    {
        return yearPerformanceMapper.updateYearPerformance(yearPerformance);
    }

    /**
     * 批量删除年度绩效主档
     *
     * @param ids 需要删除的年度绩效主档主键
     * @return 结果
     */
    @Override
    public int deleteYearPerformanceByIds(String[] ids)
    {
        return yearPerformanceMapper.deleteYearPerformanceByIds(ids);
    }

    /**
     * 删除年度绩效主档信息
     *
     * @param id 年度绩效主档主键
     * @return 结果
     */
    @Override
    public int deleteYearPerformanceById(String id)
    {
        return yearPerformanceMapper.deleteYearPerformanceById(id);
    }
}
