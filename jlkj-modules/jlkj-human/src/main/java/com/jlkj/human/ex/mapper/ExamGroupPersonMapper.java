package com.jlkj.human.ex.mapper;

import com.jlkj.human.ex.domain.ExamGroupPerson;

import java.util.List;

/**
 * 分组用户Mapper接口
 * 
 * @author qnsdt
 * @date 2023-02-06
 */
public interface ExamGroupPersonMapper
{
    /**
     * 查询分组用户
     * 
     * @param groupCode 分组用户ID
     * @return 分组用户
     */
    public ExamGroupPerson selecExamGroupPersonById(String groupCode);

    /**
     * 查询分组用户列表
     * 
     * @param sysGroupPerson 分组用户
     * @return 分组用户集合
     */
    public List<ExamGroupPerson> selectExamGroupPersonList(ExamGroupPerson sysGroupPerson);

    /**
     * 新增分组用户
     * 
     * @param sysGroupPerson 分组用户
     * @return 结果
     */
    public int insertExamGroupPerson(ExamGroupPerson sysGroupPerson);

    /**
     * 修改分组用户
     * 
     * @param sysGroupPerson 分组用户
     * @return 结果
     */
    public int updateExamGroupPerson(ExamGroupPerson sysGroupPerson);

    /**
     * 删除分组用户
     * 
     * @param groupCode 分组用户ID
     * @return 结果
     */
    public int deleteExamGroupPersonById(String groupCode);

    /**
     * 批量删除分组用户
     * 
     * @param groupCodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamGroupPersonByIds(String[] groupCodes);
}
