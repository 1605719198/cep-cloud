package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.OverTime;

import java.util.List;

/**
 * 加班汇总（薪酬计算用）Service接口
 * 
 * @author 266861
 * @date 2023-06-28
 */
public interface IOverTimeService 
{
    /**
     * 查询加班汇总（薪酬计算用）
     * 
     * @param id 加班汇总（薪酬计算用）主键
     * @return 加班汇总（薪酬计算用）
     */
    public OverTime selectOverTimeById(String id);

    /**
     * 查询加班汇总（薪酬计算用）列表
     *
     * @author 266861
     * @date 2023-06-28
     * @param overTime 加班汇总（薪酬计算用）
     * @return 加班汇总（薪酬计算用）集合
     */
    public List<OverTime> selectOverTimeList(OverTime overTime);

    /**
     * 新增加班汇总（薪酬计算用）
     *
     * @author 266861
     * @date 2023-06-28
     * @param overTimeList 加班汇总（薪酬计算用）列表
     * @return 结果
     */
    public int insertOverTime(List<OverTime> overTimeList);

    /**
     * 修改加班汇总（薪酬计算用）
     *
     * @author 266861
     * @date 2023-06-28
     * @param overTime 加班汇总（薪酬计算用）
     * @return 结果
     */
    public int updateOverTime(OverTime overTime);

    /**
     * 批量删除加班汇总（薪酬计算用）
     *
     * @author 266861
     * @date 2023-06-28
     * @param ids 需要删除的加班汇总（薪酬计算用）主键集合
     * @return 结果
     */
    public int deleteOverTimeByIds(String[] ids);

    /**
     * 删除加班汇总（薪酬计算用）信息
     *
     * @author 266861
     * @date 2023-06-28
     * @param id 加班汇总（薪酬计算用）主键
     * @return 结果
     */
    public int deleteOverTimeById(String id);
}
