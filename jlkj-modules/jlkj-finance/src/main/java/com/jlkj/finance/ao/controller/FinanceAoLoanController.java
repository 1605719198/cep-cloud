package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoLoan;
import com.jlkj.finance.ao.service.IFinanceAoLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 借支申请主档Controller
 *
 * @author 116514
 * @date 2023-06-02
 */
@RestController
@RequestMapping("/loanApply")
public class FinanceAoLoanController extends BaseController
{
    @Autowired
    private IFinanceAoLoanService financeAoLoanService;

    /**
     * 查询借支申请主档列表
     */
    @RequiresPermissions("finance:loanApply:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoLoan financeAoLoan)
    {
        startPage();
        List<FinanceAoLoan> list = financeAoLoanService.selectAoLoanTotalList(financeAoLoan);
        return getDataTable(list);
    }

    /**
     * 获取借支申请主档详细信息
     */
    @RequiresPermissions("finance:loanApply:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoLoanService.selectFinanceAoLoanById(id));
    }
    /**
     * 获取借支申请主档详细信息
     */
    @RequiresPermissions("finance:loanApply:query")
    @GetMapping(value = "/loanApplyFrom/{id}")
    public AjaxResult getLoanApplyFrom(@PathVariable("id") String id)
    {
        return success(financeAoLoanService.selectFinanceAoLoanByFromId(id));
    }

    /**
     * 导出借支申请主档列表
     */
    @RequiresPermissions("finance:loanApply:export")
    @Log(title = "借支申请主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoLoan financeAoLoan)
    {
        List<FinanceAoLoan> list = financeAoLoanService.selectFinanceAoLoanList(financeAoLoan);
        ExcelUtil<FinanceAoLoan> util = new ExcelUtil<FinanceAoLoan>(FinanceAoLoan.class);
        util.exportExcel(response, list, "借支申请主档数据");
    }


    /**
     * 新增借支申请主档
     */
    @RequiresPermissions("finance:loanApply:add")
    @Log(title = "借支申请主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoLoan financeAoLoan)
    {
        return toAjax(financeAoLoanService.insertFinanceAoLoan(financeAoLoan));
    }

    /**
     * 修改借支申请主档
     */
    @RequiresPermissions("finance:loanApply:edit")
    @Log(title = "借支申请主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoLoan financeAoLoan)
    {
        return toAjax(financeAoLoanService.updateFinanceAoLoan(financeAoLoan));
    }

    /**
     * 删除借支申请主档
     */
    @RequiresPermissions("finance:loanApply:remove")
    @Log(title = "借支申请主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoLoanService.deleteFinanceAoLoanByIds(ids));
    }
}
