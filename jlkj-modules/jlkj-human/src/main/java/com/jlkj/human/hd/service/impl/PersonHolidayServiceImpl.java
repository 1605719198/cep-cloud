package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hd.domain.PersonHoliday;
import com.jlkj.human.hd.mapper.PersonHolidayMapper;
import com.jlkj.human.hd.service.IPersonHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工请假记录Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-04-14
 */
@Service
public class PersonHolidayServiceImpl implements IPersonHolidayService
{
    @Autowired
    private PersonHolidayMapper personHolidayMapper;

    /**
     * 查询员工请假记录
     *
     * @param id 员工请假记录主键
     * @return 员工请假记录
     */
    @Override
    public PersonHoliday selectPersonHolidayById(String id)
    {
        return personHolidayMapper.selectPersonHolidayById(id);
    }



    /**
     * 查询员工请假记录列表
     *
     * @param personHoliday 员工请假记录
     * @return 员工请假记录
     */
    @Override
    public List<PersonHoliday> selectPersonHolidayList(PersonHoliday personHoliday)
    {
        return personHolidayMapper.selectPersonHolidayList(personHoliday);
    }

    /**
     * 新增员工请假记录
     *
     * @param personHoliday 员工请假记录
     * @return 结果
     */
    @Override
    public int insertPersonHoliday(PersonHoliday personHoliday)
    {
        personHoliday.setId(IdUtils.simpleUUID());
        return personHolidayMapper.insertPersonHoliday(personHoliday);
    }

    /**
     * 修改员工请假记录
     *
     * @param personHoliday 员工请假记录
     * @return 结果
     */
    @Override
    public int updatePersonHoliday(PersonHoliday personHoliday)
    {
        return personHolidayMapper.updatePersonHoliday(personHoliday);
    }

    /**
     * 批量删除员工请假记录
     *
     * @param ids 需要删除的员工请假记录主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayByIds(String[] ids)
    {
        return personHolidayMapper.deletePersonHolidayByIds(ids);
    }

    /**
     * 删除员工请假记录信息
     *
     * @param id 员工请假记录主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayById(String id)
    {
        return personHolidayMapper.deletePersonHolidayById(id);
    }
}
