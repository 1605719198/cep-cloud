package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoItem;
import com.jlkj.finance.ao.service.IFinanceAoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 报支管理-报支类别集团设定主档Controller
 *
 * @author 116514
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/reimbItem")
public class FinanceAoItemController extends BaseController
{
    @Autowired
    private IFinanceAoItemService financeAoItemService;

    /**
     * 查询报支管理-报支类别集团设定主档列表
     */
    @RequiresPermissions("finance:reimbItem:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoItem financeAoItem)
    {
        startPage();
        List<FinanceAoItem> list = financeAoItemService.selectFinanceAoItemList(financeAoItem);
        return getDataTable(list);
    }

    /**
     * 导出报支管理-报支类别集团设定主档列表
     */
    @RequiresPermissions("finance:reimbItem:export")
    @Log(title = "报支管理-报支类别集团设定主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoItem financeAoItem)
    {
        List<FinanceAoItem> list = financeAoItemService.selectFinanceAoItemList(financeAoItem);
        ExcelUtil<FinanceAoItem> util = new ExcelUtil<FinanceAoItem>(FinanceAoItem.class);
        util.exportExcel(response, list, "报支管理-报支类别集团设定主档数据");
    }

    /**
     * 获取报支管理-报支类别集团设定主档详细信息
     */
    @RequiresPermissions("finance:reimbItem:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoItemService.selectFinanceAoItemById(id));
    }

    /**
     * 新增报支管理-报支类别集团设定主档
     */
    @RequiresPermissions("finance:reimbItem:add")
    @Log(title = "报支管理-报支类别集团设定主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoItem financeAoItem)
    {
        return toAjax(financeAoItemService.insertFinanceAoItem(financeAoItem));
    }

    /**
     * 修改报支管理-报支类别集团设定主档
     */
    @RequiresPermissions("finance:reimbItem:edit")
    @Log(title = "报支管理-报支类别集团设定主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoItem financeAoItem)
    {
        return toAjax(financeAoItemService.updateFinanceAoItem(financeAoItem));
    }

    /**
     * 删除报支管理-报支类别集团设定主档
     */
    @RequiresPermissions("finance:reimbItem:remove")
    @Log(title = "报支管理-报支类别集团设定主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoItemService.deleteFinanceAoItemByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:reimbItem:edit")
    @Log(title = "报支管理-状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAoItem financeAoItem)
    {
        return toAjax(financeAoItemService.updateFinanceAoItem(financeAoItem));
    }


    /**
     * 选取集团报支类别(公司级报支类别用)
     * @param companyId
     * @return
     */
    @RequiresPermissions("finance:reimbItem:list")
    @GetMapping("/itemList/{companyId}")
    public TableDataInfo selectItemAndDetailList(@PathVariable("companyId") String companyId)
    {
        startPage();
        List<Map<String, Object>> list = financeAoItemService.selectItemAndDetailList(companyId);
        return getDataTable(list);
    }


}
