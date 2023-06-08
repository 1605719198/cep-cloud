package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.PersonalSalary;

import java.util.List;

/**
 * 薪资核定Mapper接口
 * 
 * @author 266861
 * @date 2023-06-01
 */
public interface PersonalSalaryMapper 
{
    /**
     * 查询薪资核定
     * 
     * @param id 薪资核定主键
     * @return 薪资核定
     */
    public PersonalSalary selectPersonalSalaryById(String id);

    /**
     * 查询薪资核定列表
     * 
     * @param personalSalary 薪资核定
     * @return 薪资核定集合
     */
    public List<PersonalSalary> selectPersonalSalaryByEmp(PersonalSalary personalSalary);


    /**
     * 查询某员工薪资核定列表
     *
     * @param personalSalary 薪资核定
     * @return 薪资核定集合
     */
    public List<PersonalSalary> selectPersonalSalaryList(PersonalSalary personalSalary);

    /**
     * 新增薪资核定
     * 
     * @param personalSalary 薪资核定
     * @return 结果
     */
    public int insertPersonalSalary(PersonalSalary personalSalary);

    /**
     * 修改薪资核定
     * 
     * @param personalSalary 薪资核定
     * @return 结果
     */
    public int updatePersonalSalary(PersonalSalary personalSalary);

    /**
     * 删除薪资核定
     * 
     * @param id 薪资核定主键
     * @return 结果
     */
    public int deletePersonalSalaryById(String id);

    /**
     * 批量删除薪资核定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonalSalaryByIds(String[] ids);
}
