package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaCrcy;
import com.jlkj.finance.aa.service.IFinanceAaCrcyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 币别管理Controller
 *
 * @author SunXuTong
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/crcy")
public class FinanceAaCrcyController extends BaseController
{
    @Autowired
    private IFinanceAaCrcyService financeAaCrcyService;

    /**
     * 查询币别管理列表
     */
    @RequiresPermissions("finance:crcy:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCrcy financeAaCrcy)
    {
        startPage();
        List<FinanceAaCrcy> list = financeAaCrcyService.selectFinanceAaCrcyList(financeAaCrcy);
        return getDataTable(list);
    }

    /**
     * 导出币别管理列表
     */
    @RequiresPermissions("finance:crcy:export")
    @Log(title = "币别管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCrcy financeAaCrcy)
    {
        List<FinanceAaCrcy> list = financeAaCrcyService.selectFinanceAaCrcyList(financeAaCrcy);
        ExcelUtil<FinanceAaCrcy> util = new ExcelUtil<FinanceAaCrcy>(FinanceAaCrcy.class);
        util.exportExcel(response, list, "币别管理数据");
    }

    /**
     * 获取币别管理详细信息
     */
    @RequiresPermissions("finance:crcy:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCrcyService.selectFinanceAaCrcyById(id));
    }

    /**
     * 新增币别管理
     */
    @RequiresPermissions("finance:crcy:add")
    @Log(title = "币别管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCrcy financeAaCrcy)
    {
        return toAjax(financeAaCrcyService.insertFinanceAaCrcy(financeAaCrcy));
    }

    /**
     * 修改币别管理
     */
    @RequiresPermissions("finance:crcy:edit")
    @Log(title = "币别管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCrcy financeAaCrcy)
    {
        return toAjax(financeAaCrcyService.updateFinanceAaCrcy(financeAaCrcy));
    }

    /**
     * 删除币别管理
     */
    @RequiresPermissions("finance:crcy:remove")
    @Log(title = "币别管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCrcyService.deleteFinanceAaCrcyByIds(ids));
    }
}
