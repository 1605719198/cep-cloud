package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.HolidayTable;
import com.jlkj.human.hd.dto.HolidayTableDTO;
import com.jlkj.human.hd.mapper.HolidayTableMapper;
import com.jlkj.human.hd.service.IHolidayTableService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 假日设定Service业务层处理
 *
 * @author 266861
 * @date 2023-04-10
 */
@Service
public class HolidayTableServiceImpl implements IHolidayTableService
{
    @Autowired
    private HolidayTableMapper holidayTableMapper;

    /**
     * 查询假日设定
     *
     * @param id 假日设定主键
     * @return 假日设定
     */
    @Override
    public HolidayTable selectHolidayTableById(String id)
    {
        return holidayTableMapper.selectHolidayTableById(id);
    }

    /**
     * 查询假日设定列表
     *
     * @param holidayTable 假日设定
     * @return 假日设定
     */
    @Override
    public List<HolidayTable> selectHolidayTableList(HolidayTable holidayTable)
    {
        return holidayTableMapper.selectHolidayTableList(holidayTable);
    }

    /**
     * 新增假日设定
     *
     * @param holidayTable 假日设定
     * @return 结果
     */
    @Override
    public int insertHolidayTable(HolidayTable holidayTable)
    {
        List<HolidayTable> list = holidayTableMapper.selectHolidayTableByData(holidayTable);
        if(list.size()!=0){
            holidayTable.setId(list.get(0).getId());
            return holidayTableMapper.updateHolidayTable(holidayTable);
        }else{
            holidayTable.setId(UUID.randomUUID().toString().substring(0, 32));
            return holidayTableMapper.insertHolidayTable(holidayTable);
        }

    }

    /**
     * 查询时间段内假日设定
     *
     * @param holidayTable 假日设定
     * @return 假日设定集合
     */
    @Override
    public List<HolidayTableDTO> selectHolidayTable(HolidayTableDTO holidayTable){
        return holidayTableMapper.selectHolidayTable(holidayTable);
    }

    /**
     * 新增年度假日设定
     *
     * @param holidayTable 假日设定
     * @return 结果
     */
    @Override
    public int setYearData(HolidayTable holidayTable)
    {
        HolidayTable yearHolidayTable = new HolidayTable();
        BeanUtils.copyProperties(holidayTable,yearHolidayTable);
         //大月
         int[] bigMonth= {1, 3, 5, 7, 8, 10, 12};
         //年份
         int year = Integer.parseInt(holidayTable.getYear());
         //当年1号星期
        int k = 0;
        try {
            k = getWeek(year+"-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //月配置
        int months = 13;
        int elseDay = 0 ;
         int[] week = {2,1, 1, 1, 1, 1, 2};
         for (int i = 1; i <  months; i++) {
           int monthDays ;
           Arrays.sort(bigMonth);
           int index = Arrays.binarySearch(bigMonth,i);
           if ((index>=0)) {
             monthDays = 32;
           } else if (i == 2) {

             boolean o = ((year%400 ==0)||(year % 4 == 0 && year%100 != 0));
             if(o){
                 elseDay=1;
             }
             monthDays = 29+elseDay;
           } else {
             monthDays = 31;
           }
           for (int j = 1; j < monthDays; j++) {
             int t = k % 7;
             k++;
             String month = (i<10)? '0'+String.valueOf(i): String.valueOf(i);
             String day = (j<10)? '0'+String.valueOf(j):String.valueOf(j);
             yearHolidayTable.setMonth(month);
             yearHolidayTable.setDay(day);
             yearHolidayTable.setDateType(String.valueOf(week[t]));
             String timeString = String.valueOf(year)+'-'+month+'-'+day;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             Date date = null;
             try {
                 date = sdf.parse(timeString);
             }catch (ParseException e){
                 e.printStackTrace();
             }
             yearHolidayTable.setHolDay(date);
               insertHolidayTable(yearHolidayTable);
           }
         }
        return 1;
    }
    /**
     * 根据日期获得星期数
     *
     * @param sdate 日期字符串
     * @return 星期几
     */
    public static int getWeek(String sdate) throws ParseException {
        // 再转换为时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Date parseDate = dateFormat.parse(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate);
        int weekIndex = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
//        return weeks[weekIndex];
        return weekIndex;
    }
    /**
     * 修改假日设定
     *
     * @param holidayTable 假日设定
     * @return 结果
     */
    @Override
    public int updateHolidayTable(HolidayTable holidayTable)
    {
        return holidayTableMapper.updateHolidayTable(holidayTable);
    }

    /**
     * 批量删除假日设定
     *
     * @param ids 需要删除的假日设定主键
     * @return 结果
     */
    @Override
    public int deleteHolidayTableByIds(String[] ids)
    {
        return holidayTableMapper.deleteHolidayTableByIds(ids);
    }

    /**
     * 删除假日设定信息
     *
     * @param id 假日设定主键
     * @return 结果
     */
    @Override
    public int deleteHolidayTableById(String id)
    {
        return holidayTableMapper.deleteHolidayTableById(id);
    }
}
