package com.jlkj.human.hd.service.impl;

import com.jlkj.human.hd.domain.YearHoliday;
import com.jlkj.human.hd.mapper.YearHolidayMapper;
import com.jlkj.human.hd.service.IYearHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 年休假天数设定Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-04-12
 */
@Service
public class YearHolidayServiceImpl implements IYearHolidayService
{
    @Autowired
    private YearHolidayMapper yearHolidayMapper;

    /**
     * 查询年休假天数设定
     *
     * @param id 年休假天数设定主键
     * @return 年休假天数设定
     */
    @Override
    public YearHoliday selectYearHolidayById(String id)
    {
        return yearHolidayMapper.selectYearHolidayById(id);
    }

    /**
     * 查询年休假天数设定列表
     *
     * @param yearHoliday 年休假天数设定
     * @return 年休假天数设定
     */
    @Override
    public List<YearHoliday> selectYearHolidayList(YearHoliday yearHoliday)
    {
        return yearHolidayMapper.selectYearHolidayList(yearHoliday);
    }

    /**
     * 新增年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     */
    @Override
    public int insertYearHoliday(YearHoliday yearHoliday)
    {
        return yearHolidayMapper.insertYearHoliday(yearHoliday);
    }

    /**
     * 修改年休假天数设定
     *
     * @param yearHoliday 年休假天数设定
     * @return 结果
     */
    @Override
    public int updateYearHoliday(YearHoliday yearHoliday)
    {
        return yearHolidayMapper.updateYearHoliday(yearHoliday);
    }

    /**
     * 批量删除年休假天数设定
     *
     * @param ids 需要删除的年休假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteYearHolidayByIds(String[] ids)
    {
        return yearHolidayMapper.deleteYearHolidayByIds(ids);
    }

    /**
     * 删除年休假天数设定信息
     *
     * @param id 年休假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteYearHolidayById(String id)
    {
        return yearHolidayMapper.deleteYearHolidayById(id);
    }
}
