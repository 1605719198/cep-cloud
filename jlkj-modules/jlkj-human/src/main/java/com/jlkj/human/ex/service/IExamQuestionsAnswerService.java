package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamQuestionsAnswer;

import java.util.List;

/**
 * 考试题目答案Service接口
 * 
 * @author 111191
 * @Date:  2023-07-11
 */
public interface IExamQuestionsAnswerService 
{
    /**
     * 查询考试题目答案
     * 
     * @param questionsCode 考试题目答案ID
     * @return 考试题目答案
     */
    ExamQuestionsAnswer selectExamQuestionsAnswerById(String questionsCode);

    /**
     * 查询考试题目答案列表
     * 
     * @param examQuestionsAnswer 考试题目答案
     * @return 考试题目答案集合
     */
    List<ExamQuestionsAnswer> selectExamQuestionsAnswerList(ExamQuestionsAnswer examQuestionsAnswer);

    /**
     * 查询考试题目答案选项
     * 
     * @param questionsCode 题目代码
     * @return 考试题目答案集合
     */
    List<ExamQuestionsAnswer> selectExamQuestionsOptionList(String questionsCode);

    /**
     * 新增考试题目答案
     * 
     * @param examQuestionsAnswer 考试题目答案
     * @return 结果
     */
    int insertExamQuestionsAnswer(ExamQuestionsAnswer examQuestionsAnswer);

    /**
     * 修改考试题目答案
     * 
     * @param examQuestionsAnswer 考试题目答案
     * @return 结果
     */
    int updateExamQuestionsAnswer(ExamQuestionsAnswer examQuestionsAnswer);

    /**
     * 批量删除考试题目答案
     * 
     * @param questionsCodes 需要删除的考试题目答案ID
     * @return 结果
     */
    int deleteExamQuestionsAnswerByIds(String[] questionsCodes);

    /**
     * 删除考试题目答案信息
     * 
     * @param questionsCode 考试题目答案ID
     * @return 结果
     */
    int deleteExamQuestionsAnswerById(String questionsCode);
}
