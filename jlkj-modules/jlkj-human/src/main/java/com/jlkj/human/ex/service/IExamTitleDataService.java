package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.ExamTitleData;

public interface IExamTitleDataService {
	public ExamTitleData selectCurrentExam(String userCode);
}
