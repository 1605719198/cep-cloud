package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.human.ex.domain.ExamTaskAnswer;
import com.jlkj.human.ex.service.IExamTaskAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试实时数据Controller
 * 
 * @author qnsdt
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/onlineexam/taskanswer")
public class ExamTaskAnswerController extends BaseController
{
    @Autowired
    private IExamTaskAnswerService examTaskAnswerService;

    /**
     * 查询考试实时数据列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamTaskAnswer examTaskAnswer)
    {
        startPage();
        List<ExamTaskAnswer> list = examTaskAnswerService.selectExamTaskAnswerList(examTaskAnswer);
        return getDataTable(list);
    }

    /**
     * 导出考试实时数据列表
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamTaskAnswer examTaskAnswer)
    {
        List<ExamTaskAnswer> list = examTaskAnswerService.selectExamTaskAnswerList(examTaskAnswer);
        ExcelUtil<ExamTaskAnswer> util = new ExcelUtil<ExamTaskAnswer>(ExamTaskAnswer.class);
        util.exportExcel(response,list, "考试实时数据数据");
    }

    /**
     * 获取考试实时数据详细信息
     */
    @GetMapping(value = "/{examCode}")
    public AjaxResult getInfo(@PathVariable("examCode") String examCode)
    {
        return AjaxResult.success(examTaskAnswerService.selectExamTaskAnswerById(examCode));
    }

    /**
     * 新增考试实时数据
     */
    @PostMapping
    public AjaxResult add(@RequestBody ExamTaskAnswer examTaskAnswer)
    {
    	
        return toAjax(examTaskAnswerService.insertExamTaskAnswer(examTaskAnswer));
    }

    /**
     * 修改考试实时数据
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ExamTaskAnswer examTaskAnswer)
    {
        return toAjax(examTaskAnswerService.updateExamTaskAnswer(examTaskAnswer));
    }

    /**
     * 删除考试实时数据
     */
	@GetMapping("/remove/{examCodes}")
    public AjaxResult remove(@PathVariable String[] examCodes)
    {
        return toAjax(examTaskAnswerService.deleteExamTaskAnswerByIds(examCodes));
    }
    
    /**
     * 删除考试实时数据
     */
	@PostMapping("/delete")
    public AjaxResult delete(ExamTaskAnswer examTaskAnswer)
    {
        return toAjax(examTaskAnswerService.deleteExamTaskAnswer(examTaskAnswer));
    }
}
