package com.jlkj.human.hd.service.impl;

import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.ShiftClassMapper;
import com.jlkj.human.hd.domain.ShiftClass;
import com.jlkj.human.hd.service.IShiftClassService;

/**
 * 班别数据Service业务层处理
 *
 * @author 266861
 * @date 2023-04-07
 */
@Service
public class ShiftClassServiceImpl implements IShiftClassService
{
    @Autowired
    private ShiftClassMapper shiftClassMapper;

    /**
     * 查询班别数据
     *
     * @param id 班别数据主键
     * @return 班别数据
     */
    @Override
    public ShiftClass selectShiftClassById(String id)
    {
        return shiftClassMapper.selectShiftClassById(id);
    }

    /**
     * 查询班别数据列表
     *
     * @param shiftClass 班别数据
     * @return 班别数据
     */
    @Override
    public List<ShiftClass> selectShiftClassList(ShiftClass shiftClass)
    {
        return shiftClassMapper.selectShiftClassList(shiftClass);
    }

    /**
     * 新增班别数据
     *
     * @param shiftClass 班别数据
     * @return 结果
     */
    @Override
    public int insertShiftClass(ShiftClass shiftClass)
    {
        shiftClass.setId(UUID.randomUUID().toString().substring(0,32));
        return shiftClassMapper.insertShiftClass(shiftClass);
    }

    /**
     * 修改班别数据
     *
     * @param shiftClass 班别数据
     * @return 结果
     */
    @Override
    public int updateShiftClass(ShiftClass shiftClass)
    {
        return shiftClassMapper.updateShiftClass(shiftClass);
    }

    /**
     * 批量删除班别数据
     *
     * @param ids 需要删除的班别数据主键
     * @return 结果
     */
    @Override
    public int deleteShiftClassByIds(String[] ids)
    {
        return shiftClassMapper.deleteShiftClassByIds(ids);
    }

    /**
     * 删除班别数据信息
     *
     * @param id 班别数据主键
     * @return 结果
     */
    @Override
    public int deleteShiftClassById(String id)
    {
        return shiftClassMapper.deleteShiftClassById(id);
    }
}
