package com.jlkj.human.pa.service;

import com.jlkj.human.pa.domain.YearPerformance;

import java.util.List;

/**
 * 年度绩效主档Service接口
 * 
 * @author 267383
 * @date 2023-06-09
 */
public interface IYearPerformanceService 
{
    /**
     * 查询年度绩效主档
     * 
     * @param id 年度绩效主档主键
     * @return 年度绩效主档
     */
    public YearPerformance selectYearPerformanceById(String id);

    /**
     * 查询年度绩效主档列表
     * 
     * @param yearPerformance 年度绩效主档
     * @return 年度绩效主档集合
     */
    public List<YearPerformance> selectYearPerformanceList(YearPerformance yearPerformance);

    /**
     * 新增年度绩效主档
     * 
     * @param yearPerformance 年度绩效主档
     * @return 结果
     */
    public int insertYearPerformance(YearPerformance yearPerformance);

    /**
     * 修改年度绩效主档
     * 
     * @param yearPerformance 年度绩效主档
     * @return 结果
     */
    public int updateYearPerformance(YearPerformance yearPerformance);

    /**
     * 批量删除年度绩效主档
     * 
     * @param ids 需要删除的年度绩效主档主键集合
     * @return 结果
     */
    public int deleteYearPerformanceByIds(String[] ids);

    /**
     * 删除年度绩效主档信息
     * 
     * @param id 年度绩效主档主键
     * @return 结果
     */
    public int deleteYearPerformanceById(String id);
}
