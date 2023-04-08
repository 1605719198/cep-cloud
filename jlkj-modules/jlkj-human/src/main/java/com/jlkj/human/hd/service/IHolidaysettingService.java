package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.Holidaysetting;
import com.jlkj.human.hd.dto.CopyHolidaysettingDTO;

import java.util.List;

/**
 * 假别参数设定Service接口
 *
 * @author 266861
 * @date 2023-03-21
 */
public interface IHolidaysettingService
{
    /**
     * 查询假别参数设定
     *
     * @param id 假别参数设定主键
     * @return 假别参数设定
     */
    public Holidaysetting selectHolidaysettingById(String id);

    /**
     * 查询假别参数设定列表
     *
     * @param holidaysetting 假别参数设定
     * @return 假别参数设定集合
     */
    public List<Holidaysetting> selectHolidaysettingList(Holidaysetting holidaysetting);

    /**
     * 新增假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int insertHolidaysetting(Holidaysetting holidaysetting) throws Exception;

    /**
     * 复制假别参数设定
     *
     * @param copyHolidaysettingDTO 假别参数复制设定
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int copyHolidaysetting(CopyHolidaysettingDTO copyHolidaysettingDTO) throws Exception;

    /**
     * 修改假别参数设定
     *
     * @param holidaysetting 假别参数设定
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int updateHolidaysetting(Holidaysetting holidaysetting) throws Exception;

    /**
     * 批量删除假别参数设定
     *
     * @param ids 需要删除的假别参数设定主键集合
     * @return 结果
     */
    public int deleteHolidaysettingByIds(String[] ids);

    /**
     * 删除假别参数设定信息
     *
     * @param id 假别参数设定主键
     * @return 结果
     */
    public int deleteHolidaysettingById(String id);
}
