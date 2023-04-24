package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.OvertimeWorkSetting;
import com.jlkj.human.hd.mapper.OvertimeWorkSettingMapper;
import com.jlkj.human.hd.service.IOvertimeWorkSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加班补休参数设定Service业务层处理
 *
 * @author 266861
 * @date 2023-03-29
 */
@Service
public class OvertimeWorkSettingServiceImpl implements IOvertimeWorkSettingService
{
    @Autowired
    private OvertimeWorkSettingMapper overtimeWorkSettingMapper;

    /**
     * 查询加班补休参数设定
     *
     * @param id 加班补休参数设定主键
     * @return 加班补休参数设定
     */
    @Override
    public OvertimeWorkSetting selectOvertimeWorkSettingById(String id)
    {
        return overtimeWorkSettingMapper.selectOvertimeWorkSettingById(id);
    }

    /**
     * 查询加班补休参数设定列表
     *
     * @param overtimeWorkSetting 加班补休参数设定
     * @return 加班补休参数设定
     */
    @Override
    public List<OvertimeWorkSetting> selectOvertimeWorkSettingList(OvertimeWorkSetting overtimeWorkSetting)
    {
        return overtimeWorkSettingMapper.selectOvertimeWorkSettingList(overtimeWorkSetting);
    }

    /**
     * 新增加班补休参数设定
     *
     * @param overtimeWorkSetting 加班补休参数设定
     * @return 结果
     */
    @Override
    public int insertOvertimeWorkSetting(OvertimeWorkSetting overtimeWorkSetting)
    {
        overtimeWorkSetting.setId(UUID.randomUUID().toString().substring(0, 32));
        return overtimeWorkSettingMapper.insertOvertimeWorkSetting(overtimeWorkSetting);
    }

    /**
     * 修改加班补休参数设定
     *
     * @param overtimeWorkSetting 加班补休参数设定
     * @return 结果
     */
    @Override
    public int updateOvertimeWorkSetting(OvertimeWorkSetting overtimeWorkSetting)
    {
        return overtimeWorkSettingMapper.updateOvertimeWorkSetting(overtimeWorkSetting);
    }

    /**
     * 批量删除加班补休参数设定
     *
     * @param ids 需要删除的加班补休参数设定主键
     * @return 结果
     */
    @Override
    public int deleteOvertimeWorkSettingByIds(String[] ids)
    {
        return overtimeWorkSettingMapper.deleteOvertimeWorkSettingByIds(ids);
    }

    /**
     * 删除加班补休参数设定信息
     *
     * @param id 加班补休参数设定主键
     * @return 结果
     */
    @Override
    public int deleteOvertimeWorkSettingById(String id)
    {
        return overtimeWorkSettingMapper.deleteOvertimeWorkSettingById(id);
    }
}
