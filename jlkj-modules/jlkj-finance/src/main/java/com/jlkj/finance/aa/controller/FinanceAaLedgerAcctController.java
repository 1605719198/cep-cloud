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
import com.jlkj.finance.aa.domain.FinanceAaLedgerAcct;
import com.jlkj.finance.aa.service.IFinanceAaLedgerAcctService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 账薄-会计科目余额Controller
 *
 * @author jlkj
 * @date 2023-04-29
 */
@RestController
@RequestMapping("/acct")
public class FinanceAaLedgerAcctController extends BaseController
{
    @Autowired
    private IFinanceAaLedgerAcctService financeAaLedgerAcctService;

    /**
     * 查询账薄-会计科目余额列表
     */
    @RequiresPermissions("aa:acct:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        startPage();
        List<FinanceAaLedgerAcct> list = financeAaLedgerAcctService.selectFinanceAaLedgerAcctList(financeAaLedgerAcct);
        return getDataTable(list);
    }

    /**
     * 导出账薄-会计科目余额列表
     */
    @RequiresPermissions("aa:acct:export")
    @Log(title = "账薄-会计科目余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        List<FinanceAaLedgerAcct> list = financeAaLedgerAcctService.selectFinanceAaLedgerAcctList(financeAaLedgerAcct);
        ExcelUtil<FinanceAaLedgerAcct> util = new ExcelUtil<FinanceAaLedgerAcct>(FinanceAaLedgerAcct.class);
        util.exportExcel(response, list, "账薄-会计科目余额数据");
    }

    /**
     * 获取账薄-会计科目余额详细信息
     */
    @RequiresPermissions("aa:acct:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaLedgerAcctService.selectFinanceAaLedgerAcctById(id));
    }

    /**
     * 新增账薄-会计科目余额
     */
    @RequiresPermissions("aa:acct:add")
    @Log(title = "账薄-会计科目余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        return toAjax(financeAaLedgerAcctService.insertFinanceAaLedgerAcct(financeAaLedgerAcct));
    }

    /**
     * 修改账薄-会计科目余额
     */
    @RequiresPermissions("aa:acct:edit")
    @Log(title = "账薄-会计科目余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaLedgerAcct financeAaLedgerAcct)
    {
        return toAjax(financeAaLedgerAcctService.updateFinanceAaLedgerAcct(financeAaLedgerAcct));
    }

    /**
     * 删除账薄-会计科目余额
     */
    @RequiresPermissions("aa:acct:remove")
    @Log(title = "账薄-会计科目余额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaLedgerAcctService.deleteFinanceAaLedgerAcctByIds(ids));
    }
}
