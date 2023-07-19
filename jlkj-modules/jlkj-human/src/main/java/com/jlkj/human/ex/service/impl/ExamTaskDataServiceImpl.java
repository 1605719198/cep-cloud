package com.jlkj.human.ex.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.human.ex.domain.ExamTaskData;
import com.jlkj.human.ex.domain.ExamTaskGroup;
import com.jlkj.human.ex.domain.ExamTaskManager;
import com.jlkj.human.ex.domain.ExamTaskQuestions;
import com.jlkj.human.ex.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 考试任务ServiceImpl
 *
 * @Author: 111191
 * @Date: 2023年7月11日, 0011 下午 01:20:42
 */

@Service
public class ExamTaskDataServiceImpl implements IExamTaskDataService {
    @Autowired
    private IExamTaskGroupService examTaskGroupService;
    @Autowired
    private IExamTaskManagerService examTaskManagerService;
    @Autowired
    private IExamTaskQuestionsService examTaskQuestionsService;
    @Autowired
    private IExamTaskPersonService examTaskPersonService;

    @Override
    public int saveData(ExamTaskData examTaskData) {
        ExamTaskManager examTaskManager = examTaskData.getExamTaskManager();
        int count = 0;
        try {
            // 安全删除
            examTaskManagerService.deleteExamTaskManagerById(examTaskManager.getExamCode());
            // 保存数据
            count = examTaskManagerService.insertExamTaskManager(examTaskManager);
        } catch (Exception e) {
            new ServiceException("保存属性出错,信息:" + e.getMessage());
        }
        List<ExamTaskQuestions> questionsList = examTaskData.getTaskQuestionsList();
        // 安全删除
        examTaskQuestionsService.deleteExamTaskQuestionsById(examTaskManager.getExamCode());
        for (ExamTaskQuestions examTaskQuestions : questionsList) {
            try {
                count = examTaskQuestionsService.insertExamTaskQuestions(examTaskQuestions);
            } catch (Exception e) {
                new ServiceException("保存题目信息出错,信息:" + e.getMessage());
            }
        }
        List<ExamTaskGroup> groupList = examTaskData.getTaskGroupList();
        // 安全删除
        examTaskGroupService.deleteExamTaskGroupById(examTaskManager.getExamCode());
        for (ExamTaskGroup examTaskGroup : groupList) {
            try {
                count = examTaskGroupService.insertExamTaskGroup(examTaskGroup);
            } catch (Exception e) {
                new ServiceException("保存分组信息出错,信息:" + e.getMessage());
            }
        }
        // 安全删除
        examTaskPersonService.deleteExamTaskPersonById(examTaskManager.getExamCode());
        try {
            count = examTaskPersonService.batchInsertPerson(examTaskManager.getExamCode());
        } catch (Exception e) {
            new ServiceException("保存考试人员信息出错,信息:" + e.getMessage());
        }

        return 1;
    }

}
