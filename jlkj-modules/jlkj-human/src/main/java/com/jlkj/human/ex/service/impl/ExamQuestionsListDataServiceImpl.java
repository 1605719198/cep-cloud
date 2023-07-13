package com.jlkj.human.ex.service.impl;

import com.jlkj.human.ex.domain.ExamQuestionsListData;
import com.jlkj.human.ex.mapper.ExamQuestionsListDataMapper;
import com.jlkj.human.ex.service.IExamQuestionsListDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * @Author: 111191
 * @Date: 2023年7月11日, 0011 下午 02:13:36
 */
@Service
public class ExamQuestionsListDataServiceImpl implements IExamQuestionsListDataService {
	@Autowired
	ExamQuestionsListDataMapper examQuestionsListDataMapper;
	
	@Override
	public List<ExamQuestionsListData> selectQuestionsList(ExamQuestionsListData examQuestionsListData) {
		// TODO Auto-generated method stub
		return examQuestionsListDataMapper.selectQuestionsList(examQuestionsListData);
	}

}
