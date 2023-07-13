package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamQuestionsProperty;

import java.util.List;

/**
 * 题目属性Service接口
 * 
 * @author 111191
 * @date 2023-7-11
 */
public interface IExamQuestionsListService 
{

    /**
     * 查询题目属性列表
     * 
     * @param bankCodes 题目属性
     * @return 题目属性集合
     */
    List<ExamQuestionsProperty> selectExamQuestionsList(String[] bankCodes);
    /**
     *
     * @Author: 111191
     * @Date: 2023年7月11日, 0011 下午 02:43:23
     * @param examCode
     * @return java.util.List<com.jlkj.human.ex.domain.ExamQuestionsProperty>
     */
    List<ExamQuestionsProperty> selectChoiceExamQuestionsList(String examCode);


}
