package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamQuestionsAnswer;
import com.jlkj.human.ex.service.IExamQuestionsAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试题目答案Controller
 * 
 * @author 111191
 * @Date:  2023-07-11
 */
@RestController
@RequestMapping("/answer")
public class ExamQuestionsAnswerController extends BaseController
{
    @Autowired
    private IExamQuestionsAnswerService examQuestionsAnswerService;

    /**
     * 查询考试题目答案列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamQuestionsAnswer examQuestionsAnswer)
    {
        startPage();
        List<ExamQuestionsAnswer> list = examQuestionsAnswerService.selectExamQuestionsAnswerList(examQuestionsAnswer);
        return getDataTable(list);
    }

    @GetMapping("/optionlist/{questionsCode}")
    public AjaxResult optionList(@PathVariable("questionsCode") String questionsCode)
    {
        List<ExamQuestionsAnswer> list = examQuestionsAnswerService.selectExamQuestionsOptionList(questionsCode);
        return AjaxResult.success(list);
    }

    /**
     * 导出考试题目答案列表
     */
    @RequiresPermissions("human:answer:export")
    @Log(title = "考试题目答案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamQuestionsAnswer examQuestionsAnswer)
    {
        List<ExamQuestionsAnswer> list = examQuestionsAnswerService.selectExamQuestionsAnswerList(examQuestionsAnswer);
        ExcelUtil<ExamQuestionsAnswer> util = new ExcelUtil<ExamQuestionsAnswer>(ExamQuestionsAnswer.class);
        util.exportExcel(response,list, "考试题目答案数据");
    }

    /**
     * 获取考试题目答案详细信息
     */
    @RequiresPermissions("human:answer:query")
    @GetMapping(value = "/{questionsCode}")
    public AjaxResult getInfo(@PathVariable("questionsCode") String questionsCode)
    {
        return AjaxResult.success(examQuestionsAnswerService.selectExamQuestionsAnswerById(questionsCode));
    }

    /**
     * 新增考试题目答案
     */
    @RequiresPermissions("human:answer:add")
    @Log(title = "考试题目答案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ExamQuestionsAnswer examQuestionsAnswer)
    {
        return toAjax(examQuestionsAnswerService.insertExamQuestionsAnswer(examQuestionsAnswer));
    }

    /**
     * 修改考试题目答案
     */
    @RequiresPermissions("human:answer:edit")
    @Log(title = "考试题目答案", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamQuestionsAnswer examQuestionsAnswer)
    {
        return toAjax(examQuestionsAnswerService.updateExamQuestionsAnswer(examQuestionsAnswer));
    }

    /**
     * 删除考试题目答案
     */
    @RequiresPermissions("human:answer:remove")
    @Log(title = "考试题目答案", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{questionsCodes}")
    public AjaxResult remove(@PathVariable String[] questionsCodes)
    {
        return toAjax(examQuestionsAnswerService.deleteExamQuestionsAnswerByIds(questionsCodes));
    }
}
