package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.TravelApplication;
import com.jlkj.human.hd.service.ITravelApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出差申请Controller
 *
 * @author jlkj
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/travelapplication")
public class TravelApplicationController extends BaseController
{
    @Autowired
    private ITravelApplicationService travelApplicationService;

    /**
     * 查询出差申请列表
     */
    @RequiresPermissions("human:travelapplication:list")
    @GetMapping("/list")
    public TableDataInfo list(TravelApplication travelApplication)
    {
        startPage();
        List<TravelApplication> list = travelApplicationService.selectTravelApplicationList(travelApplication);
        return getDataTable(list);
    }

    /**
     * 导出出差申请列表
     */
    @RequiresPermissions("human:travelapplication:export")
    @Log(title = "出差申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TravelApplication travelApplication)
    {
        List<TravelApplication> list = travelApplicationService.selectTravelApplicationList(travelApplication);
        ExcelUtil<TravelApplication> util = new ExcelUtil<TravelApplication>(TravelApplication.class);
        util.exportExcel(response, list, "出差申请数据");
    }

    /**
     * 获取出差申请详细信息
     */
    @RequiresPermissions("human:travelapplication:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(travelApplicationService.selectTravelApplicationById(id));
    }

    /**
     * 新增出差申请
     */
    @RequiresPermissions("human:travelapplication:add")
    @Log(title = "出差申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TravelApplication travelApplication)
    {
        return toAjax(travelApplicationService.insertTravelApplication(travelApplication));
    }

    /**
     * 修改出差申请
     */
    @RequiresPermissions("human:travelapplication:edit")
    @Log(title = "出差申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TravelApplication travelApplication)
    {
        return toAjax(travelApplicationService.updateTravelApplication(travelApplication));
    }

    /**
     * 删除出差申请
     */
    @RequiresPermissions("human:travelapplication:remove")
    @Log(title = "出差申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(travelApplicationService.deleteTravelApplicationByIds(ids));
    }
}
