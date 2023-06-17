package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.pa.domain.DeptEvaluationQuery;
import com.jlkj.human.pa.service.IDeptEvaluationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 个人绩效主档Controller
 *
 * @author 267383
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/deptEvaluationQuery")
public class DeptEvaluationQueryController extends BaseController
{
    @Autowired
    private IDeptEvaluationQueryService deptEvaluationQueryService;

    /**
     * 查询个人绩效主档列表
     */
    @RequiresPermissions("human:deptEvaluationQuery:list")
    @GetMapping("/list")
    public TableDataInfo list(DeptEvaluationQuery deptEvaluationQuery)
    {
        startPage();
        List<DeptEvaluationQuery> list = deptEvaluationQueryService.selectDeptEvaluationQueryList(deptEvaluationQuery);
        return getDataTable(list);
    }

    /**
     * 导出个人绩效主档列表
     */
    @RequiresPermissions("human:deptEvaluationQuery:export")
    @Log(title = "个人绩效主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeptEvaluationQuery deptEvaluationQuery)
    {
        List<DeptEvaluationQuery> list = deptEvaluationQueryService.selectDeptEvaluationQueryList(deptEvaluationQuery);
        ExcelUtil<DeptEvaluationQuery> util = new ExcelUtil<DeptEvaluationQuery>(DeptEvaluationQuery.class);
        util.exportExcel(response, list, "个人绩效主档数据");
    }

    /**
     * 获取个人绩效主档详细信息
     */
    @RequiresPermissions("human:deptEvaluationQuery:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(deptEvaluationQueryService.selectDeptEvaluationQueryById(id));
    }

    /**
     * 新增个人绩效主档
     */
    @RequiresPermissions("human:deptEvaluationQuery:add")
    @Log(title = "个人绩效主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeptEvaluationQuery deptEvaluationQuery)
    {
        return toAjax(deptEvaluationQueryService.insertDeptEvaluationQuery(deptEvaluationQuery));
    }

    /**
     * 修改个人绩效主档
     */
    @RequiresPermissions("human:deptEvaluationQuery:edit")
    @Log(title = "个人绩效主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeptEvaluationQuery deptEvaluationQuery)
    {
        return toAjax(deptEvaluationQueryService.updateDeptEvaluationQuery(deptEvaluationQuery));
    }

    /**
     * 删除个人绩效主档
     */
    @RequiresPermissions("human:deptEvaluationQuery:remove")
    @Log(title = "个人绩效主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(deptEvaluationQueryService.deleteDeptEvaluationQueryByIds(ids));
    }
}
