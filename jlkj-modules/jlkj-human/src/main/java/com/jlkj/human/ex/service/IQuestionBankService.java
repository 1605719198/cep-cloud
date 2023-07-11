package com.jlkj.human.ex.service;

import com.jlkj.human.ex.domain.QuestionBank;

import java.util.List;

/**
 * 题库Service接口
 * 
 * @author jlkj
 * @date 2023-07-03
 */
public interface IQuestionBankService 
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
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank 题库
     * @return 题库集合
     */
    public List<QuestionBank> selectQuestionBankList(QuestionBank questionBank);

    /**
     * 新增题库
     *
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank 题库
     * @return 结果
     */
    public int insertQuestionBank(QuestionBank questionBank);

    /**
     * 修改题库
     *
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank 题库
     * @return 结果
     */
    public int updateQuestionBank(QuestionBank questionBank);

    /**
     * 批量删除题库
     *
     * @author jlkj
     * @date 2023-07-03
     * @param ids 需要删除的题库主键集合
     * @return 结果
     */
    public int deleteQuestionBankByIds(String[] ids);

    /**
     * 删除题库信息
     *
     * @author jlkj
     * @date 2023-07-03
     * @param id 题库主键
     * @return 结果
     */
    public int deleteQuestionBankById(String id);
    /**
     * 查询题库下拉
     *
     * @param compId 公司别
     * @return 题库
     */
    public List<QuestionBank>  selectQuestionBankListByCompId(String compId);
}
