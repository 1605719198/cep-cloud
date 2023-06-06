package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.AccountRules;
import com.jlkj.human.hs.service.IAccountRulesService;
import com.jlkj.human.hs.service.ISalaryProjectBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 抛帐规则维护Controller
 *
 * @author 116519
 * @date 2023-05-22
 */
@RestController
@RequestMapping("/accountRules")
public class AccountRulesController extends BaseController {
    @Autowired
    private IAccountRulesService accountRulesService;
    @Autowired
    private ISalaryProjectBasisService salaryProjectBasisService;
    /**
     * 查询抛帐规则维护列表
     */
    @RequiresPermissions("human:accountRules:list")
    @GetMapping("/list")
    public TableDataInfo list(AccountRules accountRules) {

        startPage();
        List<AccountRules> list = accountRulesService.selectAccountRulesList(accountRules);
        return getDataTable(list);
    }

    /**
     * 导出抛帐规则维护列表
     */
    @RequiresPermissions("human:accountRules:export")
    @Log(title = "抛帐规则维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AccountRules accountRules) {
        List<AccountRules> list = accountRulesService.selectAccountRulesList(accountRules);
        ExcelUtil<AccountRules> util = new ExcelUtil<AccountRules>(AccountRules.class);
        util.exportExcel(response, list, "抛帐规则维护数据");
    }

    /**
     * 获取抛帐规则维护详细信息
     */
    @RequiresPermissions("human:accountRules:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(accountRulesService.selectAccountRulesById(id));
    }

    /**
     * 新增抛帐规则维护
     */
    @RequiresPermissions("human:accountRules:add")
    @Log(title = "抛帐规则维护", businessType = BusinessType.INSERT)
    @PostMapping
    public int add(@RequestBody List<AccountRules> accountRulesList) {
        return accountRulesService.insertAccountRules(accountRulesList);
    }

    /**
     * 修改抛帐规则维护
     */
    @RequiresPermissions("human:accountRules:edit")
    @Log(title = "抛帐规则维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountRules accountRules) {
        return toAjax(accountRulesService.updateAccountRules(accountRules));
    }

    /**
     * 删除抛帐规则维护
     */
    @RequiresPermissions("human:accountRules:remove")
    @Log(title = "抛帐规则维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(accountRulesService.deleteAccountRulesByIds(ids));
    }


//    /**
//     * 查询薪酬项目树结构
//     */
//    @RequiresPermissions("human:accountRules:list")
//    /**
//     * 获取集团级薪资项目下拉树列表
//     */
//    @GetMapping("/listTree/{compId}")
//    public AjaxResult listTree(@PathVariable String compId) {
//        List<SalaryProjectBasis> salaryProjectBasiss = salaryProjectBasisService.selectSalaryProjectTableList(compId);
//        return AjaxResult.success(salaryProjectBasisService.buildSalaryProjectTreeSelect(salaryProjectBasiss));
//    }
}


