package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.PersonHolidayCancel;

import java.util.List;

/**
 * 员工销假Service接口
 * 
 * @author 266861
 * @date 2023-04-21
 */
public interface IPersonHolidayCancelService 
{
    /**
     * 查询员工销假
     * 
     * @param id 员工销假主键
     * @return 员工销假
     */
    public PersonHolidayCancel selectPersonHolidayCancelById(String id);

    /**
     * 查询员工销假列表
     * 
     * @param personHolidayCancel 员工销假
     * @return 员工销假集合
     */
    public List<PersonHolidayCancel> selectPersonHolidayCancelList(PersonHolidayCancel personHolidayCancel);

    /**
     * 新增员工销假
     * 
     * @param personHolidayCancel 员工销假
     * @return 结果
     */
    public int insertPersonHolidayCancel(PersonHolidayCancel personHolidayCancel);

    /**
     * 修改员工销假
     * 
     * @param personHolidayCancel 员工销假
     * @return 结果
     */
    public int updatePersonHolidayCancel(PersonHolidayCancel personHolidayCancel);

    /**
     * 批量删除员工销假
     * 
     * @param ids 需要删除的员工销假主键集合
     * @return 结果
     */
    public int deletePersonHolidayCancelByIds(String[] ids);

    /**
     * 删除员工销假信息
     * 
     * @param id 员工销假主键
     * @return 结果
     */
    public int deletePersonHolidayCancelById(String id);

    /**
     * 撤回员工销假信息
     *
     * @param ids 员工销假主键
     * @return 结果
     */
    public int otherPersonHolidayCancelByIds(String ids);
}
