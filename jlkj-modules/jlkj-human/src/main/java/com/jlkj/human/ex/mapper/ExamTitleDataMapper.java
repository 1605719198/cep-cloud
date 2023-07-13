package com.jlkj.human.ex.mapper;


import com.jlkj.human.ex.domain.ExamTitleData;

public interface ExamTitleDataMapper {
	public ExamTitleData selectCurrentExam(String userCode);
}
