package com.jlkj.human.ex.service;

import java.util.List;
import com.jlkj.human.ex.domain.Questions;

/**
 * 试题Service接口
 * 
 * @author 116519
 * @date 2023-07-04
 */
public interface IQuestionsService 
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
     * @author 116519
     * @date 2023-07-04
     * @param questions 试题
     * @return 试题集合
     */
    public List<Questions> selectQuestionsList(Questions questions);

    /**
     * 新增试题
     *
     * @author 116519
     * @date 2023-07-04
     * @param questions 试题
     * @return 结果
     */
    public int insertQuestions(Questions questions);

    /**
     * 修改试题
     *
     * @author 116519
     * @date 2023-07-04
     * @param questions 试题
     * @return 结果
     */
    public int updateQuestions(Questions questions);

    /**
     * 批量删除试题
     *
     * @author 116519
     * @date 2023-07-04
     * @param ids 需要删除的试题主键集合
     * @return 结果
     */
    public int deleteQuestionsByIds(Long[] ids);

    /**
     * 删除试题信息
     *
     * @author 116519
     * @date 2023-07-04
     * @param id 试题主键
     * @return 结果
     */
    public int deleteQuestionsById(Long id);
}
