package com.jlkj.human.hd.service;

import java.util.List;
import com.jlkj.human.hd.domain.HolidayTable;

/**
 * 假日设定Service接口
 * 
 * @author 266861
 * @date 2023-04-10
 */
public interface IHolidayTableService 
{
    /**
     * 查询假日设定
     * 
     * @param id 假日设定主键
     * @return 假日设定
     */
    public HolidayTable selectHolidayTableById(String id);

    /**
     * 查询假日设定列表
     * 
     * @param holidayTable 假日设定
     * @return 假日设定集合
     */
    public List<HolidayTable> selectHolidayTableList(HolidayTable holidayTable);

    /**
     * 新增假日设定
     * 
     * @param holidayTable 假日设定
     * @return 结果
     */
    public int insertHolidayTable(HolidayTable holidayTable);

    /**
     * 新增年度假日设定
     *
     * @param holidayTable 假日设定
     * @return 结果
     */
    public int setYearData(HolidayTable holidayTable);

    /**
     * 修改假日设定
     * 
     * @param holidayTable 假日设定
     * @return 结果
     */
    public int updateHolidayTable(HolidayTable holidayTable);

    /**
     * 批量删除假日设定
     * 
     * @param ids 需要删除的假日设定主键集合
     * @return 结果
     */
    public int deleteHolidayTableByIds(String[] ids);

    /**
     * 删除假日设定信息
     * 
     * @param id 假日设定主键
     * @return 结果
     */
    public int deleteHolidayTableById(String id);
}
