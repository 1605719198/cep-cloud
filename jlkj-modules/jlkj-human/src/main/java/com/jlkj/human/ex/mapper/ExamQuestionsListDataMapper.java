package com.jlkj.human.ex.mapper;

import com.jlkj.human.ex.domain.ExamQuestionsListData;

import java.util.List;

public interface ExamQuestionsListDataMapper {
	public List<ExamQuestionsListData> selectQuestionsList(ExamQuestionsListData examQuestionsListData);
}
