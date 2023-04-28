package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.Holidaysetting;
import com.jlkj.human.hd.dto.CopyHolidaysettingDTO;
import com.jlkj.human.hd.dto.HolidaySettingDTO;

import java.util.List;

/**
 * 假别参数设定Mapper接口
 *
 * @author 266861
 * @date 2023-03-21
 */
public interface HolidaysettingMapper
{
    /**
     * 查询假别参数设定
     *
     * @param id 假别参数设定主键
     * @return 假别参数设定
     */
    public Holidaysetting selectHolidaysettingById(String id);

    /**
     * 查询公司假别设定重复编码数
     *
     * @param holidaysetting 公司卡钟设定
     * @return 结果
     */
    public int queryRepetitivedata(Holidaysetting holidaysetting);

    /**
     * 查询假别设定复制来源公司
     *
     * @param oldCompId 来源公司别
     * @return 结果
     */
    public int querycopybyOldCompId(String oldCompId);

    /**
     * 查询假别设定复制目标公司
     *
     * @param newCompId 目标公司别
     * @return 结果
     */
    public int querycopybyNewCompId(String  newCompId);


    /**
     * 查询假别参数设定列表
     *
     * @param holidaysetting 假别参数设定
     * @return 假别参数设定集合
     */
    public List<Holidaysetting> selectHolidaysettingList(Holidaysetting holidaysetting);

    /**
     * 查询公司假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 公司假别参数设定
     */
    public HolidaySettingDTO getHolidaySetting(HolidaySettingDTO holidaysetting);

    /**
     * 新增假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 结果
     */
    public int insertHolidaysetting(Holidaysetting holidaysetting);

    /**
     * 复制假别参数设定
     *
     * @param copyHolidaysettingDTO 假别参数复制设定
     * @return 结果
     */
    public int copyHolidaysetting(CopyHolidaysettingDTO copyHolidaysettingDTO);

    /**
     * 修改假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 结果
     */
    public int updateHolidaysetting(Holidaysetting holidaysetting);

    /**
     * 删除假别参数设定
     *
     * @param id 假别参数设定主键
     * @return 结果
     */
    public int deleteHolidaysettingById(String id);

    /**
     * 批量删除假别参数设定
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHolidaysettingByIds(String[] ids);
}
