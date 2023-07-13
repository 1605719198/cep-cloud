package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApLoan;
import com.jlkj.finance.ap.service.IFinanceApLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 预付申请主档Controller
 * @Description:
 * @author 265799
 * @date 2023-07-10
 * @version:V1.0
 */
@RestController
@RequestMapping("/ap/loan")
public class FinanceApLoanController extends BaseController
{
    @Autowired
    private IFinanceApLoanService financeApLoanService;

    /**
     * 查询预付申请主档列表
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan
     * @return
     */
    @RequiresPermissions("ap:loan:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApLoan financeApLoan)
    {
        startPage();
        List<FinanceApLoan> list = financeApLoanService.selectFinanceApLoanList(financeApLoan);
        return getDataTable(list);
    }

    /**
     * 导出预付申请主档列表
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan
     * @return
     */
    @RequiresPermissions("ap:loan:export")
    @Log(title = "预付申请主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApLoan financeApLoan)
    {
        List<FinanceApLoan> list = financeApLoanService.selectFinanceApLoanList(financeApLoan);
        ExcelUtil<FinanceApLoan> util = new ExcelUtil<FinanceApLoan>(FinanceApLoan.class);
        util.exportExcel(response, list, "预付申请主档数据");
    }

    /**
     * 获取预付申请主档详细信息
     * @author 265799
     * @date 2023-07-10
     * @param id
     * @return
     */
    @RequiresPermissions("ap:loan:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApLoanService.selectFinanceApLoanById(id));
    }

    /**
     * 新增预付申请主档
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan
     * @return
     */
    @RequiresPermissions("ap:loan:add")
    @Log(title = "预付申请主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApLoan financeApLoan)
    {
        return toAjax(financeApLoanService.insertFinanceApLoan(financeApLoan));
    }

    /**
     * 修改预付申请主档
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoan
     * @return
     */
    @RequiresPermissions("ap:loan:edit")
    @Log(title = "预付申请主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApLoan financeApLoan)
    {
        return toAjax(financeApLoanService.updateFinanceApLoan(financeApLoan));
    }

    /**
     * 删除预付申请主档
     * @author 265799
     * @date 2023-07-10
     * @param ids
     * @return
     */
    @RequiresPermissions("ap:loan:remove")
    @Log(title = "预付申请主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApLoanService.deleteFinanceApLoanByIds(ids));
    }
}
