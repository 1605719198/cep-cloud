package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
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
import com.jlkj.finance.aa.domain.FinanceAaBase;
import com.jlkj.finance.aa.service.IFinanceAaBaseService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 系统选单-基本设定Controller
 *
 * @author jlkj
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/base")
public class FinanceAaBaseController extends BaseController
{
    @Autowired
    private IFinanceAaBaseService financeAaBaseService;

    /**
     * 查询系统选单-基本设定列表
     */
    @RequiresPermissions("aa:base:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaBase financeAaBase)
    {
        startPage();
        List<FinanceAaBase> list = financeAaBaseService.selectFinanceAaBaseList(financeAaBase);
        return getDataTable(list);
    }

    /**
     * 查询系统选单-基本设定列表
     */
    @RequiresPermissions("aa:base:list")
    @GetMapping("/listTab")
    public AjaxResult listTab(FinanceAaBase financeAaBase)
    {

        List<JSONObject> list = financeAaBaseService.selectFinanceAaBaseListTab(financeAaBase);
        return  success(list);
    }

    /**
     * 导出系统选单-基本设定列表
     */
    @RequiresPermissions("aa:base:export")
    @Log(title = "系统选单-基本设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaBase financeAaBase)
    {
        List<FinanceAaBase> list = financeAaBaseService.selectFinanceAaBaseList(financeAaBase);
        ExcelUtil<FinanceAaBase> util = new ExcelUtil<FinanceAaBase>(FinanceAaBase.class);
        util.exportExcel(response, list, "系统选单-基本设定数据");
    }

    /**
     * 获取系统选单-基本设定详细信息
     */
    @RequiresPermissions("aa:base:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaBaseService.selectFinanceAaBaseById(id));
    }

    /**
     * 新增系统选单-基本设定
     */
    @RequiresPermissions("aa:base:add")
    @Log(title = "系统选单-基本设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaBase financeAaBase)
    {
        return toAjax(financeAaBaseService.insertFinanceAaBase(financeAaBase));
    }

    /**
     * 修改系统选单-基本设定
     */
    @RequiresPermissions("aa:base:edit")
    @Log(title = "系统选单-基本设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaBase financeAaBase)
    {
        return toAjax(financeAaBaseService.updateFinanceAaBase(financeAaBase));
    }

    /**
     * 删除系统选单-基本设定
     */
    @RequiresPermissions("aa:base:remove")
    @Log(title = "系统选单-基本设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{leafId}")
    public AjaxResult remove(@PathVariable String[] leafId)
    {
        return toAjax(financeAaBaseService.deleteFinanceAaBaseByIds(leafId));
    }
}
