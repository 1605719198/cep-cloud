package com.jlkj.human.ex.domain;

import lombok.Data;

import java.util.List;

@Data
public class ExamQuestionsData {

	private ExamQuestionsContentData examQuestionsContentData;
	private List<ExamQuestionsAnswer> examQuestionsOptionList;
	
	
	public ExamQuestionsData(ExamQuestionsContentData examQuestionsContentData,
			List<ExamQuestionsAnswer> examQuestionsOptionList) {
		super();
		this.examQuestionsContentData = examQuestionsContentData;
		this.examQuestionsOptionList = examQuestionsOptionList;
	}
	
	
}
