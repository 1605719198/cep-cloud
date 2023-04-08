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
import com.jlkj.finance.aa.domain.FinanceAaDictData;
import com.jlkj.finance.aa.service.IFinanceAaDictDataService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 系统管理-字典数据Controller
 *
 * @author 265799
 * @date 2023-04-03
 */
@RestController
@RequestMapping("/finance")
public class FinanceAaDictDataController extends BaseController
{
    @Autowired
    private IFinanceAaDictDataService financeAaDictDataService;

    /**
     * 查询系统管理-字典数据列表
     */
    @RequiresPermissions("finance:finance:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaDictData financeAaDictData)
    {
        startPage();
        List<FinanceAaDictData> list = financeAaDictDataService.selectFinanceAaDictDataList(financeAaDictData);
        return getDataTable(list);
    }

    /**
     * 导出系统管理-字典数据列表
     */
    @RequiresPermissions("finance:finance:export")
    @Log(title = "系统管理-字典数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaDictData financeAaDictData)
    {
        List<FinanceAaDictData> list = financeAaDictDataService.selectFinanceAaDictDataList(financeAaDictData);
        ExcelUtil<FinanceAaDictData> util = new ExcelUtil<FinanceAaDictData>(FinanceAaDictData.class);
        util.exportExcel(response, list, "系统管理-字典数据数据");
    }

    /**
     * 获取系统管理-字典数据详细信息
     */
    @RequiresPermissions("finance:finance:query")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable("dictCode") Long dictCode)
    {
        return success(financeAaDictDataService.selectFinanceAaDictDataByDictCode(dictCode));
    }

    /**
     * 新增系统管理-字典数据
     */
    @RequiresPermissions("finance:finance:add")
    @Log(title = "系统管理-字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaDictData financeAaDictData)
    {
        return toAjax(financeAaDictDataService.insertFinanceAaDictData(financeAaDictData));
    }

    /**
     * 修改系统管理-字典数据
     */
    @RequiresPermissions("finance:finance:edit")
    @Log(title = "系统管理-字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaDictData financeAaDictData)
    {
        return toAjax(financeAaDictDataService.updateFinanceAaDictData(financeAaDictData));
    }

    /**
     * 删除系统管理-字典数据
     */
    @RequiresPermissions("finance:finance:remove")
    @Log(title = "系统管理-字典数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        return toAjax(financeAaDictDataService.deleteFinanceAaDictDataByDictCodes(dictCodes));
    }
}
