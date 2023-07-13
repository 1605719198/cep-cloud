package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.ex.domain.ExamQuestions;
import com.jlkj.human.ex.mapper.ExamQuestionsMapper;
import com.jlkj.human.ex.service.IExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考试题目Service业务层处理
 * 
 * @author qnsdt
 * @date 2022-12-28
 */
@Service
public class ExamQuestionsServiceImpl implements IExamQuestionsService
{
    @Autowired
    private ExamQuestionsMapper examQuestionsMapper;

    /**
     * 查询考试题目
     * 
     * @param questionsCode 考试题目ID
     * @return 考试题目
     */
    @Override
    public ExamQuestions selectExamQuestionsById(String questionsCode)
    {
        return examQuestionsMapper.selectExamQuestionsById(questionsCode);
    }

    /**
     * 查询考试题目列表
     * 
     * @param examQuestions 考试题目
     * @return 考试题目
     */
    @Override
    public List<ExamQuestions> selectExamQuestionsList(ExamQuestions examQuestions)
    {
        return examQuestionsMapper.selectExamQuestionsList(examQuestions);
    }

    /**
     * 新增考试题目
     * 
     * @param examQuestions 考试题目
     * @return 结果
     */
    @Override
    public int insertExamQuestions(ExamQuestions examQuestions)
    {
        examQuestions.setCreateTime(DateUtils.getNowDate());
        return examQuestionsMapper.insertExamQuestions(examQuestions);
    }

    /**
     * 修改考试题目
     * 
     * @param examQuestions 考试题目
     * @return 结果
     */
    @Override
    public int updateExamQuestions(ExamQuestions examQuestions)
    {
        return examQuestionsMapper.updateExamQuestions(examQuestions);
    }

    /**
     * 批量删除考试题目
     * 
     * @param questionsCodes 需要删除的考试题目ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionsByIds(String[] questionsCodes)
    {
        return examQuestionsMapper.deleteExamQuestionsByIds(questionsCodes);
    }

    /**
     * 删除考试题目信息
     * 
     * @param questionsCode 考试题目ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionsById(String questionsCode)
    {
        return examQuestionsMapper.deleteExamQuestionsById(questionsCode);
    }
}
