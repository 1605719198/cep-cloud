package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.Clockwork;

import java.util.List;

/**
 * 公司卡钟设定Service接口
 * 
 * @author 266861
 * @date 2023-03-21
 */
public interface IClockworkService 
{
    /**
     * 查询公司卡钟设定
     * 
     * @param id 公司卡钟设定主键
     * @return 公司卡钟设定
     */
    public Clockwork selectClockworkById(String id);

    /**
     * 查询公司卡钟设定列表
     * 
     * @param clockwork 公司卡钟设定
     * @return 公司卡钟设定集合
     */
    public List<Clockwork> selectClockworkList(Clockwork clockwork);

    /**
     * 新增公司卡钟设定
     * 
     * @param clockwork 公司卡钟设定
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int insertClockwork(Clockwork clockwork) throws Exception;

    /**
     * 修改公司卡钟设定
     * 
     * @param clockwork 公司卡钟设定
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int updateClockwork(Clockwork clockwork) throws Exception;

    /**
     * 批量删除公司卡钟设定
     * 
     * @param ids 需要删除的公司卡钟设定主键集合
     * @return 结果
     */
    public int deleteClockworkByIds(String[] ids);

    /**
     * 删除公司卡钟设定信息
     * 
     * @param id 公司卡钟设定主键
     * @return 结果
     */
    public int deleteClockworkById(String id);
}
