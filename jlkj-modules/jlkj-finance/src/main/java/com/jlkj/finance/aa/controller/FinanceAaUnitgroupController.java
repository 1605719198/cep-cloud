package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaUnitgroup;
import com.jlkj.finance.aa.service.IFinanceAaUnitgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 计量单位分组Controller
 *
 * @author SunXuTong
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/unitGroup")
public class FinanceAaUnitgroupController extends BaseController
{
    @Autowired
    private IFinanceAaUnitgroupService financeAaUnitgroupService;

    /**
     * 查询计量单位分组列表
     */
    @RequiresPermissions("finance:unitGroup:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaUnitgroup financeAaUnitgroup)
    {
        startPage();
        List<FinanceAaUnitgroup> list = financeAaUnitgroupService.selectFinanceAaUnitgroupList(financeAaUnitgroup);
        return getDataTable(list);
    }

    /**
     * 导出计量单位分组列表
     */
    @RequiresPermissions("finance:unitGroup:export")
    @Log(title = "计量单位分组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaUnitgroup financeAaUnitgroup)
    {
        List<FinanceAaUnitgroup> list = financeAaUnitgroupService.selectFinanceAaUnitgroupList(financeAaUnitgroup);
        ExcelUtil<FinanceAaUnitgroup> util = new ExcelUtil<FinanceAaUnitgroup>(FinanceAaUnitgroup.class);
        util.exportExcel(response, list, "计量单位分组数据");
    }

    /**
     * 获取计量单位分组详细信息
     */
    @RequiresPermissions("finance:unitGroup:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaUnitgroupService.selectFinanceAaUnitgroupById(id));
    }

    /**
     * 新增计量单位分组
     */
    @RequiresPermissions("finance:unitGroup:add")
    @Log(title = "计量单位分组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaUnitgroup financeAaUnitgroup)
    {
        return toAjax(financeAaUnitgroupService.insertFinanceAaUnitgroup(financeAaUnitgroup));
    }

    /**
     * 修改计量单位分组
     */
    @RequiresPermissions("finance:unitGroup:edit")
    @Log(title = "计量单位分组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaUnitgroup financeAaUnitgroup)
    {
        return toAjax(financeAaUnitgroupService.updateFinanceAaUnitgroup(financeAaUnitgroup));
    }

    /**
     * 删除计量单位分组
     */
    @RequiresPermissions("finance:unitGroup:remove")
    @Log(title = "计量单位分组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaUnitgroupService.deleteFinanceAaUnitgroupByIds(ids));
    }
}
