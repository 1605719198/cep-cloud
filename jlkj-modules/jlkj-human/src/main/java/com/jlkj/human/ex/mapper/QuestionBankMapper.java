package com.jlkj.human.ex.mapper;

import com.jlkj.human.ex.domain.QuestionBank;

import java.util.List;

/**
 * 题库Mapper接口
 * 
 * @author jlkj
 * @date 2023-07-03
 */
public interface QuestionBankMapper 
{
    /**
     * 查询题库
     * 
     * @param id 题库主键
     * @return 题库
     */
    public QuestionBank selectQuestionBankById(String id);

    /**
     * 查询题库列表
     * 
     * @param questionBank 题库
     * @return 题库集合
     */
    public List<QuestionBank> selectQuestionBankList(QuestionBank questionBank);

    /**
     * 新增题库
     * 
     * @param questionBank 题库
     * @return 结果
     */
    public int insertQuestionBank(QuestionBank questionBank);

    /**
     * 修改题库
     * 
     * @param questionBank 题库
     * @return 结果
     */
    public int updateQuestionBank(QuestionBank questionBank);

    /**
     * 删除题库
     * 
     * @param id 题库主键
     * @return 结果
     */
    public int deleteQuestionBankById(String id);

    /**
     * 批量删除题库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionBankByIds(String[] ids);

    /**
     * 查询题库
     *
     * @param compId 题库主键
     * @return 题库
     */
    public List<QuestionBank> selectQuestionBankByCompId(String compId);
}
