package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaRate;
import com.jlkj.finance.aa.service.IFinanceAaRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 利率汇率管理Controller
 *
 * @author SunXuTong
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/rate")
public class FinanceAaRateController extends BaseController
{
    @Autowired
    private IFinanceAaRateService financeAaRateService;

    /**
     * 查询利率汇率管理列表
     */
    @RequiresPermissions("finance:rate:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaRate financeAaRate)
    {
        startPage();
        List<FinanceAaRate> list = financeAaRateService.selectFinanceAaRateList(financeAaRate);
        return getDataTable(list);
    }
    /**
     * 获取利率汇率管理详细信息
     */
    @RequiresPermissions("finance:rate:query")
    @GetMapping(value = "/listCrcyUnit")
    public AjaxResult getInfo(FinanceAaRate financeAaRate)
    {
        return success(financeAaRateService.selectFinanceAaRateRateValue(financeAaRate));
    }
    /**
     * 导出利率汇率管理列表
     */
    @RequiresPermissions("finance:rate:export")
    @Log(title = "利率汇率管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaRate financeAaRate)
    {
        List<FinanceAaRate> list = financeAaRateService.selectFinanceAaRateList(financeAaRate);
        ExcelUtil<FinanceAaRate> util = new ExcelUtil<FinanceAaRate>(FinanceAaRate.class);
        util.exportExcel(response, list, "利率汇率管理数据");
    }

    /**
     * 获取利率汇率管理详细信息
     */
    @RequiresPermissions("finance:rate:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaRateService.selectFinanceAaRateById(id));
    }

    /**
     * 新增利率汇率管理
     */
    @RequiresPermissions("finance:rate:add")
    @Log(title = "利率汇率管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaRate financeAaRate)
    {
        return toAjax(financeAaRateService.insertFinanceAaRate(financeAaRate));
    }

    /**
     * 修改利率汇率管理
     */
    @RequiresPermissions("finance:rate:edit")
    @Log(title = "利率汇率管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaRate financeAaRate)
    {
        return toAjax(financeAaRateService.updateFinanceAaRate(financeAaRate));
    }

    /**
     * 删除利率汇率管理
     */
    @RequiresPermissions("finance:rate:remove")
    @Log(title = "利率汇率管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaRateService.deleteFinanceAaRateByIds(ids));
    }
}
