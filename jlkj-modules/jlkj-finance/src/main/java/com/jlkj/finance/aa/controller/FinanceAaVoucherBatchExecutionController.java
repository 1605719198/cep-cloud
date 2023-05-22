package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaVoucher;
import com.jlkj.finance.aa.service.IFinanceAaVoucherBatchExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 凭证批量执行Controller
 *
 * @author 265799
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/voucherBatchExecution")
public class FinanceAaVoucherBatchExecutionController  extends BaseController {
    @Autowired
    private IFinanceAaVoucherBatchExecutionService financeAaVoucherBatchExecutionService;
    /**
     * 查询凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaVoucher financeAaVoucher)
    {
        startPage();
        List<FinanceAaVoucher> list = financeAaVoucherBatchExecutionService.selectFinanceAaVoucherList(financeAaVoucher);
        return getDataTable(list);
    }
    /**
     * 确认和取消确认凭证维护-主
     */
    @RequiresPermissions("aa:voucher:edit")
    @Log(title = "凭证维护-主", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult editStatus(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateBy(getUsername());
        return toAjax(financeAaVoucherBatchExecutionService.updateFinanceAaVoucherStatus(financeAaVoucher));
    }
    /**
     * 过账凭证维护-主
     */
    @RequiresPermissions("aa:voucher:edit")
    @Log(title = "凭证维护-主", businessType = BusinessType.UPDATE)
    @PutMapping("/cross")
    public AjaxResult editCross(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateBy(getUsername());
        return toAjax(financeAaVoucherBatchExecutionService.updateFinanceAaVoucherCross(financeAaVoucher));
    }
    /**
     * 删除凭证维护-主
     */
    @RequiresPermissions("aa:voucher:remove")
    @Log(title = "凭证维护-主", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult remove(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        return toAjax(financeAaVoucherBatchExecutionService.deleteFinanceAaVoucherByIds(financeAaVoucher));
    }
}
