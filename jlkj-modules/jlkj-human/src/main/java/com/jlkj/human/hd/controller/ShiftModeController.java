package com.jlkj.human.hd.controller;

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
import com.jlkj.human.hd.domain.ShiftMode;
import com.jlkj.human.hd.service.IShiftModeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 轮班方式数据Controller
 *
 * @author 266861
 * @date 2023-04-04
 */
@RestController
@RequestMapping("/shiftMode")
public class ShiftModeController extends BaseController
{
    @Autowired
    private IShiftModeService shiftModeService;

    /**
     * 查询轮班方式数据列表
     */
    @RequiresPermissions("human:shiftMode:list")
    @GetMapping("/list")
    public TableDataInfo list(ShiftMode shiftMode)
    {
        startPage();
        List<ShiftMode> list = shiftModeService.selectShiftModeList(shiftMode);
        return getDataTable(list);
    }

    /**
     * 查询轮班方式数据列表(所有)
     */
    @RequiresPermissions("human:shiftMode:list")
    @GetMapping("/listAll")
    public TableDataInfo listAll(ShiftMode shiftMode)
    {
        List<ShiftMode> list = shiftModeService.selectShiftModeListAll(shiftMode);
        return getDataTable(list);
    }

    /**
     * 导出轮班方式数据列表
     */
    @RequiresPermissions("human:shiftMode:export")
    @Log(title = "轮班方式数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShiftMode shiftMode)
    {
        List<ShiftMode> list = shiftModeService.selectShiftModeList(shiftMode);
        ExcelUtil<ShiftMode> util = new ExcelUtil<ShiftMode>(ShiftMode.class);
        util.exportExcel(response, list, "轮班方式数据数据");
    }

    /**
     * 获取轮班方式数据详细信息
     */
    @RequiresPermissions("human:shiftMode:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(shiftModeService.selectShiftModeById(id));
    }

    /**
     * 新增轮班方式数据
     */
    @RequiresPermissions("human:shiftMode:add")
    @Log(title = "轮班方式数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShiftMode shiftMode)
    {
        return toAjax(shiftModeService.insertShiftMode(shiftMode));
    }

    /**
     * 修改轮班方式数据
     */
    @RequiresPermissions("human:shiftMode:edit")
    @Log(title = "轮班方式数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShiftMode shiftMode)
    {
        return toAjax(shiftModeService.updateShiftMode(shiftMode));
    }

    /**
     * 删除轮班方式数据
     */
    @RequiresPermissions("human:shiftMode:remove")
    @Log(title = "轮班方式数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(shiftModeService.deleteShiftModeByIds(ids));
    }
}
