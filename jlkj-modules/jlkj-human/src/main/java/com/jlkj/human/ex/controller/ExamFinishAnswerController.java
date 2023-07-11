package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamFinishAnswer;
import com.jlkj.human.ex.service.IExamFinishAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试成绩Controller
 * 
 * @author qnsdt
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/onlineexam/finishanswer")
public class ExamFinishAnswerController extends BaseController
{
    @Autowired
    private IExamFinishAnswerService examFinishAnswerService;

    /**
     * 查询考试成绩列表
     */
    @RequiresPermissions("onlineexam:finishanswer:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamFinishAnswer examFinishAnswer)
    {
        startPage();
        List<ExamFinishAnswer> list = examFinishAnswerService.selectExamFinishAnswerList(examFinishAnswer);
        return getDataTable(list);
    }

    /**
     * 导出考试成绩列表
     */
    @RequiresPermissions("onlineexam:finishanswer:export")
    @Log(title = "考试成绩", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamFinishAnswer examFinishAnswer)
    {
        List<ExamFinishAnswer> list = examFinishAnswerService.selectExamFinishAnswerList(examFinishAnswer);
        ExcelUtil<ExamFinishAnswer> util = new ExcelUtil<ExamFinishAnswer>(ExamFinishAnswer.class);
        util.exportExcel( response,list, "考试成绩数据");
    }

    /**
     * 获取考试成绩详细信息
     */
    @RequiresPermissions("onlineexam:finishanswer:query")
    @GetMapping(value = "/{examCode}")
    public AjaxResult getInfo(@PathVariable("examCode") String examCode)
    {
        return AjaxResult.success(examFinishAnswerService.selectExamFinishAnswerById(examCode));
    }

    /**
     * 新增考试成绩
     */
    @RequiresPermissions("onlineexam:finishanswer:add")
    @Log(title = "考试成绩", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamFinishAnswer examFinishAnswer)
    {
        return toAjax(examFinishAnswerService.insertExamFinishAnswer(examFinishAnswer));
    }
    
    /**
     * 维护考试成绩
     */
    @PostMapping("/save")
    public AjaxResult save(@RequestBody ExamFinishAnswer examFinishAnswer)
    {
    	System.out.println("examFinishAnswer:" + examFinishAnswer.toString());
        return toAjax(examFinishAnswerService.intoExamFinishAnswer(examFinishAnswer));
    }

    /**
     * 修改考试成绩
     */
    @RequiresPermissions("onlineexam:finishanswer:edit")
    @Log(title = "考试成绩", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamFinishAnswer examFinishAnswer)
    {
        return toAjax(examFinishAnswerService.updateExamFinishAnswer(examFinishAnswer));
    }

    /**
     * 删除考试成绩
     */
    @RequiresPermissions("onlineexam:finishanswer:remove")
    @Log(title = "考试成绩", businessType = BusinessType.DELETE)
	@DeleteMapping("/{examCodes}")
    public AjaxResult remove(@PathVariable String[] examCodes)
    {
        return toAjax(examFinishAnswerService.deleteExamFinishAnswerByIds(examCodes));
    }
}
