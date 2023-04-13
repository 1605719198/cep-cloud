package com.jlkj.human.hd.service;

import java.util.List;
import com.jlkj.human.hd.domain.YearHoliday;

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
}
