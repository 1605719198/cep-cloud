package com.jlkj.human.hd.service.impl;

import java.util.List;
import java.util.Random;

import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.ShiftModeMapper;
import com.jlkj.human.hd.domain.ShiftMode;
import com.jlkj.human.hd.service.IShiftModeService;

/**
 * 轮班方式数据Service业务层处理
 *
 * @author 266861
 * @date 2023-04-04
 */
@Service
public class ShiftModeServiceImpl implements IShiftModeService
{
    @Autowired
    private ShiftModeMapper shiftModeMapper;

    /**
     * 查询轮班方式数据
     *
     * @param id 轮班方式数据主键
     * @return 轮班方式数据
     */
    @Override
    public ShiftMode selectShiftModeById(String id)
    {
        return shiftModeMapper.selectShiftModeById(id);
    }

    /**
     * 查询轮班方式数据列表
     *
     * @param shiftMode 轮班方式数据
     * @return 轮班方式数据
     */
    @Override
    public List<ShiftMode> selectShiftModeList(ShiftMode shiftMode)
    {
        return shiftModeMapper.selectShiftModeList(shiftMode);
    }

    /**
     * 查询轮班方式数据列表
     *
     * @param shiftMode 轮班方式数据
     * @return 轮班方式数据
     */
    @Override
    public List<ShiftMode> selectShiftModeListAll(ShiftMode shiftMode)
    {
        return shiftModeMapper.selectShiftModeListAll(shiftMode);
    }

    /**
     * 新增轮班方式数据
     *
     * @param shiftMode 轮班方式数据
     * @return 结果
     */
    @Override
    public int insertShiftMode(ShiftMode shiftMode)
    {
        shiftMode.setId(UUID.randomUUID().toString().substring(0,32));
        return shiftModeMapper.insertShiftMode(shiftMode);
    }

    /**
     * 修改轮班方式数据
     *
     * @param shiftMode 轮班方式数据
     * @return 结果
     */
    @Override
    public int updateShiftMode(ShiftMode shiftMode)
    {
        return shiftModeMapper.updateShiftMode(shiftMode);
    }

    /**
     * 批量删除轮班方式数据
     *
     * @param ids 需要删除的轮班方式数据主键
     * @return 结果
     */
    @Override
    public int deleteShiftModeByIds(String[] ids)
    {
        return shiftModeMapper.deleteShiftModeByIds(ids);
    }

    /**
     * 删除轮班方式数据信息
     *
     * @param id 轮班方式数据主键
     * @return 结果
     */
    @Override
    public int deleteShiftModeById(String id)
    {
        return shiftModeMapper.deleteShiftModeById(id);
    }
}
