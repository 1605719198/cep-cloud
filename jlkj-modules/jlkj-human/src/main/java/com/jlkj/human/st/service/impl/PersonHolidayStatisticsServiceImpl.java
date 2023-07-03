package com.jlkj.human.st.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.st.mapper.PersonHolidayStatisticsMapper;
import com.jlkj.human.st.domain.PersonHolidayStatistics;
import com.jlkj.human.st.service.IPersonHolidayStatisticsService;

/**
 * 员工请假统计Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-07-03
 * @version 1.0
 */
@Service
public class PersonHolidayStatisticsServiceImpl implements IPersonHolidayStatisticsService
{
    @Autowired
    private PersonHolidayStatisticsMapper personHolidayStatisticsMapper;

    /**
     * 查询员工请假统计
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param id 员工请假统计主键
     * @return 员工请假统计
     */
    @Override
    public PersonHolidayStatistics selectPersonHolidayStatisticsById(String id)
    {
        return personHolidayStatisticsMapper.selectPersonHolidayStatisticsById(id);
    }

    /**
     * 查询员工请假统计列表
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics 员工请假统计
     * @return 员工请假统计
     */
    @Override
    public List<PersonHolidayStatistics> selectPersonHolidayStatisticsList(PersonHolidayStatistics personHolidayStatistics)
    {
        return personHolidayStatisticsMapper.selectPersonHolidayStatisticsList(personHolidayStatistics);
    }

    /**
     * 新增员工请假统计
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics 员工请假统计
     * @return 结果
     */
    @Override
    public int insertPersonHolidayStatistics(PersonHolidayStatistics personHolidayStatistics)
    {
        return personHolidayStatisticsMapper.insertPersonHolidayStatistics(personHolidayStatistics);
    }

    /**
     * 修改员工请假统计
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics 员工请假统计
     * @return 结果
     */
    @Override
    public int updatePersonHolidayStatistics(PersonHolidayStatistics personHolidayStatistics)
    {
        return personHolidayStatisticsMapper.updatePersonHolidayStatistics(personHolidayStatistics);
    }

    /**
     * 批量删除员工请假统计
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param ids 需要删除的员工请假统计主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayStatisticsByIds(String[] ids)
    {
        return personHolidayStatisticsMapper.deletePersonHolidayStatisticsByIds(ids);
    }

    /**
     * 删除员工请假统计信息
     *
     * @author jiangbingchen
     * @date 2023-07-03
     * @param id 员工请假统计主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayStatisticsById(String id)
    {
        return personHolidayStatisticsMapper.deletePersonHolidayStatisticsById(id);
    }
}
