package com.jlkj.finance.aa.controller;

import java.util.List;
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
import com.jlkj.finance.aa.domain.FinanceAaCarryRulesCorp;
import com.jlkj.finance.aa.service.IFinanceAaCarryRulesCorpService;
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
@RequestMapping("/financeAaCarryRulesCorp")
public class FinanceAaCarryRulesCorpController extends BaseController
{
    @Autowired
    private IFinanceAaCarryRulesCorpService financeAaCarryRulesCorpService;

    /**
     * 查询集团管理-自动结转规则-规则设置列表
     */
    @RequiresPermissions("finance:financeAaCarryRulesCorp:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCarryRulesCorp financeAaCarryRulesCorp)
    {
        startPage();
        List<FinanceAaCarryRulesCorp> list = financeAaCarryRulesCorpService.selectFinanceAaCarryRulesCorpList(financeAaCarryRulesCorp);
        return getDataTable(list);
    }

    /**
     * 导出集团管理-自动结转规则-规则设置列表
     */
    @RequiresPermissions("finance:financeAaCarryRulesCorp:export")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCarryRulesCorp financeAaCarryRulesCorp)
    {
        List<FinanceAaCarryRulesCorp> list = financeAaCarryRulesCorpService.selectFinanceAaCarryRulesCorpList(financeAaCarryRulesCorp);
        ExcelUtil<FinanceAaCarryRulesCorp> util = new ExcelUtil<FinanceAaCarryRulesCorp>(FinanceAaCarryRulesCorp.class);
        util.exportExcel(response, list, "集团管理-自动结转规则-规则设置数据");
    }

    /**
     * 获取集团管理-自动结转规则-规则设置详细信息
     */
    @RequiresPermissions("finance:financeAaCarryRulesCorp:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCarryRulesCorpService.selectFinanceAaCarryRulesCorpById(id));
    }

    /**
     * 新增集团管理-自动结转规则-规则设置
     */
    @RequiresPermissions("finance:financeAaCarryRulesCorp:add")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCarryRulesCorp financeAaCarryRulesCorp)
    {
        return toAjax(financeAaCarryRulesCorpService.insertFinanceAaCarryRulesCorp(financeAaCarryRulesCorp));
    }

    /**
     * 修改集团管理-自动结转规则-规则设置
     */
    @RequiresPermissions("finance:financeAaCarryRulesCorp:edit")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCarryRulesCorp financeAaCarryRulesCorp)
    {
        return toAjax(financeAaCarryRulesCorpService.updateFinanceAaCarryRulesCorp(financeAaCarryRulesCorp));
    }

    /**
     * 删除集团管理-自动结转规则-规则设置
     */
    @RequiresPermissions("finance:financeAaCarryRulesCorp:remove")
    @Log(title = "集团管理-自动结转规则-规则设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCarryRulesCorpService.deleteFinanceAaCarryRulesCorpByIds(ids));
    }
}
