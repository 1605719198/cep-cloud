package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApItem;
import com.jlkj.finance.ap.service.IFinanceApItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报支类别集团设置Controller
 *
 * @author SunXuTong
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/apItem")
public class FinanceApItemController extends BaseController
{
    @Autowired
    private IFinanceApItemService financeApItemService;

    /**
     * 查询报支类别集团设置列表
     */
    @RequiresPermissions("finance:item:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApItem financeApItem)
    {
        startPage();
        List<FinanceApItem> list = financeApItemService.selectFinanceApItemList(financeApItem);
        return getDataTable(list);
    }

    /**
     * 导出报支类别集团设置列表
     */
    @RequiresPermissions("finance:item:export")
    @Log(title = "报支类别集团设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApItem financeApItem)
    {
        List<FinanceApItem> list = financeApItemService.selectFinanceApItemList(financeApItem);
        ExcelUtil<FinanceApItem> util = new ExcelUtil<FinanceApItem>(FinanceApItem.class);
        util.exportExcel(response, list, "报支类别集团设置数据");
    }

    /**
     * 获取报支类别集团设置详细信息
     */
    @RequiresPermissions("finance:item:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApItemService.selectFinanceApItemById(id));
    }

    /**
     * 新增报支类别集团设置
     */
    @RequiresPermissions("finance:item:add")
    @Log(title = "报支类别集团设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApItem financeApItem)
    {
        return toAjax(financeApItemService.insertFinanceApItem(financeApItem));
    }

    /**
     * 修改报支类别集团设置
     */
    @RequiresPermissions("finance:item:edit")
    @Log(title = "报支类别集团设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApItem financeApItem)
    {
        return toAjax(financeApItemService.updateFinanceApItem(financeApItem));
    }

    /**
     * 删除报支类别集团设置
     */
    @RequiresPermissions("finance:item:remove")
    @Log(title = "报支类别集团设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApItemService.deleteFinanceApItemByIds(ids));
    }
}
