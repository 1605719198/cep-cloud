package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamGroupPerson;

import java.util.List;


/**
 * 分组用户Service接口
 * 
 * @author qnsdt
 * @date 2023-02-06
 */
public interface IExamGroupPersonService
{
    /**
     * 查询分组用户
     * 
     * @param groupCode 分组用户ID
     * @return 分组用户
     */
    public ExamGroupPerson selectSysGroupPersonById(String groupCode);

    /**
     * 查询分组用户列表
     * 
     * @param sysGroupPerson 分组用户
     * @return 分组用户集合
     */
    public List<ExamGroupPerson> selectSysGroupPersonList(ExamGroupPerson sysGroupPerson);

    /**
     * 新增分组用户
     * 
     * @param sysGroupPerson 分组用户
     * @return 结果
     */
    public int insertSysGroupPerson(List<ExamGroupPerson> sysGroupPersonList);

    /**
     * 修改分组用户
     * 
     * @param sysGroupPerson 分组用户
     * @return 结果
     */
    public int updateSysGroupPerson(ExamGroupPerson sysGroupPerson);

    /**
     * 批量删除分组用户
     * 
     * @param groupCodes 需要删除的分组用户ID
     * @return 结果
     */
    public int deleteSysGroupPersonByIds(String[] groupCodes);

    /**
     * 删除分组用户信息
     * 
     * @param groupCode 分组用户ID
     * @return 结果
     */
    public int deleteSysGroupPersonById(String groupCode);
}
