package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtDictType;
import com.jlkj.finance.ft.service.IFinanceFtDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 选单设定Controller
 *
 * @author SunXuTong
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/dictType")
public class FinanceFtDictTypeController extends BaseController
{
    @Autowired
    private IFinanceFtDictTypeService financeFtDictTypeService;

    /**
     * 查询选单设定列表
     */
    @RequiresPermissions("finance:dictType:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtDictType financeFtDictType)
    {
        startPage();
        List<FinanceFtDictType> list = financeFtDictTypeService.selectFinanceFtDictTypeList(financeFtDictType);
        return getDataTable(list);
    }

    /**
     * 导出选单设定列表
     */
    @RequiresPermissions("finance:dictType:export")
    @Log(title = "选单设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtDictType financeFtDictType)
    {
        List<FinanceFtDictType> list = financeFtDictTypeService.selectFinanceFtDictTypeList(financeFtDictType);
        ExcelUtil<FinanceFtDictType> util = new ExcelUtil<FinanceFtDictType>(FinanceFtDictType.class);
        util.exportExcel(response, list, "选单设定数据");
    }

    /**
     * 获取选单设定详细信息
     */
    @RequiresPermissions("finance:dictType:query")
    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable("dictId") String dictId)
    {
        return success(financeFtDictTypeService.selectFinanceFtDictTypeByDictId(dictId));
    }

    /**
     * 新增选单设定
     */
    @RequiresPermissions("finance:dictType:add")
    @Log(title = "选单设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtDictType financeFtDictType)
    {
        return toAjax(financeFtDictTypeService.insertFinanceFtDictType(financeFtDictType));
    }

    /**
     * 修改选单设定
     */
    @RequiresPermissions("finance:dictType:edit")
    @Log(title = "选单设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtDictType financeFtDictType)
    {
        return toAjax(financeFtDictTypeService.updateFinanceFtDictType(financeFtDictType));
    }

    /**
     * 删除选单设定
     */
    @RequiresPermissions("finance:dictType:remove")
    @Log(title = "选单设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictIds}")
    public AjaxResult remove(@PathVariable String[] dictIds)
    {
        return toAjax(financeFtDictTypeService.deleteFinanceFtDictTypeByDictIds(dictIds));
    }
}
