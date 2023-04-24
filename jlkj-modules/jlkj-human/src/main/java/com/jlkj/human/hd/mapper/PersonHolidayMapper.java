package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.PersonHoliday;

import java.util.List;

/**
 * 员工请假记录Mapper接口
 * 
 * @author jiangbingchen
 * @date 2023-04-14
 */
public interface PersonHolidayMapper 
{
    /**
     * 查询员工请假记录
     * 
     * @param id 员工请假记录主键
     * @return 员工请假记录
     */
    public PersonHoliday selectPersonHolidayById(String id);

    /**
     * 查询员工请假记录列表
     * 
     * @param personHoliday 员工请假记录
     * @return 员工请假记录集合
     */
    public List<PersonHoliday> selectPersonHolidayList(PersonHoliday personHoliday);

    /**
     * 新增员工请假记录
     * 
     * @param personHoliday 员工请假记录
     * @return 结果
     */
    public int insertPersonHoliday(PersonHoliday personHoliday);

    /**
     * 修改员工请假记录
     * 
     * @param personHoliday 员工请假记录
     * @return 结果
     */
    public int updatePersonHoliday(PersonHoliday personHoliday);

    /**
     * 删除员工请假记录
     * 
     * @param id 员工请假记录主键
     * @return 结果
     */
    public int deletePersonHolidayById(String id);

    /**
     * 批量删除员工请假记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonHolidayByIds(String[] ids);
}
