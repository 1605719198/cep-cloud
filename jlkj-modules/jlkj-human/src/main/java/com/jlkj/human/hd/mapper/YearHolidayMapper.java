package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.YearHoliday;

import java.util.List;

/**
 * 年休假天数设定Mapper接口
 *
 * @author jiangbingchen
 * @date 2023-04-12
 */
public interface YearHolidayMapper
{
    /**
     * 查询年休假天数设定
     *
     * @param id 年休假天数设定主键
     * @return 年休假天数设定
     */
    public YearHoliday selectYearHolidayById(String id);

    /**
     * 查询员工年休假
     *
     * @param yearHoliday 员工年休假设定
     * @return 设定值
     */
    public List<YearHoliday> selectYearHolidayByempNo(YearHoliday yearHoliday);

    /**
     * 查询员工年休假
     *
     * @param yearHoliday 员工年休假设定
     * @return 设定值
     */
    public YearHoliday selectYearHolidayByEmpNo(YearHoliday yearHoliday);

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
    public int updateYearHolidayByEmpNo(YearHoliday yearHoliday);

    /**
     * 修改年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     */
    public int updateYearHoliday(YearHoliday yearHoliday);

    /**
     * 修改某员工某年年休假数据
     * @param yearHoliday 年休假天数设定
     * @return 结果
     * @author 266861
     * @date 2023/6/29 13:37
     **/
    public int updateYearHolidayByEmp(YearHoliday yearHoliday);

    /**
     * 删除年休假天数设定
     *
     * @param id 年休假天数设定主键
     * @return 结果
     */
    public int deleteYearHolidayById(String id);

    /**
     * 批量删除年休假天数设定
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteYearHolidayByIds(String[] ids);
}
