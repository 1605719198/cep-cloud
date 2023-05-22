package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApAccrule;
import com.jlkj.finance.ap.service.IFinanceApAccruleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 付款抛账规则维护Controller
 *
 * @author SunXuTong
 * @date 2023-05-17
 */
@RestController
@RequestMapping("/accrule")
public class FinanceApAccruleController extends BaseController
{
    @Autowired
    private IFinanceApAccruleService financeApAccruleService;

    /**
     * 查询付款抛账规则维护列表
     */
    @RequiresPermissions("finance:accrule:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApAccrule financeApAccrule)
    {
        startPage();
        List<FinanceApAccrule> list = financeApAccruleService.selectFinanceApAccruleList(financeApAccrule);
        return getDataTable(list);
    }

    /**
     * 导出付款抛账规则维护列表
     */
    @RequiresPermissions("finance:accrule:export")
    @Log(title = "付款抛账规则维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApAccrule financeApAccrule)
    {
        List<FinanceApAccrule> list = financeApAccruleService.selectFinanceApAccruleList(financeApAccrule);
        ExcelUtil<FinanceApAccrule> util = new ExcelUtil<FinanceApAccrule>(FinanceApAccrule.class);
        util.exportExcel(response, list, "付款抛账规则维护数据");
    }

    /**
     * 获取付款抛账规则维护详细信息
     */
    @RequiresPermissions("finance:accrule:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApAccruleService.selectFinanceApAccruleById(id));
    }

    /**
     * 新增付款抛账规则维护
     */
    @RequiresPermissions("finance:accrule:add")
    @Log(title = "付款抛账规则维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApAccrule financeApAccrule)
    {
        return toAjax(financeApAccruleService.insertFinanceApAccrule(financeApAccrule));
    }

    /**
     * 修改付款抛账规则维护
     */
    @RequiresPermissions("finance:accrule:edit")
    @Log(title = "付款抛账规则维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApAccrule financeApAccrule)
    {
        return toAjax(financeApAccruleService.updateFinanceApAccrule(financeApAccrule));
    }

    /**
     * 删除付款抛账规则维护
     */
    @RequiresPermissions("finance:accrule:remove")
    @Log(title = "付款抛账规则维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApAccruleService.deleteFinanceApAccruleByIds(ids));
    }
}
