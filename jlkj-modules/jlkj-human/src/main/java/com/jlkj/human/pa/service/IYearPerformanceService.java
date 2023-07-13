package com.jlkj.human.pa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.pa.domain.YearPerformance;
import com.jlkj.human.pa.dto.YearPerformanceDTO;

import java.util.List;

/**
 * 年度绩效主档Service接口
 * 
 * @author 267383
 * @date 2023-06-09
 */
public interface IYearPerformanceService extends IService<YearPerformance>
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
     * 查询年度绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param yearPerformance 查询参数集
     * @return list 分页数据
     */
    List<YearPerformance> listYearPerformance(YearPerformance yearPerformance);

    /**
     * 新增年度绩效主档
     * 
     * @param yearPerformance 年度绩效主档
     * @return 结果
     */
    public Object insertYearPerformance(YearPerformance yearPerformance);

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

    /**
     * 呈送计划
     * @author HuangBing
     * @date 2023-6-20
     * @param yearPerformanceDTO 表格数据
     * @return 呈送结果
     */
    Object sendYearPerformance(YearPerformanceDTO yearPerformanceDTO);

    /**
     * 最终复核
     * @author HuangBing
     * @date 2023-6-20
     * @param yearPerformanceDTO 表格数据
     * @return 最终复核结果
     */
    Object finalCheckYearPerformance(YearPerformanceDTO yearPerformanceDTO);

    /**
     * 导入年度绩效数据
     *
     * @param yearPerformanceList 年度绩效数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
   String importYearPerformance(List<YearPerformance> yearPerformanceList, Boolean isUpdateSupport, String operName);
}
