package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.AttendanceAbnormalStatistics;
import com.jlkj.human.st.service.IAttendanceAbnormalStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出勤异常统计Controller
 *
 * @author 267383
 * @date 2023-06-18
 */
@RestController
@RequestMapping("/attendanceAbnormalStatistics")
public class AttendanceAbnormalStatisticsController extends BaseController
{
    @Autowired
    private IAttendanceAbnormalStatisticsService attendanceAbnormalStatisticsService;

    /**
     * 查询出勤异常统计列表
     */
    @RequiresPermissions("human:attendanceAbnormalStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        startPage();
        List<AttendanceAbnormalStatistics> list = attendanceAbnormalStatisticsService.selectAttendanceAbnormalStatisticsList(attendanceAbnormalStatistics);
        return getDataTable(list);
    }

    /**
     * 导出出勤异常统计列表
     */
    @RequiresPermissions("human:attendanceAbnormalStatistics:export")
    @Log(title = "出勤异常统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        List<AttendanceAbnormalStatistics> list = attendanceAbnormalStatisticsService.selectAttendanceAbnormalStatisticsList(attendanceAbnormalStatistics);
        ExcelUtil<AttendanceAbnormalStatistics> util = new ExcelUtil<AttendanceAbnormalStatistics>(AttendanceAbnormalStatistics.class);
        util.exportExcel(response, list, "出勤异常统计数据");
    }

    /**
     * 获取出勤异常统计详细信息
     */
    @RequiresPermissions("human:attendanceAbnormalStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(attendanceAbnormalStatisticsService.selectAttendanceAbnormalStatisticsById(id));
    }

    /**
     * 新增出勤异常统计
     */
    @RequiresPermissions("human:attendanceAbnormalStatistics:add")
    @Log(title = "出勤异常统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        return toAjax(attendanceAbnormalStatisticsService.insertAttendanceAbnormalStatistics(attendanceAbnormalStatistics));
    }

    /**
     * 修改出勤异常统计
     */
    @RequiresPermissions("human:attendanceAbnormalStatistics:edit")
    @Log(title = "出勤异常统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttendanceAbnormalStatistics attendanceAbnormalStatistics)
    {
        return toAjax(attendanceAbnormalStatisticsService.updateAttendanceAbnormalStatistics(attendanceAbnormalStatistics));
    }

    /**
     * 删除出勤异常统计
     */
    @RequiresPermissions("human:attendanceAbnormalStatistics:remove")
    @Log(title = "出勤异常统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(attendanceAbnormalStatisticsService.deleteAttendanceAbnormalStatisticsByIds(ids));
    }
}
