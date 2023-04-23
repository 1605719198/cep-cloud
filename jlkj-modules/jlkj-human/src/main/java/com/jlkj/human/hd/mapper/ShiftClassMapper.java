package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.ShiftClass;

import java.util.List;

/**
 * 班别数据Mapper接口
 * 
 * @author 266861
 * @date 2023-04-07
 */
public interface ShiftClassMapper 
{
    /**
     * 查询班别数据
     * 
     * @param id 班别数据主键
     * @return 班别数据
     */
    public ShiftClass selectShiftClassById(String id);

    /**
     * 查询班别数据列表
     * 
     * @param shiftClass 班别数据
     * @return 班别数据集合
     */
    public List<ShiftClass> selectShiftClassList(ShiftClass shiftClass);

    /**
     * 新增班别数据
     * 
     * @param shiftClass 班别数据
     * @return 结果
     */
    public int insertShiftClass(ShiftClass shiftClass);

    /**
     * 修改班别数据
     * 
     * @param shiftClass 班别数据
     * @return 结果
     */
    public int updateShiftClass(ShiftClass shiftClass);

    /**
     * 删除班别数据
     * 
     * @param id 班别数据主键
     * @return 结果
     */
    public int deleteShiftClassById(String id);

    /**
     * 批量删除班别数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShiftClassByIds(String[] ids);
}
