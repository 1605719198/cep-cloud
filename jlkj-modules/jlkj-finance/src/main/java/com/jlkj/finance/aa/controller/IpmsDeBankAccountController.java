package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.IpmsDeBankAccount;
import com.jlkj.finance.aa.service.IIpmsDeBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 往来银行Controller
 *
 * @author SunXuTong
 * @date 2023-05-22
 */
@RestController
@RequestMapping("/account")
public class IpmsDeBankAccountController extends BaseController
{
    @Autowired
    private IIpmsDeBankAccountService ipmsDeBankAccountService;

    /**
     * 查询往来银行列表
     */
    @RequiresPermissions("finance:account:list")
    @GetMapping("/list")
    public TableDataInfo list(IpmsDeBankAccount ipmsDeBankAccount)
    {
        startPage();
        List<IpmsDeBankAccount> list = ipmsDeBankAccountService.selectIpmsDeBankAccountList(ipmsDeBankAccount);
        return getDataTable(list);
    }

    /**
     * 导出往来银行列表
     */
    @RequiresPermissions("finance:account:export")
    @Log(title = "往来银行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IpmsDeBankAccount ipmsDeBankAccount)
    {
        List<IpmsDeBankAccount> list = ipmsDeBankAccountService.selectIpmsDeBankAccountList(ipmsDeBankAccount);
        ExcelUtil<IpmsDeBankAccount> util = new ExcelUtil<IpmsDeBankAccount>(IpmsDeBankAccount.class);
        util.exportExcel(response, list, "往来银行数据");
    }

    /**
     * 获取往来银行详细信息
     */
    @RequiresPermissions("finance:account:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(ipmsDeBankAccountService.selectIpmsDeBankAccountById(id));
    }

    /**
     * 新增往来银行
     */
    @RequiresPermissions("finance:account:add")
    @Log(title = "往来银行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IpmsDeBankAccount ipmsDeBankAccount)
    {
        return toAjax(ipmsDeBankAccountService.insertIpmsDeBankAccount(ipmsDeBankAccount));
    }

    /**
     * 修改往来银行
     */
    @RequiresPermissions("finance:account:edit")
    @Log(title = "往来银行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IpmsDeBankAccount ipmsDeBankAccount)
    {
        return toAjax(ipmsDeBankAccountService.updateIpmsDeBankAccount(ipmsDeBankAccount));
    }

    /**
     * 删除往来银行
     */
    @RequiresPermissions("finance:account:remove")
    @Log(title = "往来银行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(ipmsDeBankAccountService.deleteIpmsDeBankAccountByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:account:edit")
    @Log(title = "往来银行", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody IpmsDeBankAccount ipmsDeBankAccount)
    {
        ipmsDeBankAccount.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(ipmsDeBankAccountService.updateStatus(ipmsDeBankAccount));
    }
}
