package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoAccount;
import com.jlkj.finance.ao.service.IFinanceAoAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会计确认主档Controller
 *
 * @author 265799
 * @date 2023-06-25
 */
@RestController
@RequestMapping("/ao/accountHost")
public class FinanceAoAccountController extends BaseController
{
    @Autowired
    private IFinanceAoAccountService financeAoAccountService;

    /**
     * 查询会计确认主档列表
     */
    @RequiresPermissions("ao:accountHost:query")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoAccount financeAoAccount)
    {
        startPage();
        List<FinanceAoAccount> list = financeAoAccountService.selectFinanceAoAccountList(financeAoAccount);
        return getDataTable(list);
    }
    /**
     * 查询会计确认主档列表
     */
    @RequiresPermissions("ao:accountHost:query")
    @GetMapping("/detailList")
    public TableDataInfo detailList(FinanceAoAccount financeAoAccount)
    {
        startPage();
        List<FinanceAoAccount> list = financeAoAccountService.selectDetailList(financeAoAccount);
        return getDataTable(list);
    }
    /**
     * 导出会计确认主档列表
     */
    @RequiresPermissions("ao:accountHost:query")
    @Log(title = "会计确认主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoAccount financeAoAccount)
    {
        List<FinanceAoAccount> list = financeAoAccountService.selectFinanceAoAccountList(financeAoAccount);
        ExcelUtil<FinanceAoAccount> util = new ExcelUtil<FinanceAoAccount>(FinanceAoAccount.class);
        util.exportExcel(response, list, "会计确认主档数据");
    }

    /**
     * 获取会计确认主档详细信息
     */
    @RequiresPermissions("ao:accountHost:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoAccountService.selectFinanceAoAccountById(id));
    }

    /**
     * 新增会计确认主档
     */
    @RequiresPermissions("ao:accountHost:query")
    @Log(title = "会计确认主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoAccount financeAoAccount)
    {
        return toAjax(financeAoAccountService.insertFinanceAoAccount(financeAoAccount));
    }

    /**
     * 修改会计确认主档
     */
    @RequiresPermissions("ao:accountHost:query")
    @Log(title = "会计确认主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoAccount financeAoAccount)
    {
        return toAjax(financeAoAccountService.updateFinanceAoAccount(financeAoAccount));
    }
    /**
     * 修改会计确认主档
     */
    @RequiresPermissions("ao:accountHost:query")
    @Log(title = "会计确认主档", businessType = BusinessType.UPDATE)
    @PutMapping("/editRepayment")
    public AjaxResult editRepayment(@RequestBody FinanceAoAccount financeAoAccount)
    {
        return toAjax(financeAoAccountService.updateFinanceAoAccountRepayment(financeAoAccount));
    }


    /**
     * 删除会计确认主档
     */
    @RequiresPermissions("ao:accountHost:query")
    @Log(title = "会计确认主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoAccountService.deleteFinanceAoAccountByIds(ids));
    }
}
