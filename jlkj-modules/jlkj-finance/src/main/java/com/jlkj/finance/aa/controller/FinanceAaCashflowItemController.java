package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
import com.jlkj.finance.aa.domain.FinanceAaCashflowItem;
import com.jlkj.finance.aa.service.IFinanceAaCashflowItemService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 现金流量代码-对应会计科目Controller
 *
 * @author 265799
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/item")
public class FinanceAaCashflowItemController extends BaseController
{
    @Autowired
    private IFinanceAaCashflowItemService financeAaCashflowItemService;

    /**
     * 查询现金流量代码-对应会计科目列表
     */
    @RequiresPermissions("aa:item:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCashflowItem financeAaCashflowItem)
    {
        startPage();
        List<FinanceAaCashflowItem> list = financeAaCashflowItemService.selectFinanceAaCashflowItemList(financeAaCashflowItem);
        return getDataTable(list);
    }

    /**
     * 导出现金流量代码-对应会计科目列表
     */
    @RequiresPermissions("aa:item:export")
    @Log(title = "现金流量代码-对应会计科目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCashflowItem financeAaCashflowItem)
    {
        List<FinanceAaCashflowItem> list = financeAaCashflowItemService.selectFinanceAaCashflowItemList(financeAaCashflowItem);
        ExcelUtil<FinanceAaCashflowItem> util = new ExcelUtil<FinanceAaCashflowItem>(FinanceAaCashflowItem.class);
        util.exportExcel(response, list, "现金流量代码-对应会计科目数据");
    }

    /**
     * 获取现金流量代码-对应会计科目详细信息
     */
    @RequiresPermissions("aa:item:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCashflowItemService.selectFinanceAaCashflowItemById(id));
    }

    /**
     * 新增现金流量代码-对应会计科目
     */
    @RequiresPermissions("aa:item:add")
    @Log(title = "现金流量代码-对应会计科目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCashflowItem financeAaCashflowItem)
    {
        List<FinanceAaCashflowItem> codeList = financeAaCashflowItem.getCodeList();
        return toAjax(financeAaCashflowItemService.insertFinanceAaCashflowItem(codeList));
    }

    /**
     * 修改现金流量代码-对应会计科目
     */
    @RequiresPermissions("aa:item:edit")
    @Log(title = "现金流量代码-对应会计科目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCashflowItem financeAaCashflowItem)
    {
        return toAjax(financeAaCashflowItemService.updateFinanceAaCashflowItem(financeAaCashflowItem));
    }

    /**
     * 删除现金流量代码-对应会计科目
     */
    @RequiresPermissions("aa:item:remove")
    @Log(title = "现金流量代码-对应会计科目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCashflowItemService.deleteFinanceAaCashflowItemByIds(ids));
    }
}
