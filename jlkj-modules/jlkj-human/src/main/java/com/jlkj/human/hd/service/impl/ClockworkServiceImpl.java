package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.Clockwork;
import com.jlkj.human.hd.mapper.ClockworkMapper;
import com.jlkj.human.hd.service.IClockworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公司卡钟设定Service业务层处理
 *
 * @author 266861
 * @date 2023-03-21
 */
@Service
public class ClockworkServiceImpl implements IClockworkService
{
    @Autowired
    private ClockworkMapper clockworkMapper;

    /**
     * 查询公司卡钟设定
     *
     * @param id 公司卡钟设定主键
     * @return 公司卡钟设定
     */
    @Override
    public Clockwork selectClockworkById(String id)
    {
        return clockworkMapper.selectClockworkById(id);
    }

    /**
     * 查询公司卡钟设定列表
     *
     * @param clockwork 公司卡钟设定
     * @return 公司卡钟设定
     */
    @Override
    public List<Clockwork> selectClockworkList(Clockwork clockwork)
    {
        return clockworkMapper.selectClockworkList(clockwork);
    }

    /**
     * 新增公司卡钟设定
     *
     * @param clockwork 公司卡钟设定
     * @return 结果
     */
    @Override
    public int insertClockwork(Clockwork clockwork) throws Exception
    {
        int i = clockworkMapper.queryRepetitivedata(clockwork);
        if(i>0){
            throw new Exception("刷卡钟编码已存在，请重新输入");
        }
        clockwork.setId(UUID.randomUUID().toString().substring(0, 32));
        return clockworkMapper.insertClockwork(clockwork);
    }

    /**
     * 修改公司卡钟设定
     *
     * @param clockwork 公司卡钟设定
     * @return 结果
     */
    @Override
    public int updateClockwork(Clockwork clockwork) throws Exception
    {
        int i = clockworkMapper.queryRepetitivedata(clockwork);
        if(i>0){
            throw new Exception("刷卡钟编码已存在，请重新输入");
        }
        return clockworkMapper.updateClockwork(clockwork);
    }

    /**
     * 批量删除公司卡钟设定
     *
     * @param ids 需要删除的公司卡钟设定主键
     * @return 结果
     */
    @Override
    public int deleteClockworkByIds(String[] ids)
    {
        return clockworkMapper.deleteClockworkByIds(ids);
    }

    /**
     * 删除公司卡钟设定信息
     *
     * @param id 公司卡钟设定主键
     * @return 结果
     */
    @Override
    public int deleteClockworkById(String id)
    {
        return clockworkMapper.deleteClockworkById(id);
    }
}
