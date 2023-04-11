package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.HomeLeaveHoliday;

import java.util.List;

/**
 * 探亲假天数设定Service接口
 * 
 * @author jiangbingchen
 * @date 2023-04-08
 */
public interface IHomeLeaveHolidayService 
{
    /**
     * 查询探亲假天数设定
     * 
     * @param id 探亲假天数设定主键
     * @return 探亲假天数设定
     */
    public HomeLeaveHoliday selectHomeLeaveHolidayById(String id);

    /**
     * 查询探亲假天数设定列表
     * 
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 探亲假天数设定集合
     */
    public List<HomeLeaveHoliday> selectHomeLeaveHolidayList(HomeLeaveHoliday homeLeaveHoliday);

    /**
     * 新增探亲假天数设定
     * 
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     */
    public int insertHomeLeaveHoliday(HomeLeaveHoliday homeLeaveHoliday);

    /**
     * 修改探亲假天数设定
     * 
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     */
    public int updateHomeLeaveHoliday(HomeLeaveHoliday homeLeaveHoliday);

    /**
     * 批量删除探亲假天数设定
     * 
     * @param ids 需要删除的探亲假天数设定主键集合
     * @return 结果
     */
    public int deleteHomeLeaveHolidayByIds(String[] ids);

    /**
     * 删除探亲假天数设定信息
     * 
     * @param id 探亲假天数设定主键
     * @return 结果
     */
    public int deleteHomeLeaveHolidayById(String id);
}
