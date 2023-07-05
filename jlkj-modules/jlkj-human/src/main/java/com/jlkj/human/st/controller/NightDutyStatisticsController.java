package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.NightDutyStatistics;
import com.jlkj.human.st.service.INightDutyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 夜班统计Controller
 * @Description:
 * @author 267383
 * @date 2023-07-05
 * @version:V1.0
 */
@RestController
@RequestMapping("/nightDutyStatistics")
public class NightDutyStatisticsController extends BaseController
{
    @Autowired
    private INightDutyStatisticsService nightDutyStatisticsService;

    /**
     * 查询夜班统计列表
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics
     * @return
     */
    @RequiresPermissions("human:nightDutyStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(NightDutyStatistics nightDutyStatistics)
    {
        startPage();
        List<NightDutyStatistics> list = nightDutyStatisticsService.selectNightDutyStatisticsList(nightDutyStatistics);
        return getDataTable(list);
    }

    /**
     * 导出夜班统计列表
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics
     * @return
     */
    @RequiresPermissions("human:nightDutyStatistics:export")
    @Log(title = "夜班统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NightDutyStatistics nightDutyStatistics)
    {
        List<NightDutyStatistics> list = nightDutyStatisticsService.selectNightDutyStatisticsList(nightDutyStatistics);
        ExcelUtil<NightDutyStatistics> util = new ExcelUtil<NightDutyStatistics>(NightDutyStatistics.class);
        util.exportExcel(response, list, "夜班统计数据");
    }

    /**
     * 获取夜班统计详细信息
     * @author 267383
     * @date 2023-07-05
     * @param id
     * @return
     */
    @RequiresPermissions("human:nightDutyStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(nightDutyStatisticsService.selectNightDutyStatisticsById(id));
    }

    /**
     * 新增夜班统计
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics
     * @return
     */
    @RequiresPermissions("human:nightDutyStatistics:add")
    @Log(title = "夜班统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NightDutyStatistics nightDutyStatistics)
    {
        return toAjax(nightDutyStatisticsService.insertNightDutyStatistics(nightDutyStatistics));
    }

    /**
     * 修改夜班统计
     * @author 267383
     * @date 2023-07-05
     * @param nightDutyStatistics
     * @return
     */
    @RequiresPermissions("human:nightDutyStatistics:edit")
    @Log(title = "夜班统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NightDutyStatistics nightDutyStatistics)
    {
        return toAjax(nightDutyStatisticsService.updateNightDutyStatistics(nightDutyStatistics));
    }

    /**
     * 删除夜班统计
     * @author 267383
     * @date 2023-07-05
     * @param ids
     * @return
     */
    @RequiresPermissions("human:nightDutyStatistics:remove")
    @Log(title = "夜班统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(nightDutyStatisticsService.deleteNightDutyStatisticsByIds(ids));
    }
}
