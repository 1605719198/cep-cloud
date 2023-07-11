package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.SchedulingStatistics;
import com.jlkj.human.st.service.ISchedulingStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 年度排班统计Controller
 * @Description:
 * @author 267383
 * @date 2023-07-11
 * @version:V1.0
 */
@RestController
@RequestMapping("/schedulingStatistics")
public class SchedulingStatisticsController extends BaseController
{
    @Autowired
    private ISchedulingStatisticsService schedulingStatisticsService;

    /**
     * 查询年度排班统计列表
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics
     * @return
     */
    @RequiresPermissions("human:schedulingStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(SchedulingStatistics schedulingStatistics)
    {
        startPage();
        List<SchedulingStatistics> list = schedulingStatisticsService.selectSchedulingStatisticsList(schedulingStatistics);
        return getDataTable(list);
    }

    /**
     * 导出年度排班统计列表
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics
     * @return
     */
    @RequiresPermissions("human:schedulingStatistics:export")
    @Log(title = "年度排班统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SchedulingStatistics schedulingStatistics)
    {
        List<SchedulingStatistics> list = schedulingStatisticsService.selectSchedulingStatisticsList(schedulingStatistics);
        ExcelUtil<SchedulingStatistics> util = new ExcelUtil<SchedulingStatistics>(SchedulingStatistics.class);
        util.exportExcel(response, list, "年度排班统计数据");
    }

    /**
     * 获取年度排班统计详细信息
     * @author 267383
     * @date 2023-07-11
     * @param id
     * @return
     */
    @RequiresPermissions("human:schedulingStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(schedulingStatisticsService.selectSchedulingStatisticsById(id));
    }

    /**
     * 新增年度排班统计
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics
     * @return
     */
    @RequiresPermissions("human:schedulingStatistics:add")
    @Log(title = "年度排班统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SchedulingStatistics schedulingStatistics)
    {
        return toAjax(schedulingStatisticsService.insertSchedulingStatistics(schedulingStatistics));
    }

    /**
     * 修改年度排班统计
     * @author 267383
     * @date 2023-07-11
     * @param schedulingStatistics
     * @return
     */
    @RequiresPermissions("human:schedulingStatistics:edit")
    @Log(title = "年度排班统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SchedulingStatistics schedulingStatistics)
    {
        return toAjax(schedulingStatisticsService.updateSchedulingStatistics(schedulingStatistics));
    }

    /**
     * 删除年度排班统计
     * @author 267383
     * @date 2023-07-11
     * @param ids
     * @return
     */
    @RequiresPermissions("human:schedulingStatistics:remove")
    @Log(title = "年度排班统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(schedulingStatisticsService.deleteSchedulingStatisticsByIds(ids));
    }
}
