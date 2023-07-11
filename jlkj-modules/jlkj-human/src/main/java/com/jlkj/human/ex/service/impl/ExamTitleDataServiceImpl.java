package com.jlkj.human.ex.service.impl;

import com.jlkj.human.ex.domain.ExamTitleData;
import com.jlkj.human.ex.mapper.ExamTitleDataMapper;
import com.jlkj.human.ex.service.IExamTitleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExamTitleDataServiceImpl implements IExamTitleDataService {
	@Autowired
	ExamTitleDataMapper examTitleDataMapper;
	
	@Override
	public ExamTitleData selectCurrentExam(String userCode) {
		// TODO Auto-generated method stub
		return examTitleDataMapper.selectCurrentExam(userCode);
	}

}
