package com.jlkj.finance.aa.controller;

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
import com.jlkj.finance.aa.domain.FinanceAaDictType;
import com.jlkj.finance.aa.service.IFinanceAaDictTypeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 系统管理-字典类型Controller
 *
 * @author 265799
 * @date 2023-04-03
 */
@RestController
@RequestMapping("/type")
public class FinanceAaDictTypeController extends BaseController
{
    @Autowired
    private IFinanceAaDictTypeService financeAaDictTypeService;

    /**
     * 查询系统管理-字典类型列表
     */
    @RequiresPermissions("finance:type:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaDictType financeAaDictType)
    {
        startPage();
        List<FinanceAaDictType> list = financeAaDictTypeService.selectFinanceAaDictTypeList(financeAaDictType);
        return getDataTable(list);
    }

    /**
     * 导出系统管理-字典类型列表
     */
    @RequiresPermissions("finance:type:export")
    @Log(title = "系统管理-字典类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaDictType financeAaDictType)
    {
        List<FinanceAaDictType> list = financeAaDictTypeService.selectFinanceAaDictTypeList(financeAaDictType);
        ExcelUtil<FinanceAaDictType> util = new ExcelUtil<FinanceAaDictType>(FinanceAaDictType.class);
        util.exportExcel(response, list, "系统管理-字典类型数据");
    }

    /**
     * 获取系统管理-字典类型详细信息
     */
    @RequiresPermissions("finance:type:query")
    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable("dictId") Long dictId)
    {
        return success(financeAaDictTypeService.selectFinanceAaDictTypeByDictId(dictId));
    }

    /**
     * 新增系统管理-字典类型
     */
    @RequiresPermissions("finance:type:add")
    @Log(title = "系统管理-字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaDictType financeAaDictType)
    {
        return toAjax(financeAaDictTypeService.insertFinanceAaDictType(financeAaDictType));
    }

    /**
     * 修改系统管理-字典类型
     */
    @RequiresPermissions("finance:type:edit")
    @Log(title = "系统管理-字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaDictType financeAaDictType)
    {
        return toAjax(financeAaDictTypeService.updateFinanceAaDictType(financeAaDictType));
    }

    /**
     * 删除系统管理-字典类型
     */
    @RequiresPermissions("finance:type:remove")
    @Log(title = "系统管理-字典类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictIds}")
    public AjaxResult remove(@PathVariable Long[] dictIds)
    {
        return toAjax(financeAaDictTypeService.deleteFinanceAaDictTypeByDictIds(dictIds));
    }
}
