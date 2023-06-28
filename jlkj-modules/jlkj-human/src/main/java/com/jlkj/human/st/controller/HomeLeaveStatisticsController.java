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
import com.jlkj.human.st.domain.HomeLeaveStatistics;
import com.jlkj.human.st.service.IHomeLeaveStatisticsService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 探亲假统计Controller
 *
 * @author 267383
 * @date 2023-06-25
 */
@RestController
@RequestMapping("/homeLeaveStatistics")
public class HomeLeaveStatisticsController extends BaseController
{
    @Autowired
    private IHomeLeaveStatisticsService homeLeaveStatisticsService;

    /**
     * 查询探亲假统计列表
     */
    @RequiresPermissions("human:homeLeaveStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(HomeLeaveStatistics homeLeaveStatistics)
    {
        startPage();
        List<HomeLeaveStatistics> list = homeLeaveStatisticsService.selectHomeLeaveStatisticsList(homeLeaveStatistics);
        return getDataTable(list);
    }

    /**
     * 导出探亲假统计列表
     */
    @RequiresPermissions("human:homeLeaveStatistics:export")
    @Log(title = "探亲假统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomeLeaveStatistics homeLeaveStatistics)
    {
        List<HomeLeaveStatistics> list = homeLeaveStatisticsService.selectHomeLeaveStatisticsList(homeLeaveStatistics);
        ExcelUtil<HomeLeaveStatistics> util = new ExcelUtil<HomeLeaveStatistics>(HomeLeaveStatistics.class);
        util.exportExcel(response, list, "探亲假统计数据");
    }

    /**
     * 获取探亲假统计详细信息
     */
    @RequiresPermissions("human:homeLeaveStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(homeLeaveStatisticsService.selectHomeLeaveStatisticsById(id));
    }

    /**
     * 新增探亲假统计
     */
    @RequiresPermissions("human:homeLeaveStatistics:add")
    @Log(title = "探亲假统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomeLeaveStatistics homeLeaveStatistics)
    {
        return toAjax(homeLeaveStatisticsService.insertHomeLeaveStatistics(homeLeaveStatistics));
    }

    /**
     * 修改探亲假统计
     */
    @RequiresPermissions("human:homeLeaveStatistics:edit")
    @Log(title = "探亲假统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomeLeaveStatistics homeLeaveStatistics)
    {
        return toAjax(homeLeaveStatisticsService.updateHomeLeaveStatistics(homeLeaveStatistics));
    }

    /**
     * 删除探亲假统计
     */
    @RequiresPermissions("human:homeLeaveStatistics:remove")
    @Log(title = "探亲假统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(homeLeaveStatisticsService.deleteHomeLeaveStatisticsByIds(ids));
    }
}
