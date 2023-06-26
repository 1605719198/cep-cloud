package com.jlkj.human.st.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.YearHolidayStatistics;
import com.jlkj.human.st.service.IYearHolidayStatisticsService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 年休假统计Controller
 *
 * @author 267383
 * @date 2023-06-25
 */
@RestController
@RequestMapping("/yearHolidayStatistics")
public class YearHolidayStatisticsController extends BaseController
{
    @Autowired
    private IYearHolidayStatisticsService yearHolidayStatisticsService;

    /**
     * 查询年休假统计列表
     */
    @RequiresPermissions("human:yearHolidayStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(YearHolidayStatistics yearHolidayStatistics)
    {
        startPage();
        List<YearHolidayStatistics> list = yearHolidayStatisticsService.selectYearHolidayStatisticsList(yearHolidayStatistics);
        return getDataTable(list);
    }

    /**
     * 导出年休假统计列表
     */
    @RequiresPermissions("human:yearHolidayStatistics:export")
    @Log(title = "年休假统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YearHolidayStatistics yearHolidayStatistics)
    {
        List<YearHolidayStatistics> list = yearHolidayStatisticsService.selectYearHolidayStatisticsList(yearHolidayStatistics);
        ExcelUtil<YearHolidayStatistics> util = new ExcelUtil<YearHolidayStatistics>(YearHolidayStatistics.class);
        util.exportExcel(response, list, "年休假统计数据");
    }

    /**
     * 获取年休假统计详细信息
     */
    @RequiresPermissions("human:yearHolidayStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(yearHolidayStatisticsService.selectYearHolidayStatisticsById(id));
    }

    /**
     * 新增年休假统计
     */
    @RequiresPermissions("human:yearHolidayStatistics:add")
    @Log(title = "年休假统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YearHolidayStatistics yearHolidayStatistics)
    {
        return toAjax(yearHolidayStatisticsService.insertYearHolidayStatistics(yearHolidayStatistics));
    }

    /**
     * 修改年休假统计
     */
    @RequiresPermissions("human:yearHolidayStatistics:edit")
    @Log(title = "年休假统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YearHolidayStatistics yearHolidayStatistics)
    {
        return toAjax(yearHolidayStatisticsService.updateYearHolidayStatistics(yearHolidayStatistics));
    }

    /**
     * 删除年休假统计
     */
    @RequiresPermissions("human:yearHolidayStatistics:remove")
    @Log(title = "年休假统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(yearHolidayStatisticsService.deleteYearHolidayStatisticsByIds(ids));
    }
}
