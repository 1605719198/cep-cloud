package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaUnit;
import com.jlkj.finance.aa.service.IFinanceAaUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 计量单位维护Controller
 *
 * @author SunXuTong
 * @date 2023-04-27
 */
@RestController
@RequestMapping("/unit")
public class FinanceAaUnitController extends BaseController
{
    @Autowired
    private IFinanceAaUnitService financeAaUnitService;

    /**
     * 查询计量单位维护列表
     */
    @RequiresPermissions("finance:unit:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaUnit financeAaUnit)
    {
        startPage();
        List<FinanceAaUnit> list = financeAaUnitService.selectFinanceAaUnitList(financeAaUnit);
        return getDataTable(list);
    }

    /**
     * 导出计量单位维护列表
     */
    @RequiresPermissions("finance:unit:export")
    @Log(title = "计量单位维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaUnit financeAaUnit)
    {
        List<FinanceAaUnit> list = financeAaUnitService.selectFinanceAaUnitList(financeAaUnit);
        ExcelUtil<FinanceAaUnit> util = new ExcelUtil<FinanceAaUnit>(FinanceAaUnit.class);
        util.exportExcel(response, list, "计量单位维护数据");
    }

    /**
     * 获取计量单位维护详细信息
     */
    @RequiresPermissions("finance:unit:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaUnitService.selectFinanceAaUnitById(id));
    }

    /**
     * 新增计量单位维护
     */
    @RequiresPermissions("finance:unit:add")
    @Log(title = "计量单位维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaUnit financeAaUnit)
    {
        return toAjax(financeAaUnitService.insertFinanceAaUnit(financeAaUnit));
    }

    /**
     * 修改计量单位维护
     */
    @RequiresPermissions("finance:unit:edit")
    @Log(title = "计量单位维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaUnit financeAaUnit)
    {
        return toAjax(financeAaUnitService.updateFinanceAaUnit(financeAaUnit));
    }

    /**
     * 删除计量单位维护
     */
    @RequiresPermissions("finance:unit:remove")
    @Log(title = "计量单位维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaUnitService.deleteFinanceAaUnitByIds(ids));
    }
}
