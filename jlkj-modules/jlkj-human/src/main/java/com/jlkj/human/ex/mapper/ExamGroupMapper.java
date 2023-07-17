package com.jlkj.human.ex.mapper;

import com.jlkj.human.ex.domain.ExamGroup;

import java.util.List;


/**
 * 人员分组Mapper接口
 * 
 * @author qnsdt
 * @date 2023-01-28
 */
public interface ExamGroupMapper
{
    /**
     * 查询人员分组
     * 
     * @param groupId 人员分组ID
     * @return 人员分组
     */
    public ExamGroup selectExamGroupById(Long groupId);

    /**
     * 查询人员分组列表
     * 
     * @param ExamGroup 人员分组
     * @return 人员分组集合
     */
    public List<ExamGroup> selectExamGroupList(ExamGroup ExamGroup);

    /**
     * 查询人员分组列表
     * 
     * @param ExamGroup 人员分组
     * @return 人员分组集合
     */
    public List<ExamGroup> selectChoiceExamGroupList(String examCode);

    /**
     * 新增人员分组
     * 
     * @param ExamGroup 人员分组
     * @return 结果
     */
    public int insertExamGroup(ExamGroup ExamGroup);

    /**
     * 修改人员分组
     * 
     * @param ExamGroup 人员分组
     * @return 结果
     */
    public int updateExamGroup(ExamGroup ExamGroup);

    /**
     * 删除人员分组
     * 
     * @param groupId 人员分组ID
     * @return 结果
     */
    public int deleteExamGroupById(Long groupId);

    /**
     * 批量删除人员分组
     * 
     * @param groupIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamGroupByIds(Long[] groupIds);
}
