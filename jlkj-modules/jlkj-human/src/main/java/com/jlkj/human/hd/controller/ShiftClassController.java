package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.ShiftClass;
import com.jlkj.human.hd.service.IShiftClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 班别数据Controller
 *
 * @author 266861
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/shiftClass")
public class ShiftClassController extends BaseController
{
    @Autowired
    private IShiftClassService shiftClassService;

    /**
     * 查询班别数据列表
     */
    @RequiresPermissions("human:shiftMode:list")
    @GetMapping("/list")
    public TableDataInfo list(ShiftClass shiftClass)
    {
        startPage();
        List<ShiftClass> list = shiftClassService.selectShiftClassList(shiftClass);
        return getDataTable(list);
    }

    /**
     * 导出班别数据列表
     */
    @RequiresPermissions("human:shiftMode:export")
    @Log(title = "班别数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShiftClass shiftClass)
    {
        List<ShiftClass> list = shiftClassService.selectShiftClassList(shiftClass);
        ExcelUtil<ShiftClass> util = new ExcelUtil<ShiftClass>(ShiftClass.class);
        util.exportExcel(response, list, "班别数据数据");
    }

    /**
     * 获取班别数据详细信息
     */
    @RequiresPermissions("human:shiftMode:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(shiftClassService.selectShiftClassById(id));
    }

    /**
     * 新增班别数据
     */
    @RequiresPermissions("human:shiftMode:add")
    @Log(title = "班别数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShiftClass shiftClass)
    {
        return toAjax(shiftClassService.insertShiftClass(shiftClass));
    }

    /**
     * 修改班别数据
     */
    @RequiresPermissions("human:shiftMode:edit")
    @Log(title = "班别数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShiftClass shiftClass)
    {
        return toAjax(shiftClassService.updateShiftClass(shiftClass));
    }

    /**
     * 删除班别数据
     */
    @RequiresPermissions("human:shiftMode:remove")
    @Log(title = "班别数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(shiftClassService.deleteShiftClassByIds(ids));
    }
}
