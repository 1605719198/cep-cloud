package com.jlkj.human.ex.service.impl;

import com.jlkj.human.ex.domain.ExamQuestionsProperty;
import com.jlkj.human.ex.mapper.ExamQuestionsListMapper;
import com.jlkj.human.ex.service.IExamQuestionsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 题目属性Service业务层处理
 * 
 * @author 111191
 * @date 2023-07-11
 */
@Service
public class ExamQuestionsListServiceImpl implements IExamQuestionsListService
{
    @Autowired
    private ExamQuestionsListMapper examQuestionsListMapper;


    /**
     * 查询题目属性列表
     * 
     * @param bankCodes 题目属性
     * @return 题目属性
     */
    @Override
    public List<ExamQuestionsProperty> selectExamQuestionsList(String[] bankCodes)
    {
        return examQuestionsListMapper.selectExamQuestionsList(bankCodes);
    }


	@Override
	public List<ExamQuestionsProperty> selectChoiceExamQuestionsList(String examCode) {
		// TODO Auto-generated method stub
		return examQuestionsListMapper.selectChoiceExamQuestionsList(examCode);
	}




}
