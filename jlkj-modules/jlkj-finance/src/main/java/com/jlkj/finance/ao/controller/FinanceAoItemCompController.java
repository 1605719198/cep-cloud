package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoItemComp;
import com.jlkj.finance.ao.service.IFinanceAoItemCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报支类别-公司级-设定主档Controller
 *
 * @author 116514
 * @date 2023-05-23
 */
@RestController
@RequestMapping("/reimbItemComp")
public class FinanceAoItemCompController extends BaseController
{
    @Autowired
    private IFinanceAoItemCompService financeAoItemCompService;

    /**
     * 查询报支类别-公司级-设定主档列表
     */
    @RequiresPermissions("finance:reimbItemComp:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoItemComp financeAoItemComp)
    {
        startPage();
        List<FinanceAoItemComp> list = financeAoItemCompService.selectFinanceAoItemCompList(financeAoItemComp);
        return getDataTable(list);
    }

    /**
     * 导出报支类别-公司级-设定主档列表
     */
    @RequiresPermissions("finance:reimbItemComp:export")
    @Log(title = "报支类别-公司级-设定主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoItemComp financeAoItemComp)
    {
        List<FinanceAoItemComp> list = financeAoItemCompService.selectFinanceAoItemCompList(financeAoItemComp);
        ExcelUtil<FinanceAoItemComp> util = new ExcelUtil<FinanceAoItemComp>(FinanceAoItemComp.class);
        util.exportExcel(response, list, "报支类别-公司级-设定主档数据");
    }

    /**
     * 获取报支类别-公司级-设定主档详细信息
     */
    @RequiresPermissions("finance:reimbItemComp:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoItemCompService.selectFinanceAoItemCompById(id));
    }

    /**
     * 新增报支类别-公司级-设定主档
     */
    @RequiresPermissions("finance:reimbItemComp:add")
    @Log(title = "报支类别-公司级-设定主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoItemComp financeAoItemComp)
    {
        return toAjax(financeAoItemCompService.insertFinanceAoItemComp(financeAoItemComp));
    }

    /**
     * 修改报支类别-公司级-设定主档
     */
    @RequiresPermissions("finance:reimbItemComp:edit")
    @Log(title = "报支类别-公司级-设定主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoItemComp financeAoItemComp)
    {
        return toAjax(financeAoItemCompService.updateFinanceAoItemComp(financeAoItemComp));
    }

    /**
     * 删除报支类别-公司级-设定主档
     */
    @RequiresPermissions("finance:reimbItemComp:remove")
    @Log(title = "报支类别-公司级-设定主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoItemCompService.deleteFinanceAoItemCompByIds(ids));
    }


    /**
     * 新增会计科目-公司级
     */
    @RequiresPermissions("finance:reimbItemComp:add")
    @Log(title = "报支类别-公司级批量新增", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult addBatch(@RequestBody List<FinanceAoItemComp> financeAoItemComps)
    {
        return toAjax(financeAoItemCompService.insertBatch(financeAoItemComps));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:reimbItemComp:edit")
    @Log(title = "状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAoItemComp financeAoItemComp)
    {
        return toAjax(financeAoItemCompService.updateFinanceAoItemComp(financeAoItemComp));
    }
}
