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
import com.jlkj.finance.aa.domain.FinanceCalSysRule;
import com.jlkj.finance.aa.service.IFinanceCalSysRuleService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 核算项目-系统设定Controller
 *
 * @author 116514
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/sysRule")
public class FinanceCalSysRuleController extends BaseController
{
    @Autowired
    private IFinanceCalSysRuleService financeCalSysRuleService;

    /**
     * 查询核算项目-系统设定列表
     */
    @RequiresPermissions("finance:calType:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceCalSysRule financeCalSysRule)
    {
        startPage();
        List<FinanceCalSysRule> list = financeCalSysRuleService.selectFinanceCalSysRuleList(financeCalSysRule);
        return getDataTable(list);
    }

    /**
     * 导出核算项目-系统设定列表
     */
    @RequiresPermissions("finance:calType:export")
    @Log(title = "核算项目-系统设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceCalSysRule financeCalSysRule)
    {
        List<FinanceCalSysRule> list = financeCalSysRuleService.selectFinanceCalSysRuleList(financeCalSysRule);
        ExcelUtil<FinanceCalSysRule> util = new ExcelUtil<FinanceCalSysRule>(FinanceCalSysRule.class);
        util.exportExcel(response, list, "核算项目-系统设定数据");
    }

    /**
     * 获取核算项目-系统设定详细信息
     */
    @RequiresPermissions("finance:calType:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeCalSysRuleService.selectFinanceCalSysRuleById(id));
    }

    /**
     * 新增核算项目-系统设定
     */
    @RequiresPermissions("finance:calType:add")
    @Log(title = "核算项目-系统设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceCalSysRule financeCalSysRule)
    {
        return toAjax(financeCalSysRuleService.insertFinanceCalSysRule(financeCalSysRule));
    }

    /**
     * 修改核算项目-系统设定
     */
    @RequiresPermissions("finance:calType:edit")
    @Log(title = "核算项目-系统设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceCalSysRule financeCalSysRule)
    {
        return toAjax(financeCalSysRuleService.updateFinanceCalSysRule(financeCalSysRule));
    }

    /**
     * 删除核算项目-系统设定
     */
    @RequiresPermissions("finance:calType:remove")
    @Log(title = "核算项目-系统设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeCalSysRuleService.deleteFinanceCalSysRuleByIds(ids));
    }
}
