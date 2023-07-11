package com.jlkj.human.ex.mapper;

import com.hig.onlineexam.domain.dto.ExamTitleData;

public interface ExamTitleDataMapper {
	public ExamTitleData selectCurrentExam(String userCode);
}
