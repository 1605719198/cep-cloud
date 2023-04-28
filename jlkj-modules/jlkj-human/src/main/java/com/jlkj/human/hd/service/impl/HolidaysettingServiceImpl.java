package com.jlkj.human.hd.service.impl;

import com.jlkj.human.hd.domain.Holidaysetting;
import com.jlkj.human.hd.dto.CopyHolidaysettingDTO;
import com.jlkj.human.hd.dto.HolidaySettingDTO;
import com.jlkj.human.hd.mapper.HolidaysettingMapper;
import com.jlkj.human.hd.service.IHolidaysettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 假别参数设定Service业务层处理
 *
 * @author 266861
 * @date 2023-03-21
 */
@Service
public class HolidaysettingServiceImpl implements IHolidaysettingService
{
    @Autowired
    private HolidaysettingMapper holidaysettingMapper;

    /**
     * 查询假别参数设定
     *
     * @param id 假别参数设定主键
     * @return 假别参数设定
     */
    @Override
    public Holidaysetting selectHolidaysettingById(String id)
    {
        return holidaysettingMapper.selectHolidaysettingById(id);
    }

    /**
     * 查询假别参数设定列表
     *
     * @param holidaysetting 假别参数设定
     * @return 假别参数设定
     */
    @Override
    public List<Holidaysetting> selectHolidaysettingList(Holidaysetting holidaysetting)
    {
        return holidaysettingMapper.selectHolidaysettingList(holidaysetting);
    }

    /**
     * 查询公司假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 公司假别参数设定
     */
    public HolidaySettingDTO getHolidaySetting(HolidaySettingDTO holidaysetting){
        return holidaysettingMapper.getHolidaySetting(holidaysetting);
    }

    /**
     * 新增假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 结果
     */
    @Override
    public int insertHolidaysetting(Holidaysetting holidaysetting) throws Exception
    {
        int i = holidaysettingMapper.queryRepetitivedata(holidaysetting);
        if(i>0){
            throw new Exception("该公司对应的假别类型已存在，请重新输入");
        }
        return holidaysettingMapper.insertHolidaysetting(holidaysetting);
    }

    /**
     * 复制假别参数设定
     *
     * @param copyHolidaysettingDTO 假别参数复制设定
     * @return 结果
     */
    @Override
    public int copyHolidaysetting(CopyHolidaysettingDTO copyHolidaysettingDTO) throws Exception
    {
        int i = holidaysettingMapper.querycopybyOldCompId(copyHolidaysettingDTO.getOldCompId());
        int j = holidaysettingMapper.querycopybyNewCompId(copyHolidaysettingDTO.getNewCompId());
        if(i==0){
            throw new Exception("来源公司下无假别参数设定数据");
        }else if(j>0){
            throw new Exception("目标公司下已有假别参数设定数据");
        }
        return holidaysettingMapper.copyHolidaysetting(copyHolidaysettingDTO);
    }

    /**
     * 修改假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 结果
     */
    @Override
    public int updateHolidaysetting(Holidaysetting holidaysetting) throws Exception
    {
        int i = holidaysettingMapper.queryRepetitivedata(holidaysetting);
        if(i>0){
            throw new Exception("该公司对应的假别类型已存在，请重新输入");
        }
        return holidaysettingMapper.updateHolidaysetting(holidaysetting);
    }

    /**
     * 批量删除假别参数设定
     *
     * @param ids 需要删除的假别参数设定主键
     * @return 结果
     */
    @Override
    public int deleteHolidaysettingByIds(String[] ids)
    {
        return holidaysettingMapper.deleteHolidaysettingByIds(ids);
    }

    /**
     * 删除假别参数设定信息
     *
     * @param id 假别参数设定主键
     * @return 结果
     */
    @Override
    public int deleteHolidaysettingById(String id)
    {
        return holidaysettingMapper.deleteHolidaysettingById(id);
    }
}
