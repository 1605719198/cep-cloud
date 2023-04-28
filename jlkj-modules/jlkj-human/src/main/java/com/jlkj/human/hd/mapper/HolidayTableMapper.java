package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.HolidayTable;
import com.jlkj.human.hd.dto.HolidayTableDTO;

import java.util.List;

/**
 * 假日设定Mapper接口
 * 
 * @author 266861
 * @date 2023-04-10
 */
public interface HolidayTableMapper 
{
    /**
     * 查询假日设定
     * 
     * @param id 假日设定主键
     * @return 假日设定
     */
    public HolidayTable selectHolidayTableById(String id);

    /**
     * 查询假日设定
     *
     * @param holidayTable 假日设定
     * @return 假日设定
     */
    public List<HolidayTable> selectHolidayTableByData(HolidayTable holidayTable);

    /**
     * 查询假日设定列表
     * 
     * @param holidayTable 假日设定
     * @return 假日设定集合
     */
    public List<HolidayTable> selectHolidayTableList(HolidayTable holidayTable);

    /**
     * 查询时间段内假日设定
     *
     * @param holidayTable 假日设定
     * @return 假日设定集合
     */
    public List<HolidayTableDTO> selectHolidayTable(HolidayTableDTO holidayTable);

    /**
     * 新增假日设定
     * 
     * @param holidayTable 假日设定
     * @return 结果
     */
    public int insertHolidayTable(HolidayTable holidayTable);

    /**
     * 修改假日设定
     * 
     * @param holidayTable 假日设定
     * @return 结果
     */
    public int updateHolidayTable(HolidayTable holidayTable);

    /**
     * 删除假日设定
     * 
     * @param id 假日设定主键
     * @return 结果
     */
    public int deleteHolidayTableById(String id);

    /**
     * 批量删除假日设定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHolidayTableByIds(String[] ids);
}
