package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.ShiftCode;
import com.jlkj.human.hd.dto.PersonShiftCodeDTO;

import java.util.List;

/**
 * 班次数据Service接口
 * 
 * @author 266861
 * @date 2023-04-06
 */
public interface IShiftCodeService 
{
    /**
     * 查询班次数据
     *
     * @param personShiftCodedto 排班人员工号时间
     * @return 班次数据
     */
    public ShiftCode selectShiftCodeByPerson(PersonShiftCodeDTO personShiftCodedto);

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
     * 批量删除班次数据
     * 
     * @param ids 需要删除的班次数据主键集合
     * @return 结果
     */
    public int deleteShiftCodeByIds(String[] ids);

    /**
     * 删除班次数据信息
     * 
     * @param id 班次数据主键
     * @return 结果
     */
    public int deleteShiftCodeById(String id);
}
