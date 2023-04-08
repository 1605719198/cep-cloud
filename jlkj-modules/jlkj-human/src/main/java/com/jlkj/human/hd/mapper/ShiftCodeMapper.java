package com.jlkj.human.hd.mapper;

import java.util.List;
import com.jlkj.human.hd.domain.ShiftCode;

/**
 * 班次数据Mapper接口
 * 
 * @author 266861
 * @date 2023-04-06
 */
public interface ShiftCodeMapper 
{
    /**
     * 查询班次数据
     * 
     * @param id 班次数据主键
     * @return 班次数据
     */
    public ShiftCode selectShiftCodeById(String id);

    /**
     * 查询班次数据列表
     * 
     * @param shiftCode 班次数据
     * @return 班次数据集合
     */
    public List<ShiftCode> selectShiftCodeList(ShiftCode shiftCode);

    /**
     * 新增班次数据
     * 
     * @param shiftCode 班次数据
     * @return 结果
     */
    public int insertShiftCode(ShiftCode shiftCode);

    /**
     * 修改班次数据
     * 
     * @param shiftCode 班次数据
     * @return 结果
     */
    public int updateShiftCode(ShiftCode shiftCode);

    /**
     * 删除班次数据
     * 
     * @param id 班次数据主键
     * @return 结果
     */
    public int deleteShiftCodeById(String id);

    /**
     * 批量删除班次数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShiftCodeByIds(String[] ids);
}
