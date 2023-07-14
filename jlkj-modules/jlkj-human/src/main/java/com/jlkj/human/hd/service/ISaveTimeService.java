package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.SaveTime;

import java.util.List;

/**
 * 存班记录Service接口
 * 
 * @author 266861
 * @date 2023-07-10
 */
public interface ISaveTimeService 
{
    /**
     * 查询存班记录
     * 
     * @param id 存班记录主键
     * @return 存班记录
     */
    public SaveTime selectSaveTimeById(String id);

    /**
     * 查询存班记录列表
     * 
     * @param saveTime 存班记录
     * @return 存班记录集合
     */
    public List<SaveTime> selectSaveTimeList(SaveTime saveTime);

    /**
     * 新增存班记录
     * 
     * @param saveTime 存班记录
     * @return 结果
     */
    public int insertSaveTime(SaveTime saveTime);

    /**
     * 修改存班记录
     * 
     * @param saveTime 存班记录
     * @return 结果
     */
    public int updateSaveTime(SaveTime saveTime);

    /**
     * 批量删除存班记录
     * 
     * @param ids 需要删除的存班记录主键集合
     * @return 结果
     */
    public int deleteSaveTimeByIds(String[] ids);

    /**
     * 删除存班记录信息
     * 
     * @param id 存班记录主键
     * @return 结果
     */
    public int deleteSaveTimeById(String id);

    /**
     *
     * 加班存班时数定时清0
     * @return 批次启动结果
     * @author 266861
     * @Date 2023/7/11 13:01
     **/
    public int scheduledZeroing();

}
