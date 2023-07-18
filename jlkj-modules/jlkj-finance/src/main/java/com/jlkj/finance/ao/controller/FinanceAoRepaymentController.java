package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoRepayment;
import com.jlkj.finance.ao.service.IFinanceAoRepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 还款单资料档Controller
 *
 * @author 116514
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/repayment")
public class FinanceAoRepaymentController extends BaseController
{
    @Autowired
    private IFinanceAoRepaymentService financeAoRepaymentService;

    /**
     * 查询还款单资料档列表
     */
    @RequiresPermissions("finance:repayment:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoRepayment financeAoRepayment)
    {
        startPage();
        List<FinanceAoRepayment> list = financeAoRepaymentService.selectFinanceAoRepaymentList(financeAoRepayment);
        return getDataTable(list);
    }

    /**
     * 导出还款单资料档列表
     */
    @RequiresPermissions("finance:repayment:export")
    @Log(title = "还款单资料档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoRepayment financeAoRepayment)
    {
        List<FinanceAoRepayment> list = financeAoRepaymentService.selectFinanceAoRepaymentList(financeAoRepayment);
        ExcelUtil<FinanceAoRepayment> util = new ExcelUtil<FinanceAoRepayment>(FinanceAoRepayment.class);
        util.exportExcel(response, list, "还款单资料档数据");
    }

    /**
     * 获取还款单资料档详细信息
     */
    @RequiresPermissions("finance:repayment:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoRepaymentService.selectRepaymentById(id));
    }

    /**
     * 新增还款单资料档
     */
    @RequiresPermissions("finance:repayment:add")
    @Log(title = "还款单资料档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoRepayment financeAoRepayment)
    {
        return toAjax(financeAoRepaymentService.insertFinanceAoRepayment(financeAoRepayment));
    }

    /**
     * 修改还款单资料档
     */
    @RequiresPermissions("finance:repayment:edit")
    @Log(title = "还款单资料档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoRepayment financeAoRepayment)
    {
        return toAjax(financeAoRepaymentService.updateFinanceAoRepayment(financeAoRepayment));
    }

    /**
     * 删除还款单资料档
     */
    @RequiresPermissions("finance:repayment:remove")
    @Log(title = "还款单资料档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoRepaymentService.deleteFinanceAoRepaymentByIds(ids));
    }


    /**
     * 查询借支申请单
     */
    @RequiresPermissions("finance:repayment:query")
    @GetMapping(value = "/loanApply")
    public TableDataInfo selectLoanApply(FinanceAoRepayment financeAoRepayment)
    {
        startPage();
        List<Map<String, String>> list = financeAoRepaymentService.selectLoanApply(financeAoRepayment.getCompanyId());
        return getDataTable(list);
    }

    /**
     * 查询还款单列表
     */
    @RequiresPermissions("finance:repayment:list")
    @GetMapping("/qryList")
    public TableDataInfo qryList(FinanceAoRepayment financeAoRepayment)
    {
        startPage();
        List<Map<String,Object>> list = financeAoRepaymentService.selectRepaymentList(financeAoRepayment);
        return getDataTable(list);
    }


    /**
     * 批量新增还款单资料档
     */
    @RequiresPermissions("finance:repayment:add")
    @Log(title = "批量新增还款单资料档", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult addBatch(@RequestBody List<FinanceAoRepayment> financeAoRepayments)
    {
        return toAjax(financeAoRepaymentService.insertBatchFinanceAoRepayment(financeAoRepayments));
    }

    /**
     * 成本中心清单查询
     */
    @PostMapping("/costCenterList")
    public TableDataInfo selectcostCenterList(@RequestBody Map paramInfo)
    {
        startPage();
        List<Map<String, Object>> list = financeAoRepaymentService.selectcostCenterList(paramInfo);
        return getDataTable(list);
    }

}
