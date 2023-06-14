package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.pa.domain.YearPerformance;
import com.jlkj.human.pa.service.IYearPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 年度绩效主档Controller
 *
 * @author 267383
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/yearPerformance")
public class YearPerformanceController extends BaseController
{
    @Autowired
    private IYearPerformanceService yearPerformanceService;

    /**
     * 查询年度绩效主档列表
     */
    @RequiresPermissions("human:yearPerformance:list")
    @GetMapping("/list")
    public TableDataInfo list(YearPerformance yearPerformance)
    {
        startPage();
        List<YearPerformance> list = yearPerformanceService.selectYearPerformanceList(yearPerformance);
        return getDataTable(list);
    }

    /**
     * 导出年度绩效主档列表
     */
    @RequiresPermissions("human:yearPerformance:export")
    @Log(title = "年度绩效主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YearPerformance yearPerformance)
    {
        List<YearPerformance> list = yearPerformanceService.selectYearPerformanceList(yearPerformance);
        ExcelUtil<YearPerformance> util = new ExcelUtil<YearPerformance>(YearPerformance.class);
        util.exportExcel(response, list, "年度绩效主档数据");
    }

    /**
     * 获取年度绩效主档详细信息
     */
    @RequiresPermissions("human:yearPerformance:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(yearPerformanceService.selectYearPerformanceById(id));
    }

    /**
     * 新增年度绩效主档
     */
    @RequiresPermissions("human:yearPerformance:add")
    @Log(title = "年度绩效主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YearPerformance yearPerformance)
    {
        return toAjax(yearPerformanceService.insertYearPerformance(yearPerformance));
    }

    /**
     * 修改年度绩效主档
     */
    @RequiresPermissions("human:yearPerformance:edit")
    @Log(title = "年度绩效主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YearPerformance yearPerformance)
    {
        return toAjax(yearPerformanceService.updateYearPerformance(yearPerformance));
    }

    /**
     * 删除年度绩效主档
     */
    @RequiresPermissions("human:yearPerformance:remove")
    @Log(title = "年度绩效主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(yearPerformanceService.deleteYearPerformanceByIds(ids));
    }
}
