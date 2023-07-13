package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamQuestionsContentData;
/**
 *
 * @Author: 111191
 * @Date: 2023年7月11日, 0011 下午 02:31:32
 */
public interface IExamQuestionsContentDataService {
    /**
     *
     * @Author: 111191
     * @Date: 2023年7月11日, 0011 下午 02:31:29
     * @param questionsContentData
     * @return com.jlkj.human.ex.domain.ExamQuestionsContentData
     */
    ExamQuestionsContentData selectExamQuestionsContent(ExamQuestionsContentData questionsContentData);

}
