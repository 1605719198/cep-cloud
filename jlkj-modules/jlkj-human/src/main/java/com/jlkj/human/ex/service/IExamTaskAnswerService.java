package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamTaskAnswer;

import java.util.List;

/**
 * 考试实时数据Service接口
 * 
 * @author qnsdt
 * @date 2023-02-24
 */
public interface IExamTaskAnswerService 
{
    /**
     * 查询考试实时数据
     * 
     * @param examCode 考试实时数据ID
     * @return 考试实时数据
     */
    public ExamTaskAnswer selectExamTaskAnswerById(String examCode);

    /**
     * 查询考试实时数据列表
     * 
     * @param examTaskAnswer 考试实时数据
     * @return 考试实时数据集合
     */
    public List<ExamTaskAnswer> selectExamTaskAnswerList(ExamTaskAnswer examTaskAnswer);

    /**
     * 新增考试实时数据
     * 
     * @param examTaskAnswer 考试实时数据
     * @return 结果
     */
    public int insertExamTaskAnswer(ExamTaskAnswer examTaskAnswer);

    /**
     * 修改考试实时数据
     * 
     * @param examTaskAnswer 考试实时数据
     * @return 结果
     */
    public int updateExamTaskAnswer(ExamTaskAnswer examTaskAnswer);

    /**
     * 删除个人单条考试实时数据
     * 
     * @param examTaskAnswer 考试实时数据ID
     * @return 结果
     */
    public int deleteExamTaskAnswer(ExamTaskAnswer examTaskAnswer);

    /**
     * 批量删除考试实时数据
     * 
     * @param examCodes 需要删除的考试实时数据ID
     * @return 结果
     */
    public int deleteExamTaskAnswerByIds(String[] examCodes);

    /**
     * 删除考试实时数据信息
     * 
     * @param examCode 考试实时数据ID
     * @return 结果
     */
    public int deleteExamTaskAnswerById(String examCode);
}
