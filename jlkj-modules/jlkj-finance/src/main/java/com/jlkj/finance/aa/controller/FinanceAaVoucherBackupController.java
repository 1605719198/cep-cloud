package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.finance.aa.service.IFinanceAaVoucherBackupService;
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
import com.jlkj.finance.aa.domain.FinanceAaVoucherBackup;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 凭证维护-主备份Controller
 *
 * @author jlkj
 * @date 2023-04-28
 */
@RestController
@RequestMapping("/backup")
public class FinanceAaVoucherBackupController extends BaseController
{
    @Autowired
    private IFinanceAaVoucherBackupService financeAaVoucherBackupService;

    /**
     * 查询凭证维护-主备份列表
     */
    @RequiresPermissions("aa:backup:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        startPage();
        List<FinanceAaVoucherBackup> list = financeAaVoucherBackupService.selectFinanceAaVoucherBackupList(financeAaVoucherBackup);
        return getDataTable(list);
    }

    /**
     * 导出凭证维护-主备份列表
     */
    @RequiresPermissions("aa:backup:export")
    @Log(title = "凭证维护-主备份", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        List<FinanceAaVoucherBackup> list = financeAaVoucherBackupService.selectFinanceAaVoucherBackupList(financeAaVoucherBackup);
        ExcelUtil<FinanceAaVoucherBackup> util = new ExcelUtil<FinanceAaVoucherBackup>(FinanceAaVoucherBackup.class);
        util.exportExcel(response, list, "凭证维护-主备份数据");
    }

    /**
     * 获取凭证维护-主备份详细信息
     */
    @RequiresPermissions("aa:backup:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaVoucherBackupService.selectFinanceAaVoucherBackupById(id));
    }

    /**
     * 新增凭证维护-主备份
     */
    @RequiresPermissions("aa:backup:add")
    @Log(title = "凭证维护-主备份", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        return toAjax(financeAaVoucherBackupService.insertFinanceAaVoucherBackup(financeAaVoucherBackup));
    }

    /**
     * 修改凭证维护-主备份
     */
    @RequiresPermissions("aa:backup:edit")
    @Log(title = "凭证维护-主备份", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaVoucherBackup financeAaVoucherBackup)
    {
        return toAjax(financeAaVoucherBackupService.updateFinanceAaVoucherBackup(financeAaVoucherBackup));
    }

    /**
     * 删除凭证维护-主备份
     */
    @RequiresPermissions("aa:backup:remove")
    @Log(title = "凭证维护-主备份", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaVoucherBackupService.deleteFinanceAaVoucherBackupByIds(ids));
    }
}
