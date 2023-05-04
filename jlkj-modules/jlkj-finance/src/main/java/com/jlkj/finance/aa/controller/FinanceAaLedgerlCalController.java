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
import com.jlkj.finance.aa.domain.FinanceAaLedgerlCal;
import com.jlkj.finance.aa.service.IFinanceAaLedgerlCalService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 账薄-核算项目余额Controller
 *
 * @author jlkj
 * @date 2023-04-29
 */
@RestController
@RequestMapping("/cal")
public class FinanceAaLedgerlCalController extends BaseController
{
    @Autowired
    private IFinanceAaLedgerlCalService financeAaLedgerlCalService;

    /**
     * 查询账薄-核算项目余额列表
     */
    @RequiresPermissions("aa:cal:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        startPage();
        List<FinanceAaLedgerlCal> list = financeAaLedgerlCalService.selectFinanceAaLedgerlCalList(financeAaLedgerlCal);
        return getDataTable(list);
    }

    /**
     * 导出账薄-核算项目余额列表
     */
    @RequiresPermissions("aa:cal:export")
    @Log(title = "账薄-核算项目余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        List<FinanceAaLedgerlCal> list = financeAaLedgerlCalService.selectFinanceAaLedgerlCalList(financeAaLedgerlCal);
        ExcelUtil<FinanceAaLedgerlCal> util = new ExcelUtil<FinanceAaLedgerlCal>(FinanceAaLedgerlCal.class);
        util.exportExcel(response, list, "账薄-核算项目余额数据");
    }

    /**
     * 获取账薄-核算项目余额详细信息
     */
    @RequiresPermissions("aa:cal:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaLedgerlCalService.selectFinanceAaLedgerlCalById(id));
    }

    /**
     * 新增账薄-核算项目余额
     */
    @RequiresPermissions("aa:cal:add")
    @Log(title = "账薄-核算项目余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        return toAjax(financeAaLedgerlCalService.insertFinanceAaLedgerlCal(financeAaLedgerlCal));
    }

    /**
     * 修改账薄-核算项目余额
     */
    @RequiresPermissions("aa:cal:edit")
    @Log(title = "账薄-核算项目余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaLedgerlCal financeAaLedgerlCal)
    {
        return toAjax(financeAaLedgerlCalService.updateFinanceAaLedgerlCal(financeAaLedgerlCal));
    }

    /**
     * 删除账薄-核算项目余额
     */
    @RequiresPermissions("aa:cal:remove")
    @Log(title = "账薄-核算项目余额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaLedgerlCalService.deleteFinanceAaLedgerlCalByIds(ids));
    }
}
