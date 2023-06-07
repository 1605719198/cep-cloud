package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.YearRate;

import java.util.List;

/**
 * 年度奖金比例设定Service接口
 * 
 * @author 267383
 * @date 2023-06-04
 */
public interface IYearRateService 
{
    /**
     * 查询年度奖金比例设定
     * 
     * @param id 年度奖金比例设定主键
     * @return 年度奖金比例设定
     */
    public YearRate selectYearRateById(String id);

    /**
     * 查询年度奖金比例设定列表
     * 
     * @param yearRate 年度奖金比例设定
     * @return 年度奖金比例设定集合
     */
    public List<YearRate> selectYearRateList(YearRate yearRate);

    /**
     * 保存年度奖金比例设定
     * 
     * @param yearRate 年度奖金比例设定
     * @return 结果
     */
    public int insertYearRate(YearRate yearRate);

    /**
     * 修改年度奖金比例设定
     * 
     * @param yearRate 年度奖金比例设定
     * @return 结果
     */
    public int updateYearRate(YearRate yearRate);

    /**
     * 批量删除年度奖金比例设定
     * 
     * @param ids 需要删除的年度奖金比例设定主键集合
     * @return 结果
     */
    public int deleteYearRateByIds(String[] ids);

    /**
     * 删除年度奖金比例设定信息
     * 
     * @param id 年度奖金比例设定主键
     * @return 结果
     */
    public int deleteYearRateById(String id);
}
