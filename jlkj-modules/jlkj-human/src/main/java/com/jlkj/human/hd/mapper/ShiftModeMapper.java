package com.jlkj.human.hd.mapper;

import java.util.List;
import com.jlkj.human.hd.domain.ShiftMode;

/**
 * 轮班方式数据Mapper接口
 * 
 * @author 266861
 * @date 2023-04-04
 */
public interface ShiftModeMapper 
{
    /**
     * 查询轮班方式数据
     * 
     * @param id 轮班方式数据主键
     * @return 轮班方式数据
     */
    public ShiftMode selectShiftModeById(String id);

    /**
     * 查询轮班方式数据列表
     * 
     * @param shiftMode 轮班方式数据
     * @return 轮班方式数据集合
     */
    public List<ShiftMode> selectShiftModeList(ShiftMode shiftMode);

    /**
     * 查询轮班方式数据列表
     *
     * @param shiftMode 轮班方式数据
     * @return 轮班方式数据集合
     */
    public List<ShiftMode> selectShiftModeListAll(ShiftMode shiftMode);

    /**
     * 新增轮班方式数据
     * 
     * @param shiftMode 轮班方式数据
     * @return 结果
     */
    public int insertShiftMode(ShiftMode shiftMode);

    /**
     * 修改轮班方式数据
     * 
     * @param shiftMode 轮班方式数据
     * @return 结果
     */
    public int updateShiftMode(ShiftMode shiftMode);

    /**
     * 删除轮班方式数据
     * 
     * @param id 轮班方式数据主键
     * @return 结果
     */
    public int deleteShiftModeById(String id);

    /**
     * 批量删除轮班方式数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShiftModeByIds(String[] ids);
}
