package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.PayamtRules;
import com.jlkj.human.hs.service.IPayamtRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 应付抛帐设定Controller
 *
 * @author 116519
 * @date 2023-06-20
 */
@RestController
@RequestMapping("/payamtRules")
public class PayamtRulesController extends BaseController
{
    @Autowired
    private IPayamtRulesService payamtRulesService;

    /**
     * 查询应付抛帐设定列表
     */
    @RequiresPermissions("human:payamtRules:list")
    @GetMapping("/list")
    public TableDataInfo list(PayamtRules payamtRules)
    {
        startPage();
        List<PayamtRules> list = payamtRulesService.selectPayamtRules(payamtRules);
        return getDataTable(list);
    }

    /**
     * 导出应付抛帐设定列表
     */
    @RequiresPermissions("human:payamtRules:export")
    @Log(title = "应付抛帐设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayamtRules payamtRules)
    {
        List<PayamtRules> list = payamtRulesService.selectPayamtRulesList(payamtRules);
        ExcelUtil<PayamtRules> util = new ExcelUtil<PayamtRules>(PayamtRules.class);
        util.exportExcel(response, list, "应付抛帐设定数据");
    }

    /**
     * 获取应付抛帐设定详细信息
     */
    @RequiresPermissions("human:payamtRules:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(payamtRulesService.selectPayamtRulesById(id));
    }

//    /**
//     * 新增应付抛帐设定
//     */
//    @RequiresPermissions("human:payamtRules:add")
//    @Log(title = "应付抛帐设定", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody PayamtRules payamtRules)
//    {
//        return toAjax(payamtRulesService.insertPayamtRules(payamtRules));
//    }

    /**
     * 新增抛帐规则维护
     */
    @RequiresPermissions("human:payamtRules:add")
    @Log(title = "应付抛帐设定", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public int save(@RequestBody List<PayamtRules> payamtRulesList) {
        return payamtRulesService.insertPayamtRules(payamtRulesList);
    }



    /**
     * 修改应付抛帐设定
     */
    @RequiresPermissions("human:payamtRules:edit")
    @Log(title = "应付抛帐设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayamtRules payamtRules)
    {
        return toAjax(payamtRulesService.updatePayamtRules(payamtRules));
    }

    /**
     * 删除应付抛帐设定
     */
    @RequiresPermissions("human:payamtRules:remove")
    @Log(title = "应付抛帐设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(payamtRulesService.deletePayamtRulesByIds(ids));
    }
}
