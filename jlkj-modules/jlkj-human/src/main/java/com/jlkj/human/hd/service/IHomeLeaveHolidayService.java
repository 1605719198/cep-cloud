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
     * 查询探亲假天数设定
     *
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 探亲假天数设定
     */
    public List<HomeLeaveHoliday> selectHomeLeaveHolidayByempNo(HomeLeaveHoliday homeLeaveHoliday);

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
     * 修改某员工某年探亲假数据
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     * @author 266861
     * @date 2023/6/29 13:37
     **/
    public int updateHomeLeaveHolidayByEmp(HomeLeaveHoliday homeLeaveHoliday);



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

    /**
     * 导入探亲假资料数据
     *
     * @param homeLeaveHolidayList 探亲假数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<HomeLeaveHoliday> homeLeaveHolidayList, Boolean isUpdateSupport, String operName);
}
