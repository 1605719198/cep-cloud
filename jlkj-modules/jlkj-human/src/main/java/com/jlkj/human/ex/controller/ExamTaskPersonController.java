package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamTaskPerson;
import com.jlkj.human.ex.service.IExamTaskPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试人员Controller
 *
 * @author 111191
 * @date 2023-07-16
 */
@RestController
@RequestMapping("/taskperson")
public class ExamTaskPersonController extends BaseController
{
    @Autowired
    private IExamTaskPersonService examTaskPersonService;

    /**
     * 查询考试人员列表
     */
    @RequiresPermissions("human:taskperson:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamTaskPerson examTaskPerson)
    {
        startPage();
        List<ExamTaskPerson> list = examTaskPersonService.selectExamTaskPersonList(examTaskPerson);
        return getDataTable(list);
    }

    /**
     * 导出考试人员列表
     */
    @RequiresPermissions("human:taskperson:export")
    @Log(title = "考试人员", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamTaskPerson examTaskPerson)
    {
        List<ExamTaskPerson> list = examTaskPersonService.selectExamTaskPersonList(examTaskPerson);
        ExcelUtil<ExamTaskPerson> util = new ExcelUtil<ExamTaskPerson>(ExamTaskPerson.class);
        util.exportExcel(response,list, "考试人员数据");
    }

    /**
     * 获取考试人员详细信息
     */
    @RequiresPermissions("human:taskperson:query")
    @GetMapping(value = "/{examCode}")
    public AjaxResult getInfo(@PathVariable("examCode") String examCode)
    {
        return AjaxResult.success(examTaskPersonService.selectExamTaskPersonById(examCode));
    }

    /**
     * 新增考试人员
     */
    @RequiresPermissions("human:taskperson:add")
    @Log(title = "考试人员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ExamTaskPerson examTaskPerson)
    {
        return toAjax(examTaskPersonService.insertExamTaskPerson(examTaskPerson));
    }

    /**
     * 修改考试人员
     */
    @RequiresPermissions("human:taskperson:edit")
    @Log(title = "考试人员", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamTaskPerson examTaskPerson)
    {
        return toAjax(examTaskPersonService.updateExamTaskPerson(examTaskPerson));
    }

    /**
     * 更新考试开始时间
     */
    @PostMapping("/starttime")
    public AjaxResult updateStartTime(@RequestBody ExamTaskPerson examTaskPerson)
    {
        return toAjax(examTaskPersonService.updateStartTime(examTaskPerson));
    }
    /**
     * 删除考试人员
     */
    @RequiresPermissions("human:taskperson:remove")
    @Log(title = "考试人员", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{examCodes}")
    public AjaxResult remove(@PathVariable String[] examCodes)
    {
        return toAjax(examTaskPersonService.deleteExamTaskPersonByIds(examCodes));
    }
}
