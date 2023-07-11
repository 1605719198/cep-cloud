package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamQuestionsContent;
import com.jlkj.human.ex.service.IExamQuestionsContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 题目内容表Controller
 * 
 * @author qnsdt
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/questions/questionscontent")
public class ExamQuestionsContentController extends BaseController
{
    @Autowired
    private IExamQuestionsContentService examQuestionsContentService;

    /**
     * 查询题目内容表列表
     */
    @RequiresPermissions("questions:questionscontent:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamQuestionsContent examQuestionsContent)
    {
        startPage();
        List<ExamQuestionsContent> list = examQuestionsContentService.selectExamQuestionsContentList(examQuestionsContent);
        return getDataTable(list);
    }

    /**
     * 导出题目内容表列表
     */
    @RequiresPermissions("questions:questionscontent:export")
    @Log(title = "题目内容表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamQuestionsContent examQuestionsContent)
    {
        List<ExamQuestionsContent> list = examQuestionsContentService.selectExamQuestionsContentList(examQuestionsContent);
        ExcelUtil<ExamQuestionsContent> util = new ExcelUtil<ExamQuestionsContent>(ExamQuestionsContent.class);
        util.exportExcel(response,list, "题目内容表数据");
    }

    /**
     * 获取题目内容表详细信息
     */
    @GetMapping(value = "/{questionsCode}")
    public AjaxResult getInfo(@PathVariable("questionsCode") String questionsCode)
    {
        return AjaxResult.success(examQuestionsContentService.selectExamQuestionsContentById(questionsCode));
    }

    /**
     * 新增题目内容表
     */
    @RequiresPermissions("questions:questionscontent:add")
    @Log(title = "题目内容表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamQuestionsContent examQuestionsContent)
    {
        return toAjax(examQuestionsContentService.insertExamQuestionsContent(examQuestionsContent));
    }

    /**
     * 修改题目内容表
     */
    @RequiresPermissions("questions:questionscontent:edit")
    @Log(title = "题目内容表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamQuestionsContent examQuestionsContent)
    {
        return toAjax(examQuestionsContentService.updateExamQuestionsContent(examQuestionsContent));
    }

    /**
     * 删除题目内容表
     */
    @RequiresPermissions("questions:questionscontent:remove")
    @Log(title = "题目内容表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{questionsCodes}")
    public AjaxResult remove(@PathVariable String[] questionsCodes)
    {
        return toAjax(examQuestionsContentService.deleteExamQuestionsContentByIds(questionsCodes));
    }
}
