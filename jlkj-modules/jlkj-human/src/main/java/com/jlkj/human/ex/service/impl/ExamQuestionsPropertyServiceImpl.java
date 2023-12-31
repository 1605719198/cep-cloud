package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.ex.domain.ExamQuestionsProperty;
import com.jlkj.human.ex.mapper.ExamQuestionsPropertyMapper;
import com.jlkj.human.ex.service.IExamQuestionsPropertyService;
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
public class ExamQuestionsPropertyServiceImpl implements IExamQuestionsPropertyService
{
    @Autowired
    private ExamQuestionsPropertyMapper examQuestionsPropertyMapper;

    /**
     * 查询题目属性
     * 
     * @param questionsCode 题目属性ID
     * @return 题目属性
     */
    @Override
    public ExamQuestionsProperty selectExamQuestionsPropertyById(String questionsCode)
    {
        return examQuestionsPropertyMapper.selectExamQuestionsPropertyById(questionsCode);
    }

    /**
     * 查询题目属性列表
     * 
     * @param examQuestionsProperty 题目属性
     * @return 题目属性
     */
    @Override
    public List<ExamQuestionsProperty> selectExamQuestionsPropertyList(ExamQuestionsProperty examQuestionsProperty)
    {
        return examQuestionsPropertyMapper.selectExamQuestionsPropertyList(examQuestionsProperty);
    }

    /**
     * 新增题目属性
     * 
     * @param examQuestionsProperty 题目属性
     * @return 结果
     */
    @Override
    public int insertExamQuestionsProperty(ExamQuestionsProperty examQuestionsProperty)
    {
        examQuestionsProperty.setCreateTime(DateUtils.getNowDate());
        return examQuestionsPropertyMapper.insertExamQuestionsProperty(examQuestionsProperty);
    }

    /**
     * 修改题目属性
     * 
     * @param examQuestionsProperty 题目属性
     * @return 结果
     */
    @Override
    public int updateExamQuestionsProperty(ExamQuestionsProperty examQuestionsProperty)
    {
        return examQuestionsPropertyMapper.updateExamQuestionsProperty(examQuestionsProperty);
    }

    /**
     * 批量删除题目属性
     * 
     * @param questionsCodes 需要删除的题目属性ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionsPropertyByIds(String[] questionsCodes)
    {
        return examQuestionsPropertyMapper.deleteExamQuestionsPropertyByIds(questionsCodes);
    }

    /**
     * 删除题目属性信息
     * 
     * @param questionsCode 题目属性ID
     * @return 结果
     */
    @Override
    public int deleteExamQuestionsPropertyById(String questionsCode)
    {
        return examQuestionsPropertyMapper.deleteExamQuestionsPropertyById(questionsCode);
    }
}
