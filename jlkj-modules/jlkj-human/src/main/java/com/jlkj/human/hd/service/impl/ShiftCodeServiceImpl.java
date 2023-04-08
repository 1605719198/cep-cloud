package com.jlkj.human.hd.service.impl;

import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.ShiftCodeMapper;
import com.jlkj.human.hd.domain.ShiftCode;
import com.jlkj.human.hd.service.IShiftCodeService;

/**
 * 班次数据Service业务层处理
 *
 * @author 266861
 * @date 2023-04-06
 */
@Service
public class ShiftCodeServiceImpl implements IShiftCodeService
{
    @Autowired
    private ShiftCodeMapper shiftCodeMapper;

    /**
     * 查询班次数据
     *
     * @param id 班次数据主键
     * @return 班次数据
     */
    @Override
    public ShiftCode selectShiftCodeById(String id)
    {
        return shiftCodeMapper.selectShiftCodeById(id);
    }

    /**
     * 查询班次数据列表
     *
     * @param shiftCode 班次数据
     * @return 班次数据
     */
    @Override
    public List<ShiftCode> selectShiftCodeList(ShiftCode shiftCode)
    {
        return shiftCodeMapper.selectShiftCodeList(shiftCode);
    }

    /**
     * 新增班次数据
     *
     * @param shiftCode 班次数据
     * @return 结果
     */
    @Override
    public int insertShiftCode(ShiftCode shiftCode)
    {
        shiftCode.setId(UUID.randomUUID().toString().substring(0,32));
        return shiftCodeMapper.insertShiftCode(shiftCode);
    }

    /**
     * 修改班次数据
     *
     * @param shiftCode 班次数据
     * @return 结果
     */
    @Override
    public int updateShiftCode(ShiftCode shiftCode)
    {
        return shiftCodeMapper.updateShiftCode(shiftCode);
    }

    /**
     * 批量删除班次数据
     *
     * @param ids 需要删除的班次数据主键
     * @return 结果
     */
    @Override
    public int deleteShiftCodeByIds(String[] ids)
    {
        return shiftCodeMapper.deleteShiftCodeByIds(ids);
    }

    /**
     * 删除班次数据信息
     *
     * @param id 班次数据主键
     * @return 结果
     */
    @Override
    public int deleteShiftCodeById(String id)
    {
        return shiftCodeMapper.deleteShiftCodeById(id);
    }
}