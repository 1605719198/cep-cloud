package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.pa.domain.PerformanceLevel;
import com.jlkj.human.pa.service.IPerformanceLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 绩效系数设定Controller
 *
 * @author 116519
 * @date 2023-05-15
 */
@RestController
@RequestMapping("/performanceLevel")
public class PerformanceLevelController extends BaseController
{
    @Autowired
    private IPerformanceLevelService performanceLevelService;

    /**
     * 查询绩效系数设定列表
     */
    @RequiresPermissions("human:performanceLevel:list")
    @GetMapping("/list")
    public TableDataInfo list(PerformanceLevel performanceLevel)
    {
        startPage();
        List<PerformanceLevel> list = performanceLevelService.selectPerformanceLevelList(performanceLevel);
        return getDataTable(list);
    }

    /**
     * 导出绩效系数设定列表
     */
    @RequiresPermissions("human:performanceLevel:export")
    @Log(title = "绩效系数设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerformanceLevel performanceLevel)
    {
        List<PerformanceLevel> list = performanceLevelService.selectPerformanceLevelList(performanceLevel);
        ExcelUtil<PerformanceLevel> util = new ExcelUtil<PerformanceLevel>(PerformanceLevel.class);
        util.exportExcel(response, list, "绩效系数设定数据");
    }

    /**
     * 获取绩效系数设定详细信息
     */
    @RequiresPermissions("human:performanceLevel:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(performanceLevelService.selectPerformanceLevelByUuid(uuid));
    }

    /**
     * 新增绩效系数设定
     */
    @RequiresPermissions("human:performanceLevel:add")
    @Log(title = "绩效系数设定", businessType = BusinessType.INSERT)
    @PostMapping

    public int add(@RequestBody  PerformanceLevel performanceLevel)
    {
        List<PerformanceLevel> performanceLevelList = performanceLevel.getLevelList();
        return performanceLevelService.insertPerformanceLevel(performanceLevelList,performanceLevel);
    }



    /**
     * 删除绩效系数设定
     */
    @RequiresPermissions("human:performanceLevel:remove")
    @Log(title = "绩效系数设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(performanceLevelService.deletePerformanceLevelByUuids(uuids));
    }
}
