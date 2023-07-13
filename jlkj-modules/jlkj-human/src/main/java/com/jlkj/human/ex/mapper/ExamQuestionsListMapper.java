package com.jlkj.human.ex.mapper;

import com.jlkj.human.ex.domain.ExamQuestionsProperty;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 题目属性Mapper接口
 * 
 * @author qnsdt
 * @date 2022-12-28
 */
@Repository
public interface ExamQuestionsListMapper 
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
     * @Date: 2023年7月11日, 0011 下午 02:44:13
     * @param examCode
     * @return java.util.List<com.jlkj.human.ex.domain.ExamQuestionsProperty>
     */
    List<ExamQuestionsProperty> selectChoiceExamQuestionsList(String examCode);

}
