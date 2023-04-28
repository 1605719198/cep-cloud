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
import com.jlkj.finance.aa.domain.FinanceAaCarryRules;
import com.jlkj.finance.aa.service.IFinanceAaCarryRulesService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 集团管理-自动结转规则-规则设置Controller
 *
 * @author 116514
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/financeAaCarryRules")
public class FinanceAaCarryRulesController extends BaseController
{
    @Autowired
    private IFinanceAaCarryRulesService financeAaCarryRulesService;

    /**
     * 查询集团管理-自动结转规则-规则设置列表
     */
    @RequiresPermissions("finance:financeAaCarryRules:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCarryRules financeAaCarryRules)
    {
        startPage();
        List<FinanceAaCarryRules> list = financeAaCarryRulesService.selectFinanceAaCarryRulesList(financeAaCarryRules);
        return getDataTable(list);
    }

    /**
     * 导出集团管理-自动结转规则-规则设置列表
     */
    @RequiresPermissions("finance:financeAaCarryRules:export")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCarryRules financeAaCarryRules)
    {
        List<FinanceAaCarryRules> list = financeAaCarryRulesService.selectFinanceAaCarryRulesList(financeAaCarryRules);
        ExcelUtil<FinanceAaCarryRules> util = new ExcelUtil<FinanceAaCarryRules>(FinanceAaCarryRules.class);
        util.exportExcel(response, list, "集团管理-自动结转规则-规则设置数据");
    }

    /**
     * 获取集团管理-自动结转规则-规则设置详细信息
     */
    @RequiresPermissions("finance:financeAaCarryRules:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCarryRulesService.selectFinanceAaCarryRulesById(id));
    }

    /**
     * 新增集团管理-自动结转规则-规则设置
     */
    @RequiresPermissions("finance:financeAaCarryRules:add")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCarryRules financeAaCarryRules)
    {
        return toAjax(financeAaCarryRulesService.insertFinanceAaCarryRules(financeAaCarryRules));
    }

    /**
     * 修改集团管理-自动结转规则-规则设置
     */
    @RequiresPermissions("finance:financeAaCarryRules:edit")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCarryRules financeAaCarryRules)
    {
        return toAjax(financeAaCarryRulesService.updateFinanceAaCarryRules(financeAaCarryRules));
    }

    /**
     * 删除集团管理-自动结转规则-规则设置
     */
    @RequiresPermissions("finance:financeAaCarryRules:remove")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCarryRulesService.deleteFinanceAaCarryRulesByIds(ids));
    }
}
