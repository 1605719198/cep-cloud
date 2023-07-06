package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.OverTime;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加班汇总（薪酬计算用）Mapper接口
 * 
 * @author 266861
 * @date 2023-06-28
 */
@Repository
public interface OverTimeMapper 
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
     * @param overTime 加班汇总（薪酬计算用）
     * @return 加班汇总（薪酬计算用）集合
     */
    public List<OverTime> selectOverTimeList(OverTime overTime);

    /**
     * 新增加班汇总（薪酬计算用）
     * 
     * @param overTime 加班汇总（薪酬计算用）
     * @return 结果
     */
    public int insertOverTime(OverTime overTime);

    /**
     * 修改加班汇总（薪酬计算用）
     * 
     * @param overTime 加班汇总（薪酬计算用）
     * @return 结果
     */
    public int updateOverTime(OverTime overTime);

    /**
     * 删除加班汇总（薪酬计算用）
     * 
     * @param id 加班汇总（薪酬计算用）主键
     * @return 结果
     */
    public int deleteOverTimeById(String id);

    /**
     * 批量删除加班汇总（薪酬计算用）
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOverTimeByIds(String[] ids);
}
