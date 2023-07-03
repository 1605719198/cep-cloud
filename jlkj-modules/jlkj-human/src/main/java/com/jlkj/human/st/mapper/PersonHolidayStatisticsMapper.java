package com.jlkj.human.st.mapper;

import java.util.List;
import com.jlkj.human.st.domain.PersonHolidayStatistics;

/**
 * 员工请假统计Mapper接口
 * 
 * @author jiangbingchen
 * @date 2023-07-03
 */
public interface PersonHolidayStatisticsMapper 
{
    /**
     * 查询员工请假统计
     * 
     * @param id 员工请假统计主键
     * @return 员工请假统计
     */
    public PersonHolidayStatistics selectPersonHolidayStatisticsById(String id);

    /**
     * 查询员工请假统计列表
     * 
     * @param personHolidayStatistics 员工请假统计
     * @return 员工请假统计集合
     */
    public List<PersonHolidayStatistics> selectPersonHolidayStatisticsList(PersonHolidayStatistics personHolidayStatistics);

    /**
     * 新增员工请假统计
     * 
     * @param personHolidayStatistics 员工请假统计
     * @return 结果
     */
    public int insertPersonHolidayStatistics(PersonHolidayStatistics personHolidayStatistics);

    /**
     * 修改员工请假统计
     * 
     * @param personHolidayStatistics 员工请假统计
     * @return 结果
     */
    public int updatePersonHolidayStatistics(PersonHolidayStatistics personHolidayStatistics);

    /**
     * 删除员工请假统计
     * 
     * @param id 员工请假统计主键
     * @return 结果
     */
    public int deletePersonHolidayStatisticsById(String id);

    /**
     * 批量删除员工请假统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonHolidayStatisticsByIds(String[] ids);
}
