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
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetailBackup;
import com.jlkj.finance.aa.service.IFinanceAaVoucherDetailBackupService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 凭证维护-明细备份Controller
 *
 * @author jlkj
 * @date 2023-04-28
 */
@RestController
@RequestMapping("/VoucherDetailbackup")
public class FinanceAaVoucherDetailBackupController extends BaseController
{
    @Autowired
    private IFinanceAaVoucherDetailBackupService financeAaVoucherDetailBackupService;

    /**
     * 查询凭证维护-明细备份列表
     */
    @RequiresPermissions("aa:VoucherDetailbackup:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup)
    {
        startPage();
        List<FinanceAaVoucherDetailBackup> list = financeAaVoucherDetailBackupService.selectFinanceAaVoucherDetailBackupList(financeAaVoucherDetailBackup);
        return getDataTable(list);
    }

    /**
     * 导出凭证维护-明细备份列表
     */
    @RequiresPermissions("aa:VoucherDetailbackup:export")
    @Log(title = "凭证维护-明细备份", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup)
    {
        List<FinanceAaVoucherDetailBackup> list = financeAaVoucherDetailBackupService.selectFinanceAaVoucherDetailBackupList(financeAaVoucherDetailBackup);
        ExcelUtil<FinanceAaVoucherDetailBackup> util = new ExcelUtil<FinanceAaVoucherDetailBackup>(FinanceAaVoucherDetailBackup.class);
        util.exportExcel(response, list, "凭证维护-明细备份数据");
    }

    /**
     * 获取凭证维护-明细备份详细信息
     */
    @RequiresPermissions("aa:VoucherDetailbackup:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaVoucherDetailBackupService.selectFinanceAaVoucherDetailBackupById(id));
    }

    /**
     * 新增凭证维护-明细备份
     */
    @RequiresPermissions("aa:VoucherDetailbackup:add")
    @Log(title = "凭证维护-明细备份", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup)
    {
        return toAjax(financeAaVoucherDetailBackupService.insertFinanceAaVoucherDetailBackup(financeAaVoucherDetailBackup));
    }

    /**
     * 修改凭证维护-明细备份
     */
    @RequiresPermissions("aa:VoucherDetailbackup:edit")
    @Log(title = "凭证维护-明细备份", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaVoucherDetailBackup financeAaVoucherDetailBackup)
    {
        return toAjax(financeAaVoucherDetailBackupService.updateFinanceAaVoucherDetailBackup(financeAaVoucherDetailBackup));
    }

    /**
     * 删除凭证维护-明细备份
     */
    @RequiresPermissions("aa:VoucherDetailbackup:remove")
    @Log(title = "凭证维护-明细备份", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaVoucherDetailBackupService.deleteFinanceAaVoucherDetailBackupByIds(ids));
    }
}
