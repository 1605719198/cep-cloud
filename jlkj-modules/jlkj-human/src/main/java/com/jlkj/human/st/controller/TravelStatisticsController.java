package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.TravelStatistics;
import com.jlkj.human.st.service.ITravelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出差统计Controller
 *
 * @author 267383
 * @date 2023-06-24
 */
@RestController
@RequestMapping("/travelStatistics")
public class TravelStatisticsController extends BaseController
{
    @Autowired
    private ITravelStatisticsService travelStatisticsService;

    /**
     * 查询出差统计列表
     */
    @RequiresPermissions("human:travelStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(TravelStatistics travelStatistics)
    {
        startPage();
        List<TravelStatistics> list = travelStatisticsService.selectTravelStatisticsList(travelStatistics);
        return getDataTable(list);
    }

    /**
     * 导出出差统计列表
     */
    @RequiresPermissions("human:travelStatistics:export")
    @Log(title = "出差统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TravelStatistics travelStatistics)
    {
        List<TravelStatistics> list = travelStatisticsService.selectTravelStatisticsList(travelStatistics);
        ExcelUtil<TravelStatistics> util = new ExcelUtil<TravelStatistics>(TravelStatistics.class);
        util.exportExcel(response, list, "出差统计数据");
    }

    /**
     * 获取出差统计详细信息
     */
    @RequiresPermissions("human:travelStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(travelStatisticsService.selectTravelStatisticsById(id));
    }

    /**
     * 新增出差统计
     */
    @RequiresPermissions("human:travelStatistics:add")
    @Log(title = "出差统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TravelStatistics travelStatistics)
    {
        return toAjax(travelStatisticsService.insertTravelStatistics(travelStatistics));
    }

    /**
     * 修改出差统计
     */
    @RequiresPermissions("human:travelStatistics:edit")
    @Log(title = "出差统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TravelStatistics travelStatistics)
    {
        return toAjax(travelStatisticsService.updateTravelStatistics(travelStatistics));
    }

    /**
     * 删除出差统计
     */
    @RequiresPermissions("human:travelStatistics:remove")
    @Log(title = "出差统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(travelStatisticsService.deleteTravelStatisticsByIds(ids));
    }
}
