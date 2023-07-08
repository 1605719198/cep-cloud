package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoReimbursementLoan;
import com.jlkj.finance.ao.service.IFinanceAoReimbursementLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报支管理-费用报销冲借支单档Controller
 * @Description:
 * @author 265799
 * @date 2023-06-28
 * @version:V1.0
 */
@RestController
@RequestMapping("/loan")
public class FinanceAoReimbursementLoanController extends BaseController
{
    @Autowired
    private IFinanceAoReimbursementLoanService financeAoReimbursementLoanService;

    /**
     * 查询报支管理-费用报销冲借支单档列表
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan
     * @return
     */
    @RequiresPermissions("ao:loan:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        startPage();
        List<FinanceAoReimbursementLoan> list = financeAoReimbursementLoanService.selectFinanceAoReimbursementLoanList(financeAoReimbursementLoan);
        return getDataTable(list);
    }
    /**
     * 获取报支管理-费用报销冲借支单档详细信息
     * @author 265799
     * @date 2023-06-28
     * @return
     */
    @RequiresPermissions("ao:loan:query")
    @GetMapping(value = "/listLoan")
    public AjaxResult listLoan(FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        return success(financeAoReimbursementLoanService.selectFinanceAoReimbursementLoanListLoan(financeAoReimbursementLoan));
    }
    /**
     * 导出报支管理-费用报销冲借支单档列表
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan
     * @return
     */
    @RequiresPermissions("ao:loan:export")
    @Log(title = "报支管理-费用报销冲借支单档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        List<FinanceAoReimbursementLoan> list = financeAoReimbursementLoanService.selectFinanceAoReimbursementLoanList(financeAoReimbursementLoan);
        ExcelUtil<FinanceAoReimbursementLoan> util = new ExcelUtil<FinanceAoReimbursementLoan>(FinanceAoReimbursementLoan.class);
        util.exportExcel(response, list, "报支管理-费用报销冲借支单档数据");
    }

    /**
     * 获取报支管理-费用报销冲借支单档详细信息
     * @author 265799
     * @date 2023-06-28
     * @return
     */
    @RequiresPermissions("ao:loan:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoReimbursementLoanService.selectFinanceAoReimbursementLoanById(id));
    }

    /**
     * 新增报支管理-费用报销冲借支单档
     * @author 265799
     * @date 2023-06-28
     * @return
     */
    @RequiresPermissions("ao:loan:add")
    @Log(title = "报支管理-费用报销冲借支单档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        return toAjax(financeAoReimbursementLoanService.insertFinanceAoReimbursementLoan(financeAoReimbursementLoan));
    }

    /**
     * 修改报支管理-费用报销冲借支单档
     * @author 265799
     * @date 2023-06-28
     * @param financeAoReimbursementLoan
     * @return
     */
    @RequiresPermissions("ao:loan:edit")
    @Log(title = "报支管理-费用报销冲借支单档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoReimbursementLoan financeAoReimbursementLoan)
    {
        return toAjax(financeAoReimbursementLoanService.updateFinanceAoReimbursementLoan(financeAoReimbursementLoan));
    }

    /**
     * 删除报支管理-费用报销冲借支单档
     * @author 265799
     * @date 2023-06-28

     * @return
     */
    @RequiresPermissions("ao:loan:remove")
    @Log(title = "报支管理-费用报销冲借支单档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoReimbursementLoanService.deleteFinanceAoReimbursementLoanByIds(ids));
    }
}
