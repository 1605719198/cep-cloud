package com.jlkj.human.st.service;

import java.util.List;
import com.jlkj.human.st.domain.PersonHolidayStatistics;

/**
 * 员工请假统计Service接口
 * 
 * @author jiangbingchen
 * @date 2023-07-03
 */
public interface IPersonHolidayStatisticsService 
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
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics 员工请假统计
     * @return 员工请假统计集合
     */
    public List<PersonHolidayStatistics> selectPersonHolidayStatisticsList(PersonHolidayStatistics personHolidayStatistics);

    /**
     * 新增员工请假统计
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics 员工请假统计
     * @return 结果
     */
    public int insertPersonHolidayStatistics(PersonHolidayStatistics personHolidayStatistics);

    /**
     * 修改员工请假统计
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics 员工请假统计
     * @return 结果
     */
    public int updatePersonHolidayStatistics(PersonHolidayStatistics personHolidayStatistics);

    /**
     * 批量删除员工请假统计
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param ids 需要删除的员工请假统计主键集合
     * @return 结果
     */
    public int deletePersonHolidayStatisticsByIds(String[] ids);

    /**
     * 删除员工请假统计信息
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param id 员工请假统计主键
     * @return 结果
     */
    public int deletePersonHolidayStatisticsById(String id);
}
