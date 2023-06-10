package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApItemComp;
import com.jlkj.finance.ap.service.IFinanceApItemCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报支类别公司设置Controller
 *
 * @author SunXuTong
 * @date 2023-06-06
 */
@RestController
@RequestMapping("/itemComp")
public class FinanceApItemCompController extends BaseController
{
    @Autowired
    private IFinanceApItemCompService financeApItemCompService;

    /**
     * 查询报支类别公司设置列表
     */
    @RequiresPermissions("finance:itemComp:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApItemComp financeApItemComp)
    {
        startPage();
        List<FinanceApItemComp> list = financeApItemCompService.selectFinanceApItemCompList(financeApItemComp);
        return getDataTable(list);
    }

    /**
     * 导出报支类别公司设置列表
     */
    @RequiresPermissions("finance:itemComp:export")
    @Log(title = "报支类别公司设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApItemComp financeApItemComp)
    {
        List<FinanceApItemComp> list = financeApItemCompService.selectFinanceApItemCompList(financeApItemComp);
        ExcelUtil<FinanceApItemComp> util = new ExcelUtil<FinanceApItemComp>(FinanceApItemComp.class);
        util.exportExcel(response, list, "报支类别公司设置数据");
    }

    /**
     * 获取报支类别公司设置详细信息
     */
    @RequiresPermissions("finance:itemComp:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApItemCompService.selectFinanceApItemCompById(id));
    }

    /**
     * 新增报支类别公司设置
     */
    @RequiresPermissions("finance:itemComp:add")
    @Log(title = "报支类别公司设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApItemComp financeApItemComp)
    {
        return toAjax(financeApItemCompService.insertFinanceApItemComp(financeApItemComp));
    }

    /**
     * 修改报支类别公司设置
     */
    @RequiresPermissions("finance:itemComp:edit")
    @Log(title = "报支类别公司设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApItemComp financeApItemComp)
    {
        return toAjax(financeApItemCompService.updateFinanceApItemComp(financeApItemComp));
    }

    /**
     * 删除报支类别公司设置
     */
    @RequiresPermissions("finance:itemComp:remove")
    @Log(title = "报支类别公司设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApItemCompService.deleteFinanceApItemCompByIds(ids));
    }

    /**
     * 新增会计科目-公司级
     */
    @RequiresPermissions("finance:reimbItemComp:add")
    @Log(title = "报支类别-公司级批量新增", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult addBatch(@RequestBody List<FinanceApItemComp> financeApItemComps)
    {
        return toAjax(financeApItemCompService.insertBatch(financeApItemComps));
    }

}
