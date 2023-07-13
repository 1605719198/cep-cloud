package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamQuestionsListData;

import java.util.List;
/**
 *
 * @Author: 111191
 * @Date: 2023年7月11日, 0011 下午 02:13:45
 */
public interface IExamQuestionsListDataService {
	List<ExamQuestionsListData> selectQuestionsList(ExamQuestionsListData examQuestionsListData);

}
