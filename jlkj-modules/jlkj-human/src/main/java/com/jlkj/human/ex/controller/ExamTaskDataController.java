package com.jlkj.human.ex.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.ex.domain.ExamTaskData;
import com.jlkj.human.ex.service.IExamTaskDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试任务Controller
 *
 * @Author: 111191
 * @Date: 2023年7月11日, 0011 上午 11:42:32
 */
@RestController
@RequestMapping("/examtask")
public class ExamTaskDataController extends BaseController {
    @Autowired
    private IExamTaskDataService examTaskDataService;

    /**
     * 保存
     *
     * @param examTaskData
     * @return com.jlkj.common.core.web.domain.AjaxResult
     * @Author: 111191
     * @Date: 2023年7月11日, 0011 上午 11:43:06
     */
    @PostMapping("/save")
    public AjaxResult save(@RequestBody ExamTaskData examTaskData) {
        // 数据判断
        if (examTaskData == null) {
            return AjaxResult.error(-1, "传入数据为空!");
        }

        if (examTaskData.getExamTaskManager() == null) {
            return AjaxResult.error(-1, "传入任务属性信息为空!");
        }

        if (examTaskData.getTaskQuestionsList() == null || examTaskData.getTaskQuestionsList().size() == 0) {
            return AjaxResult.error(-1, "传入题库信息为空!");
        }

        if (examTaskData.getTaskGroupList() == null || examTaskData.getTaskGroupList().size() == 0) {
            return AjaxResult.error(-1, "传入人员分组信息为空!");
        }

        try {
            examTaskDataService.saveData(examTaskData);
        } catch (Exception e) {
            return AjaxResult.error(-1, e.getMessage());
        }
        return AjaxResult.success("保存成功");
    }
}
