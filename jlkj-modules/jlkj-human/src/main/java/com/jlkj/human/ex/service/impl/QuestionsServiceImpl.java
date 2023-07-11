package com.jlkj.human.ex.service.impl;

import java.util.List;
import com.jlkj.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.ex.mapper.QuestionsMapper;
import com.jlkj.human.ex.domain.Questions;
import com.jlkj.human.ex.service.IQuestionsService;

/**
 * 试题Service业务层处理
 *
 * @author 116519
 * @date 2023-07-04
 * @version 1.0
 */
@Service
public class QuestionsServiceImpl implements IQuestionsService
{
    @Autowired
    private QuestionsMapper questionsMapper;

    /**
     * 查询试题
     *
     * @author 116519
     * @date 2023-07-04
     * @param id 试题主键
     * @return 试题
     */
    @Override
    public Questions selectQuestionsById(Long id)
    {
        return questionsMapper.selectQuestionsById(id);
    }

    /**
     * 查询试题列表
     *
     * @author 116519
     * @date 2023-07-04
     * @param questions 试题
     * @return 试题
     */
    @Override
    public List<Questions> selectQuestionsList(Questions questions)
    {
        return questionsMapper.selectQuestionsList(questions);
    }

    /**
     * 新增试题
     *
     * @author 116519
     * @date 2023-07-04
     * @param questions 试题
     * @return 结果
     */
    @Override
    public int insertQuestions(Questions questions)
    {
        questions.setCreateTime(DateUtils.getNowDate());
        return questionsMapper.insertQuestions(questions);
    }

    /**
     * 修改试题
     *
     * @author 116519
     * @date 2023-07-04
     * @param questions 试题
     * @return 结果
     */
    @Override
    public int updateQuestions(Questions questions)
    {
        questions.setUpdateTime(DateUtils.getNowDate());
        return questionsMapper.updateQuestions(questions);
    }

    /**
     * 批量删除试题
     *
     * @author 116519
     * @date 2023-07-04
     * @param ids 需要删除的试题主键
     * @return 结果
     */
    @Override
    public int deleteQuestionsByIds(Long[] ids)
    {
        return questionsMapper.deleteQuestionsByIds(ids);
    }

    /**
     * 删除试题信息
     *
     * @author 116519
     * @date 2023-07-04
     * @param id 试题主键
     * @return 结果
     */
    @Override
    public int deleteQuestionsById(Long id)
    {
        return questionsMapper.deleteQuestionsById(id);
    }
}
