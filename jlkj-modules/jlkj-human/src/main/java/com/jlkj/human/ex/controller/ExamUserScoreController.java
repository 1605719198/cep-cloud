package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamUserScore;
import com.jlkj.human.ex.service.IExamUserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试成绩Controller
 * 
 * @author qnsdt
 * @date 2023-03-03
 */
@RestController
@RequestMapping("/onlineexam/userscore")
public class ExamUserScoreController extends BaseController
{
    @Autowired
    private IExamUserScoreService examUserScoreService;

    /**
     * 查询考试成绩列表
     */
    @RequiresPermissions("onlineexam:userscore:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamUserScore examUserScore)
    {
        startPage();
        List<ExamUserScore> list = examUserScoreService.selectExamUserScoreList(examUserScore);
        return getDataTable(list);
    }

    /**
     * 导出考试成绩列表
     */
    @RequiresPermissions("onlineexam:userscore:export")
    @Log(title = "考试成绩", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamUserScore examUserScore)
    {
        List<ExamUserScore> list = examUserScoreService.selectExamUserScoreList(examUserScore);
        ExcelUtil<ExamUserScore> util = new ExcelUtil<ExamUserScore>(ExamUserScore.class);
        util.exportExcel(response,list, "考试成绩数据");
    }

    /**
     * 获取考试成绩详细信息
     */
    @RequiresPermissions("onlineexam:userscore:query")
    @GetMapping(value = "/{examCode}")
    public AjaxResult getInfo(@PathVariable("examCode") String examCode)
    {
        return AjaxResult.success(examUserScoreService.selectExamUserScoreById(examCode));
    }

    /**
     * 新增考试成绩
     */
    @RequiresPermissions("onlineexam:userscore:add")
    @Log(title = "考试成绩", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamUserScore examUserScore)
    {
        return toAjax(examUserScoreService.insertExamUserScore(examUserScore));
    }
    
    /**
     * 维护考试成绩
     */
     @PostMapping("/into")
    public AjaxResult into(@RequestBody ExamUserScore examUserScore)
    {
        return toAjax(examUserScoreService.intoExamUserScore(examUserScore));
    }

    /**
     * 修改考试成绩
     */
    @RequiresPermissions("@ss.hasPermi('onlineexam:userscore:edit')")
    @Log(title = "考试成绩", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamUserScore examUserScore)
    {
        return toAjax(examUserScoreService.updateExamUserScore(examUserScore));
    }

    /**
     * 删除考试成绩
     */
    @RequiresPermissions("@ss.hasPermi('onlineexam:userscore:remove')")
    @Log(title = "考试成绩", businessType = BusinessType.DELETE)
	@DeleteMapping("/{examCodes}")
    public AjaxResult remove(@PathVariable String[] examCodes)
    {
        return toAjax(examUserScoreService.deleteExamUserScoreByIds(examCodes));
    }
}
