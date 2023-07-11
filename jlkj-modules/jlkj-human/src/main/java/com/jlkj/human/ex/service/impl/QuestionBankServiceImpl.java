package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.ex.domain.QuestionBank;
import com.jlkj.human.ex.mapper.QuestionBankMapper;
import com.jlkj.human.ex.service.IQuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 题库Service业务层处理
 *
 * @author jlkj
 * @date 2023-07-03
 * @version 1.0
 */
@Service
public class QuestionBankServiceImpl implements IQuestionBankService
{
    @Autowired
    private QuestionBankMapper questionBankMapper;

    /**
     * 查询题库
     *
     * @author jlkj
     * @date 2023-07-03
     * @param id 题库主键
     * @return 题库
     */
    @Override
    public QuestionBank selectQuestionBankById(String id)
    {
        return questionBankMapper.selectQuestionBankById(id);
    }

    /**
     * 查询题库列表
     *
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank 题库
     * @return 题库
     */
    @Override
    public List<QuestionBank> selectQuestionBankList(QuestionBank questionBank)
    {
        return questionBankMapper.selectQuestionBankList(questionBank);
    }

    /**
     * 新增题库
     *
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank 题库
     * @return 结果
     */
    @Override
    public int insertQuestionBank(QuestionBank questionBank)
    {
        questionBank.setId(IdUtils.simpleUUID());
        //questionBank.setCreateTime(DateUtils.getNowDate());
        return questionBankMapper.insertQuestionBank(questionBank);
    }

    /**
     * 修改题库
     *
     * @author jlkj
     * @date 2023-07-03
     * @param questionBank 题库
     * @return 结果
     */
    @Override
    public int updateQuestionBank(QuestionBank questionBank)
    {
        questionBank.setUpdateTime(DateUtils.getNowDate());
        return questionBankMapper.updateQuestionBank(questionBank);
    }

    /**
     * 批量删除题库
     *
     * @author jlkj
     * @date 2023-07-03
     * @param ids 需要删除的题库主键
     * @return 结果
     */
    @Override
    public int deleteQuestionBankByIds(String[] ids)
    {
        return questionBankMapper.deleteQuestionBankByIds(ids);
    }

    /**
     * 删除题库信息
     *
     * @author jlkj
     * @date 2023-07-03
     * @param id 题库主键
     * @return 结果
     */
    @Override
    public int deleteQuestionBankById(String id)
    {
        return questionBankMapper.deleteQuestionBankById(id);
    }

    /**
     * 查询题库下拉
     *
     * @author jlkj
     * @date 2023-07-03
     * @param compId 公司别
     * @return 题库
     */
    @Override
    public List<QuestionBank>   selectQuestionBankListByCompId(String compId)
    {
        return questionBankMapper.selectQuestionBankByCompId(compId);
    }
}
