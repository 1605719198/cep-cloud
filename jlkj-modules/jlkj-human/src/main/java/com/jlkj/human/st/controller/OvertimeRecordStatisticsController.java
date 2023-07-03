package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.OvertimeRecordStatistics;
import com.jlkj.human.st.service.IOvertimeRecordStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 加班统计Controller
 * @Description:
 * @author 267383
 * @date 2023-07-03
 * @version:V1.0
 */
@RestController
@RequestMapping("/overtimeRecordStatistics")
public class OvertimeRecordStatisticsController extends BaseController
{
    @Autowired
    private IOvertimeRecordStatisticsService overtimeRecordStatisticsService;

    /**
     * 查询加班统计列表
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics
     * @return
     */
    @RequiresPermissions("human:overtimeRecordStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(OvertimeRecordStatistics overtimeRecordStatistics)
    {
        startPage();
        List<OvertimeRecordStatistics> list = overtimeRecordStatisticsService.selectOvertimeRecordStatisticsList(overtimeRecordStatistics);
        return getDataTable(list);
    }

    /**
     * 导出加班统计列表
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics
     * @return
     */
    @RequiresPermissions("human:overtimeRecordStatistics:export")
    @Log(title = "加班统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OvertimeRecordStatistics overtimeRecordStatistics)
    {
        List<OvertimeRecordStatistics> list = overtimeRecordStatisticsService.selectOvertimeRecordStatisticsList(overtimeRecordStatistics);
        ExcelUtil<OvertimeRecordStatistics> util = new ExcelUtil<OvertimeRecordStatistics>(OvertimeRecordStatistics.class);
        util.exportExcel(response, list, "加班统计数据");
    }

    /**
     * 获取加班统计详细信息
     * @author 267383
     * @date 2023-07-03
     * @param id
     * @return
     */
    @RequiresPermissions("human:overtimeRecordStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(overtimeRecordStatisticsService.selectOvertimeRecordStatisticsById(id));
    }

    /**
     * 新增加班统计
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics
     * @return
     */
    @RequiresPermissions("human:overtimeRecordStatistics:add")
    @Log(title = "加班统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OvertimeRecordStatistics overtimeRecordStatistics)
    {
        return toAjax(overtimeRecordStatisticsService.insertOvertimeRecordStatistics(overtimeRecordStatistics));
    }

    /**
     * 修改加班统计
     * @author 267383
     * @date 2023-07-03
     * @param overtimeRecordStatistics
     * @return
     */
    @RequiresPermissions("human:overtimeRecordStatistics:edit")
    @Log(title = "加班统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OvertimeRecordStatistics overtimeRecordStatistics)
    {
        return toAjax(overtimeRecordStatisticsService.updateOvertimeRecordStatistics(overtimeRecordStatistics));
    }

    /**
     * 删除加班统计
     * @author 267383
     * @date 2023-07-03
     * @param ids
     * @return
     */
    @RequiresPermissions("human:overtimeRecordStatistics:remove")
    @Log(title = "加班统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(overtimeRecordStatisticsService.deleteOvertimeRecordStatisticsByIds(ids));
    }
}
