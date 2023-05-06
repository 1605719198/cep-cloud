package com.jlkj.human.hd.service.impl;

import com.jlkj.human.hd.domain.PersonHolidayCancel;
import com.jlkj.human.hd.mapper.PersonHolidayCancelMapper;
import com.jlkj.human.hd.service.IPersonHolidayCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工销假Service业务层处理
 *
 * @author 266861
 * @date 2023-04-21
 */
@Service
public class PersonHolidayCancelServiceImpl implements IPersonHolidayCancelService
{
    @Autowired
    private PersonHolidayCancelMapper personHolidayCancelMapper;

    /**
     * 查询员工销假
     *
     * @param id 员工销假主键
     * @return 员工销假
     */
    @Override
    public PersonHolidayCancel selectPersonHolidayCancelById(String id)
    {
        return personHolidayCancelMapper.selectPersonHolidayCancelById(id);
    }

    /**
     * 查询员工销假列表
     *
     * @param personHolidayCancel 员工销假
     * @return 员工销假
     */
    @Override
    public List<PersonHolidayCancel> selectPersonHolidayCancelList(PersonHolidayCancel personHolidayCancel)
    {
        return personHolidayCancelMapper.selectPersonHolidayCancelList(personHolidayCancel);
    }

    /**
     * 新增员工销假
     *
     * @param personHolidayCancel 员工销假
     * @return 结果
     */
    @Override
    public int insertPersonHolidayCancel(PersonHolidayCancel personHolidayCancel)
    {
        return personHolidayCancelMapper.insertPersonHolidayCancel(personHolidayCancel);
    }

    /**
     * 修改员工销假
     *
     * @param personHolidayCancel 员工销假
     * @return 结果
     */
    @Override
    public int updatePersonHolidayCancel(PersonHolidayCancel personHolidayCancel)
    {
        return personHolidayCancelMapper.updatePersonHolidayCancel(personHolidayCancel);
    }

    /**
     * 批量删除员工销假
     *
     * @param ids 需要删除的员工销假主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayCancelByIds(String[] ids)
    {
        return personHolidayCancelMapper.deletePersonHolidayCancelByIds(ids);
    }

    /**
     * 删除员工销假信息
     *
     * @param id 员工销假主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayCancelById(String id)
    {
        return personHolidayCancelMapper.deletePersonHolidayCancelById(id);
    }

    /**
     * 撤回员工销假信息
     *
     * @param ids 员工销假主键
     * @return 结果
     */
    @Override
    public int otherPersonHolidayCancelByIds(String ids) {
        return personHolidayCancelMapper.personHolidayCancelById(ids);
    }
}
