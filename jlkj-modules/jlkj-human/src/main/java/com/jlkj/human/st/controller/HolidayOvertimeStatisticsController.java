package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.HolidayOvertimeStatistics;
import com.jlkj.human.st.service.IHolidayOvertimeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 倒班人员法定假日加班统计Controller
 * @Description:
 * @author 267383
 * @date 2023-07-10
 * @version:V1.0
 */
@RestController
@RequestMapping("/holidayOvertimeStatistics")
public class HolidayOvertimeStatisticsController extends BaseController
{
    @Autowired
    private IHolidayOvertimeStatisticsService holidayOvertimeStatisticsService;

    /**
     * 查询倒班人员法定假日加班统计列表
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics
     * @return
     */
    @RequiresPermissions("human:holidayOvertimeStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(HolidayOvertimeStatistics holidayOvertimeStatistics)
    {
        startPage();
        List<HolidayOvertimeStatistics> list = holidayOvertimeStatisticsService.selectHolidayOvertimeStatisticsList(holidayOvertimeStatistics);
        return getDataTable(list);
    }

    /**
     * 导出倒班人员法定假日加班统计列表
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics
     * @return
     */
    @RequiresPermissions("human:holidayOvertimeStatistics:export")
    @Log(title = "倒班人员法定假日加班统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HolidayOvertimeStatistics holidayOvertimeStatistics)
    {
        List<HolidayOvertimeStatistics> list = holidayOvertimeStatisticsService.selectHolidayOvertimeStatisticsList(holidayOvertimeStatistics);
        ExcelUtil<HolidayOvertimeStatistics> util = new ExcelUtil<HolidayOvertimeStatistics>(HolidayOvertimeStatistics.class);
        util.exportExcel(response, list, "倒班人员法定假日加班统计数据");
    }

    /**
     * 获取倒班人员法定假日加班统计详细信息
     * @author 267383
     * @date 2023-07-10
     * @param id
     * @return
     */
    @RequiresPermissions("human:holidayOvertimeStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(holidayOvertimeStatisticsService.selectHolidayOvertimeStatisticsById(id));
    }

    /**
     * 新增倒班人员法定假日加班统计
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics
     * @return
     */
    @RequiresPermissions("human:holidayOvertimeStatistics:add")
    @Log(title = "倒班人员法定假日加班统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HolidayOvertimeStatistics holidayOvertimeStatistics)
    {
        return toAjax(holidayOvertimeStatisticsService.insertHolidayOvertimeStatistics(holidayOvertimeStatistics));
    }

    /**
     * 修改倒班人员法定假日加班统计
     * @author 267383
     * @date 2023-07-10
     * @param holidayOvertimeStatistics
     * @return
     */
    @RequiresPermissions("human:holidayOvertimeStatistics:edit")
    @Log(title = "倒班人员法定假日加班统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HolidayOvertimeStatistics holidayOvertimeStatistics)
    {
        return toAjax(holidayOvertimeStatisticsService.updateHolidayOvertimeStatistics(holidayOvertimeStatistics));
    }

    /**
     * 删除倒班人员法定假日加班统计
     * @author 267383
     * @date 2023-07-10
     * @param ids
     * @return
     */
    @RequiresPermissions("human:holidayOvertimeStatistics:remove")
    @Log(title = "倒班人员法定假日加班统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(holidayOvertimeStatisticsService.deleteHolidayOvertimeStatisticsByIds(ids));
    }
}
