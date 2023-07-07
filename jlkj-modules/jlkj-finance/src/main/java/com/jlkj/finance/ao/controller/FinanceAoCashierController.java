package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoCashier;
import com.jlkj.finance.ao.service.IFinanceAoCashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出纳确认主档Controller
 * @Description:
 * @author 265799
 * @date 2023-07-05
 * @version:V1.0
 */
@RestController
@RequestMapping("/cashier")
public class FinanceAoCashierController extends BaseController
{
    @Autowired
    private IFinanceAoCashierService financeAoCashierService;

    /**
     * 查询出纳确认主档列表
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier
     * @return
     */
    @RequiresPermissions("ao:cashier:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoCashier financeAoCashier)
    {
        startPage();
        List<FinanceAoCashier> list = financeAoCashierService.selectFinanceAoCashierList(financeAoCashier);
        return getDataTable(list);
    }

    /**
     * 导出出纳确认主档列表
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier
     * @return
     */
    @RequiresPermissions("ao:cashier:export")
    @Log(title = "出纳确认主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoCashier financeAoCashier)
    {
        List<FinanceAoCashier> list = financeAoCashierService.selectFinanceAoCashierList(financeAoCashier);
        ExcelUtil<FinanceAoCashier> util = new ExcelUtil<FinanceAoCashier>(FinanceAoCashier.class);
        util.exportExcel(response, list, "出纳确认主档数据");
    }

    /**
     * 获取出纳确认主档详细信息
     * @author 265799
     * @date 2023-07-05

     * @return
     */
    @RequiresPermissions("ao:cashier:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoCashierService.selectFinanceAoCashierById(id));
    }

    /**
     * 新增出纳确认主档
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier
     * @return
     */
    @RequiresPermissions("ao:cashier:add")
    @Log(title = "出纳确认主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoCashier financeAoCashier)
    {
        return toAjax(financeAoCashierService.insertFinanceAoCashier(financeAoCashier));
    }

    /**
     * 修改出纳确认主档
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier
     * @return
     */
    @RequiresPermissions("ao:cashier:edit")
    @Log(title = "出纳确认主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoCashier financeAoCashier)
    {
        return toAjax(financeAoCashierService.updateFinanceAoCashier(financeAoCashier));
    }
    /**
     * 还款修改出纳确认主档
     * @author 265799
     * @date 2023-07-05
     * @param financeAoCashier
     * @return
     */
    @RequiresPermissions("ao:cashier:edit")
    @Log(title = "还款修改出纳确认主档", businessType = BusinessType.UPDATE)
    @PutMapping("/editCashierRepayment")
    public AjaxResult editCashierRepayment(@RequestBody FinanceAoCashier financeAoCashier)
    {
        return toAjax(financeAoCashierService.updateFinanceAoCashierRepayment(financeAoCashier));
    }

    /**
     * 删除出纳确认主档
     * @author 265799
     * @date 2023-07-05

     * @return
     */
    @RequiresPermissions("ao:cashier:remove")
    @Log(title = "出纳确认主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoCashierService.deleteFinanceAoCashierByIds(ids));
    }
}
