package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaUnitChg;
import com.jlkj.finance.aa.service.IFinanceAaUnitChgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 计量单位转换Controller
 *
 * @author SunXuTong
 * @date 2023-05-04
 */
@RestController
@RequestMapping("/chg")
public class FinanceAaUnitChgController extends BaseController
{
    @Autowired
    private IFinanceAaUnitChgService financeAaUnitChgService;

    /**
     * 查询计量单位转换列表
     */
    @RequiresPermissions("finance:chg:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaUnitChg financeAaUnitChg)
    {
        startPage();
        List<FinanceAaUnitChg> list = financeAaUnitChgService.selectFinanceAaUnitChgList(financeAaUnitChg);
        return getDataTable(list);
    }

    /**
     * 导出计量单位转换列表
     */
    @RequiresPermissions("finance:chg:export")
    @Log(title = "计量单位转换", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaUnitChg financeAaUnitChg)
    {
        List<FinanceAaUnitChg> list = financeAaUnitChgService.selectFinanceAaUnitChgList(financeAaUnitChg);
        ExcelUtil<FinanceAaUnitChg> util = new ExcelUtil<FinanceAaUnitChg>(FinanceAaUnitChg.class);
        util.exportExcel(response, list, "计量单位转换数据");
    }

    /**
     * 获取计量单位转换详细信息
     */
    @RequiresPermissions("finance:chg:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaUnitChgService.selectFinanceAaUnitChgById(id));
    }

    /**
     * 新增计量单位转换
     */
    @RequiresPermissions("finance:chg:add")
    @Log(title = "计量单位转换", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaUnitChg financeAaUnitChg)
    {
        return toAjax(financeAaUnitChgService.insertFinanceAaUnitChg(financeAaUnitChg));
    }

    /**
     * 修改计量单位转换
     */
    @RequiresPermissions("finance:chg:edit")
    @Log(title = "计量单位转换", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaUnitChg financeAaUnitChg)
    {
        return toAjax(financeAaUnitChgService.updateFinanceAaUnitChg(financeAaUnitChg));
    }

    /**
     * 删除计量单位转换
     */
    @RequiresPermissions("finance:chg:remove")
    @Log(title = "计量单位转换", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaUnitChgService.deleteFinanceAaUnitChgByIds(ids));
    }
}
