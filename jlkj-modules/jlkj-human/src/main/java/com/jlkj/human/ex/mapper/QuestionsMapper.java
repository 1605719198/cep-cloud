package com.jlkj.human.ex.mapper;

import java.util.List;
import com.jlkj.human.ex.domain.Questions;

/**
 * 试题Mapper接口
 * 
 * @author 116519
 * @date 2023-07-04
 */
public interface QuestionsMapper 
{
    /**
     * 查询试题
     * 
     * @param id 试题主键
     * @return 试题
     */
    public Questions selectQuestionsById(Long id);

    /**
     * 查询试题列表
     * 
     * @param questions 试题
     * @return 试题集合
     */
    public List<Questions> selectQuestionsList(Questions questions);

    /**
     * 新增试题
     * 
     * @param questions 试题
     * @return 结果
     */
    public int insertQuestions(Questions questions);

    /**
     * 修改试题
     * 
     * @param questions 试题
     * @return 结果
     */
    public int updateQuestions(Questions questions);

    /**
     * 删除试题
     * 
     * @param id 试题主键
     * @return 结果
     */
    public int deleteQuestionsById(Long id);

    /**
     * 批量删除试题
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuestionsByIds(Long[] ids);
}
