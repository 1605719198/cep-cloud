package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.OvertimeWorkSetting;

import java.util.List;

/**
 * 加班补休参数设定Mapper接口
 *
 * @author 266861
 * @date 2023-03-29
 */
public interface OvertimeWorkSettingMapper
{
    /**
     * 查询加班补休参数设定
     *
     * @param id 加班补休参数设定主键
     * @return 加班补休参数设定
     */
    public OvertimeWorkSetting selectOvertimeWorkSettingById(String id);

    /**
     * 查询加班补休参数设定列表
     *
     * @param overtimeWorkSetting 加班补休参数设定
     * @return 加班补休参数设定集合
     */
    public List<OvertimeWorkSetting> selectOvertimeWorkSettingList(OvertimeWorkSetting overtimeWorkSetting);

    /**
     * 新增加班补休参数设定
     *
     * @param overtimeWorkSetting 加班补休参数设定
     * @return 结果
     */
    public int insertOvertimeWorkSetting(OvertimeWorkSetting overtimeWorkSetting);

    /**
     * 修改加班补休参数设定
     *
     * @param overtimeWorkSetting 加班补休参数设定
     * @return 结果
     */
    public int updateOvertimeWorkSetting(OvertimeWorkSetting overtimeWorkSetting);

    /**
     * 删除加班补休参数设定
     *
     * @param id 加班补休参数设定主键
     * @return 结果
     */
    public int deleteOvertimeWorkSettingById(String id);

    /**
     * 批量删除加班补休参数设定
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOvertimeWorkSettingByIds(String[] ids);
}
