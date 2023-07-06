package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.ComptimeStatistics;
import com.jlkj.human.st.service.IComptimeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 补休统计Controller
 * @Description:
 * @author 267383
 * @date 2023-07-04
 * @version:V1.0
 */
@RestController
@RequestMapping("/comptimeStatistics")
public class ComptimeStatisticsController extends BaseController
{
    @Autowired
    private IComptimeStatisticsService comptimeStatisticsService;

    /**
     * 查询补休统计列表
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics
     * @return
     */
    @RequiresPermissions("human:comptimeStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(ComptimeStatistics comptimeStatistics)
    {
        startPage();
        List<ComptimeStatistics> list = comptimeStatisticsService.selectComptimeStatisticsList(comptimeStatistics);
        return getDataTable(list);
    }

    /**
     * 导出补休统计列表
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics
     * @return
     */
    @RequiresPermissions("human:comptimeStatistics:export")
    @Log(title = "补休统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ComptimeStatistics comptimeStatistics)
    {
        List<ComptimeStatistics> list = comptimeStatisticsService.selectComptimeStatisticsList(comptimeStatistics);
        ExcelUtil<ComptimeStatistics> util = new ExcelUtil<ComptimeStatistics>(ComptimeStatistics.class);
        util.exportExcel(response, list, "补休统计数据");
    }

    /**
     * 获取补休统计详细信息
     * @author 267383
     * @date 2023-07-04
     * @param id
     * @return
     */
    @RequiresPermissions("human:comptimeStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(comptimeStatisticsService.selectComptimeStatisticsById(id));
    }

    /**
     * 新增补休统计
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics
     * @return
     */
    @RequiresPermissions("human:comptimeStatistics:add")
    @Log(title = "补休统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ComptimeStatistics comptimeStatistics)
    {
        return toAjax(comptimeStatisticsService.insertComptimeStatistics(comptimeStatistics));
    }

    /**
     * 修改补休统计
     * @author 267383
     * @date 2023-07-04
     * @param comptimeStatistics
     * @return
     */
    @RequiresPermissions("human:comptimeStatistics:edit")
    @Log(title = "补休统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ComptimeStatistics comptimeStatistics)
    {
        return toAjax(comptimeStatisticsService.updateComptimeStatistics(comptimeStatistics));
    }

    /**
     * 删除补休统计
     * @author 267383
     * @date 2023-07-04
     * @param ids
     * @return
     */
    @RequiresPermissions("human:comptimeStatistics:remove")
    @Log(title = "补休统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(comptimeStatisticsService.deleteComptimeStatisticsByIds(ids));
    }
}
