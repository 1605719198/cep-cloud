package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoBudget;
import com.jlkj.finance.ao.service.IFinanceAoBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 预算设置Controller
 *
 * @author jlkj
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/budget")
public class FinanceAoBudgetController extends BaseController
{
    @Autowired
    private IFinanceAoBudgetService financeAoBudgetService;

    /**
     * 查询预算设置列表
     */
    @RequiresPermissions("ao:budget:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoBudget financeAoBudget)
    {
        startPage();
        List<FinanceAoBudget> list = financeAoBudgetService.selectFinanceAoBudgetList(financeAoBudget);
        return getDataTable(list);
    }

    /**
     * 导出预算设置列表
     */
    @RequiresPermissions("ao:budget:export")
    @Log(title = "预算设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoBudget financeAoBudget)
    {
        List<FinanceAoBudget> list = financeAoBudgetService.selectFinanceAoBudgetList(financeAoBudget);
        ExcelUtil<FinanceAoBudget> util = new ExcelUtil<FinanceAoBudget>(FinanceAoBudget.class);
        util.exportExcel(response, list, "预算设置数据");
    }

    /**
     * 获取预算设置详细信息
     */
    @RequiresPermissions("ao:budget:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoBudgetService.selectFinanceAoBudgetById(id));
    }

    /**
     * 新增预算设置
     */
    @RequiresPermissions("ao:budget:add")
    @Log(title = "预算设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoBudget financeAoBudget)
    {
        return toAjax(financeAoBudgetService.insertFinanceAoBudget(financeAoBudget));
    }

    /**
     * 修改预算设置
     */
    @RequiresPermissions("ao:budget:edit")
    @Log(title = "预算设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoBudget financeAoBudget)
    {
        return toAjax(financeAoBudgetService.updateFinanceAoBudget(financeAoBudget));
    }

    /**
     * 删除预算设置
     */
    @RequiresPermissions("ao:budget:remove")
    @Log(title = "预算设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoBudgetService.deleteFinanceAoBudgetByIds(ids));
    }
}
