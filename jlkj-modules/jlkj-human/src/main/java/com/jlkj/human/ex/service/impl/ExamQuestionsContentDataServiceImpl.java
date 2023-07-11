package com.jlkj.human.ex.service.impl;

import com.jlkj.human.ex.domain.ExamQuestionsContentData;
import com.jlkj.human.ex.mapper.ExamQuestionsContentDataMapper;
import com.jlkj.human.ex.service.IExamQuestionsContentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamQuestionsContentDataServiceImpl implements IExamQuestionsContentDataService {
	@Autowired
	ExamQuestionsContentDataMapper examQuestionsContentDataMapper;
	
	
	@Override
	public ExamQuestionsContentData selectExamQuestionsContent(ExamQuestionsContentData questionsContentData) {
		// TODO Auto-generated method stub
		return examQuestionsContentDataMapper.selectExamQuestionsContent(questionsContentData);
	}

}
