package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoAccountCashier;
import com.jlkj.finance.ao.dto.FinanceAoAccountDto;
import com.jlkj.finance.ao.service.IFinanceAoAccountCashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出纳确认明细档Controller
 * @Description:
 * @author 265799
 * @date 2023-07-05
 * @version:V1.0
 */
@RestController
@RequestMapping("/cashierDetail")
public class FinanceAoAccountCashierController extends BaseController
{
    @Autowired
    private IFinanceAoAccountCashierService financeAoAccountCashierService;

    /**
     * 查询出纳确认明细档列表
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountCashier
     * @return
     */
    @RequiresPermissions("ao:cashierDetail:query")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoAccountCashier financeAoAccountCashier)
    {
        startPage();
        List<FinanceAoAccountCashier> list = financeAoAccountCashierService.selectFinanceAoAccountCashierList(financeAoAccountCashier);
        return getDataTable(list);
    }
    /**
     * 查询出纳确认明细档列表
     * @author 265799
     * @date 2023-07-05

     * @return
     */
    @RequiresPermissions("ao:cashierDetail:query")
    @GetMapping(value = "/listLoanADetailCashier")
    public AjaxResult listLoanADetailCashier(FinanceAoAccountCashier financeAoAccountCashier)
    {
        return success(financeAoAccountCashierService.selectListLoanADetailCashier(financeAoAccountCashier));
    }
    /**
     * 导出出纳确认明细档列表
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountCashier
     * @return
     */
    @RequiresPermissions("ao:cashierDetail:query")
    @Log(title = "出纳确认明细档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoAccountCashier financeAoAccountCashier)
    {
        List<FinanceAoAccountCashier> list = financeAoAccountCashierService.selectFinanceAoAccountCashierList(financeAoAccountCashier);
        ExcelUtil<FinanceAoAccountCashier> util = new ExcelUtil<FinanceAoAccountCashier>(FinanceAoAccountCashier.class);
        util.exportExcel(response, list, "出纳确认明细档数据");
    }

    /**
     * 获取出纳确认明细档详细信息
     * @author 265799
     * @date 2023-07-05

     * @return
     */
    @RequiresPermissions("ao:cashierDetail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoAccountCashierService.selectFinanceAoAccountCashierById(id));
    }

    /**
     * 新增出纳确认明细档
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountDto
     * @return
     */
    @RequiresPermissions("ao:cashierDetail:query")
    @Log(title = "出纳确认明细档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoAccountDto financeAoAccountDto)
    {
        return toAjax(financeAoAccountCashierService.insertFinanceAoAccountCashier(financeAoAccountDto));
    }

    /**
     * 修改出纳确认明细档
     * @author 265799
     * @date 2023-07-05
     * @param financeAoAccountDto
     * @return
     */
    @RequiresPermissions("ao:cashierDetail:query")
    @Log(title = "出纳确认明细档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoAccountDto financeAoAccountDto)
    {
        return toAjax(financeAoAccountCashierService.updateFinanceAoAccountCashier(financeAoAccountDto));
    }

    /**
     * 删除出纳确认明细档
     * @author 265799
     * @date 2023-07-05

     * @return
     */
    @RequiresPermissions("ao:cashierDetail:query")
    @Log(title = "出纳确认明细档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoAccountCashierService.deleteFinanceAoAccountCashierByIds(ids));
    }
}
