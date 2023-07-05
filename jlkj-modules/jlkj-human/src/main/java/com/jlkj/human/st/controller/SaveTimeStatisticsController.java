package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.SaveTimeStatistics;
import com.jlkj.human.st.service.ISaveTimeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 存班统计Controller
 * @Description:
 * @author 267383
 * @date 2023-07-05
 * @version:V1.0
 */
@RestController
@RequestMapping("/saveTimeStatistics")
public class SaveTimeStatisticsController extends BaseController
{
    @Autowired
    private ISaveTimeStatisticsService saveTimeStatisticsService;

    /**
     * 查询存班统计列表
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics
     * @return
     */
    @RequiresPermissions("human:saveTimeStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(SaveTimeStatistics saveTimeStatistics)
    {
        startPage();
        List<SaveTimeStatistics> list = saveTimeStatisticsService.selectSaveTimeStatisticsList(saveTimeStatistics);
        return getDataTable(list);
    }

    /**
     * 导出存班统计列表
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics
     * @return
     */
    @RequiresPermissions("human:saveTimeStatistics:export")
    @Log(title = "存班统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SaveTimeStatistics saveTimeStatistics)
    {
        List<SaveTimeStatistics> list = saveTimeStatisticsService.selectSaveTimeStatisticsList(saveTimeStatistics);
        ExcelUtil<SaveTimeStatistics> util = new ExcelUtil<SaveTimeStatistics>(SaveTimeStatistics.class);
        util.exportExcel(response, list, "存班统计数据");
    }

    /**
     * 获取存班统计详细信息
     * @author 267383
     * @date 2023-07-05
     * @param id
     * @return
     */
    @RequiresPermissions("human:saveTimeStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(saveTimeStatisticsService.selectSaveTimeStatisticsById(id));
    }

    /**
     * 新增存班统计
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics
     * @return
     */
    @RequiresPermissions("human:saveTimeStatistics:add")
    @Log(title = "存班统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SaveTimeStatistics saveTimeStatistics)
    {
        return toAjax(saveTimeStatisticsService.insertSaveTimeStatistics(saveTimeStatistics));
    }

    /**
     * 修改存班统计
     * @author 267383
     * @date 2023-07-05
     * @param saveTimeStatistics
     * @return
     */
    @RequiresPermissions("human:saveTimeStatistics:edit")
    @Log(title = "存班统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SaveTimeStatistics saveTimeStatistics)
    {
        return toAjax(saveTimeStatisticsService.updateSaveTimeStatistics(saveTimeStatistics));
    }

    /**
     * 删除存班统计
     * @author 267383
     * @date 2023-07-05
     * @param ids
     * @return
     */
    @RequiresPermissions("human:saveTimeStatistics:remove")
    @Log(title = "存班统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(saveTimeStatisticsService.deleteSaveTimeStatisticsByIds(ids));
    }
}
