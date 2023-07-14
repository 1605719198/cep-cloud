package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.human.ex.domain.ExamTaskQuestions;
import com.jlkj.human.ex.service.IExamTaskQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务题目Controller
 * 
 * @author qnsdt
 * @date 2023-02-10
 */
@RestController
@RequestMapping("/taskquestions")
public class ExamTaskQuestionsController extends BaseController
{
    @Autowired
    private IExamTaskQuestionsService examTaskQuestionsService;

    /**
     * 查询任务题目列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamTaskQuestions examTaskQuestions)
    {
        startPage();
        List<ExamTaskQuestions> list = examTaskQuestionsService.selectExamTaskQuestionsList(examTaskQuestions);
        return getDataTable(list);
    }

    /**
     * 导出任务题目列表
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamTaskQuestions examTaskQuestions)
    {
        List<ExamTaskQuestions> list = examTaskQuestionsService.selectExamTaskQuestionsList(examTaskQuestions);
        ExcelUtil<ExamTaskQuestions> util = new ExcelUtil<ExamTaskQuestions>(ExamTaskQuestions.class);
        util.exportExcel(response,list, "任务题目数据");
    }

    /**
     * 获取任务题目详细信息
     */
    @GetMapping(value = "/{examCode}")
    public AjaxResult getInfo(@PathVariable("examCode") String examCode)
    {
        return AjaxResult.success(examTaskQuestionsService.selectExamTaskQuestionsById(examCode));
    }


    @PostMapping("/batchupdate")
    public AjaxResult batchUpdate(@RequestBody List<ExamTaskQuestions> examTaskQuestionsList)
    {
    	int count = -1;
    	
    	try
    	{
    		count = examTaskQuestionsService.batchUpdateExamTaskQuestions(examTaskQuestionsList);
    	}
    	catch(Exception e) {
    		return AjaxResult.error(e.getMessage());
    	}
    	return AjaxResult.success("更新成功");
    }

    /**
     * 修改任务题目
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamTaskQuestions examTaskQuestions)
    {
        return toAjax(examTaskQuestionsService.updateExamTaskQuestions(examTaskQuestions));
    }

    /**
     * 删除任务题目
     */
    @GetMapping("/delete/{examCodes}")
    public AjaxResult remove(@PathVariable String[] examCodes)
    {
        return toAjax(examTaskQuestionsService.deleteExamTaskQuestionsByIds(examCodes));
    }
}
