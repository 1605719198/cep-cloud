package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.YearHoliday;

import java.util.List;

/**
 * 年休假天数设定Service接口
 *
 * @author jiangbingchen
 * @date 2023-04-12
 */
public interface IYearHolidayService
{
    /**
     * 查询年休假天数设定
     *
     * @param id 年休假天数设定主键
     * @return 年休假天数设定
     */
    public YearHoliday selectYearHolidayById(String id);

    /**
     * 查询年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 年休假天数设定
     */
    public List<YearHoliday> selectYearHolidayByempNo(YearHoliday yearHoliday);

    /**
     * 查询年休假天数设定列表
     *
     * @param yearHoliday 年休假天数设定
     * @return 年休假天数设定集合
     */
    public List<YearHoliday> selectYearHolidayList(YearHoliday yearHoliday);

    /**
     * 新增年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     */
    public int insertYearHoliday(YearHoliday yearHoliday);

    /**
     * 修改年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     */
    public int updateYearHoliday(YearHoliday yearHoliday);


    /**
     * 修改某员工某年年休假数据
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     * @author 266861
     * @date 2023/6/29 13:37
     **/
    public int updateYearHolidayByEmp(YearHoliday yearHoliday);

    /**
     * 批量删除年休假天数设定
     *
     * @param ids 需要删除的年休假天数设定主键集合
     * @return 结果
     */
    public int deleteYearHolidayByIds(String[] ids);

    /**
     * 删除年休假天数设定信息
     *
     * @param id 年休假天数设定主键
     * @return 结果
     */
    public int deleteYearHolidayById(String id);

    /**
     * 导入年休假资料数据
     *
     * @param yearHolidayList 年休假数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<YearHoliday> yearHolidayList, Boolean isUpdateSupport, String operName);
}

