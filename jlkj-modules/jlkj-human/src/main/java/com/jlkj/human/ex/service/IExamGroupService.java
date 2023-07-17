package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamGroup;

import java.util.List;


/**
 * 人员分组Service接口
 * 
 * @author qnsdt
 * @date 2023-01-28
 */
public interface IExamGroupService
{
    /**
     * 查询人员分组
     * 
     * @param groupId 人员分组ID
     * @return 人员分组
     */
    public ExamGroup selectSysGroupById(Long groupId);

    /**
     * 查询人员分组列表
     * 
     * @param sysGroup 人员分组
     * @return 人员分组集合
     */
    public List<ExamGroup> selectSysGroupList(ExamGroup sysGroup);

    /**
     * 查询人员分组列表
     * 
     * @param sysGroup 人员分组
     * @return 人员分组集合
     */
    public List<ExamGroup> selectChoiceSysGroupList(String examCode);

    /**
     * 新增人员分组
     * 
     * @param sysGroup 人员分组
     * @return 结果
     */
    public int insertSysGroup(ExamGroup sysGroup);

    /**
     * 修改人员分组
     * 
     * @param sysGroup 人员分组
     * @return 结果
     */
    public int updateSysGroup(ExamGroup sysGroup);

    /**
     * 批量删除人员分组
     * 
     * @param groupIds 需要删除的人员分组ID
     * @return 结果
     */
    public int deleteSysGroupByIds(Long[] groupIds);

    /**
     * 删除人员分组信息
     * 
     * @param groupId 人员分组ID
     * @return 结果
     */
    public int deleteSysGroupById(Long groupId);
}
