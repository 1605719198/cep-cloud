package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.ex.domain.ExamTaskManager;
import com.jlkj.human.ex.mapper.ExamTaskManagerMapper;
import com.jlkj.human.ex.service.IExamTaskGroupService;
import com.jlkj.human.ex.service.IExamTaskManagerService;
import com.jlkj.human.ex.service.IExamTaskPersonService;
import com.jlkj.human.ex.service.IExamTaskQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建考试Service业务层处理
 * 
 * @author qnsdt
 * @date 2023-01-06
 */
@Service
public class ExamTaskManagerServiceImpl implements IExamTaskManagerService
{
    @Autowired
    private ExamTaskManagerMapper examTaskManagerMapper;

    @Autowired
    private IExamTaskGroupService examTaskGroupService;

    @Autowired
    private IExamTaskQuestionsService examTaskQuestionsService;

    @Autowired
    private IExamTaskPersonService examTaskPersonService;

    /**
     * 查询创建考试
     * 
     * @param examCode 创建考试ID
     * @return 创建考试
     */
    @Override
    public ExamTaskManager selectExamTaskManagerById(String examCode)
    {
        return examTaskManagerMapper.selectExamTaskManagerById(examCode);
    }

    /**
     * 查询创建考试列表
     * 
     * @param examTaskManager 创建考试
     * @return 创建考试
     */
    @Override
    public List<ExamTaskManager> selectExamTaskManagerList(ExamTaskManager examTaskManager)
    {
        return examTaskManagerMapper.selectExamTaskManagerList(examTaskManager);
    }

    /**
     * 新增创建考试
     * 
     * @param examTaskManager 创建考试
     * @return 结果
     */
    @Override
    public int insertExamTaskManager(ExamTaskManager examTaskManager)
    {
        examTaskManager.setCreateTime(DateUtils.getNowDate());
        return examTaskManagerMapper.insertExamTaskManager(examTaskManager);
    }

    /**
     * 修改创建考试
     * 
     * @param examTaskManager 创建考试
     * @return 结果
     */
    @Override
    public int updateExamTaskManager(ExamTaskManager examTaskManager)
    {
        return examTaskManagerMapper.updateExamTaskManager(examTaskManager);
    }

    /**
     * 批量删除创建考试
     * 
     * @param examCodes 需要删除的创建考试ID
     * @return 结果
     */
    @Override
    public int deleteExamTaskManagerByIds(String[] examCodes)
    {
    	// 删除题目
    	examTaskQuestionsService.deleteExamTaskQuestionsByIds(examCodes);
    	// 删除人员分组
    	examTaskGroupService.deleteExamTaskGroupByIds(examCodes);
    	// 删除人员
    	examTaskPersonService.deleteExamTaskPersonByIds(examCodes);
    	
        return examTaskManagerMapper.deleteExamTaskManagerByIds(examCodes);
    }

    /**
     * 删除创建考试信息
     * 
     * @param examCode 创建考试ID
     * @return 结果
     */
    @Override
    public int deleteExamTaskManagerById(String examCode)
    {
        return examTaskManagerMapper.deleteExamTaskManagerById(examCode);
    }
}
