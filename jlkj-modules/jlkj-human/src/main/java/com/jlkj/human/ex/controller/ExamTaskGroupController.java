package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.ex.domain.ExamTaskGroup;
import com.jlkj.human.ex.service.IExamTaskGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务分组Controller
 * 
 * @author qnsdt
 * @date 2023-02-10
 */
@RestController
@RequestMapping("/taskgroup")
public class ExamTaskGroupController extends BaseController
{
    @Autowired
    private IExamTaskGroupService examTaskGroupService;

    /**
     * 查询任务分组列表
     */
    @GetMapping("/list")
    @RequiresPermissions("human:taskgroup:list")
    public TableDataInfo list(ExamTaskGroup examTaskGroup)
    {
        startPage();
        List<ExamTaskGroup> list = examTaskGroupService.selectExamTaskGroupList(examTaskGroup);
        return getDataTable(list);
    }

    /**
     * 导出任务分组列表
     */
    @GetMapping("/export")
    @RequiresPermissions("human:taskgroup:export")
    public void export(HttpServletResponse response, ExamTaskGroup examTaskGroup)throws Exception
    {
        List<ExamTaskGroup> list = examTaskGroupService.selectExamTaskGroupList(examTaskGroup);
        ExcelUtil<ExamTaskGroup> util = new ExcelUtil<ExamTaskGroup>(ExamTaskGroup.class);
        util.exportExcel(response,list, "任务分组数据");
    }

    /**
     * 获取任务分组详细信息
     */
    @GetMapping(value = "/{examCode}")
    public AjaxResult getInfo(@PathVariable("examCode") String examCode)
    {
        return AjaxResult.success(examTaskGroupService.selectExamTaskGroupById(examCode));
    }

    /**
     * 新增任务分组
     */
    @PostMapping
    @RequiresPermissions("human:taskgroup:add")
    public AjaxResult add(@RequestBody ExamTaskGroup examTaskGroup)
    {
        return toAjax(examTaskGroupService.insertExamTaskGroup(examTaskGroup));
    }

    /**
     * 修改任务分组
     */
    @PostMapping("/update")
    @RequiresPermissions("human:taskgroup:edit")
    public AjaxResult edit(@RequestBody ExamTaskGroup examTaskGroup)
    {
        return toAjax(examTaskGroupService.updateExamTaskGroup(examTaskGroup));
    }

    @PostMapping("/batchupdate")
    public AjaxResult batchUpdate(@RequestBody List<ExamTaskGroup> examTaskGroupList)
    {
    	try
    	{
    		examTaskGroupService.batchUpdateExamTaskGroup(examTaskGroupList);
    	}
    	catch(Exception e) {
    		return AjaxResult.error(e.getMessage());
    	}
    	
        return AjaxResult.success();
    }
    /**
     * 删除任务分组
     */
    @GetMapping("/delete/{examCodes}")
    @RequiresPermissions("human:taskgroup:remove")
    public AjaxResult remove(@PathVariable String[] examCodes)
    {
        return toAjax(examTaskGroupService.deleteExamTaskGroupByIds(examCodes));
    }
}
