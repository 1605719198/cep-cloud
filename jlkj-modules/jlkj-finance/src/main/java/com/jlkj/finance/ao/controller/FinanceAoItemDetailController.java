package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoItemDetail;
import com.jlkj.finance.ao.service.IFinanceAoItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报支管理-报支类别集团设定明细档Controller
 *
 * @author 116514
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/reimbDetail")
public class FinanceAoItemDetailController extends BaseController
{
    @Autowired
    private IFinanceAoItemDetailService financeAoItemDetailService;

    /**
     * 查询报支管理-报支类别集团设定明细档列表
     */
    @RequiresPermissions("finance:reimbDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoItemDetail financeAoItemDetail)
    {
        startPage();
        List<FinanceAoItemDetail> list = financeAoItemDetailService.selectFinanceAoItemDetailList(financeAoItemDetail);
        return getDataTable(list);
    }

    /**
     * 导出报支管理-报支类别集团设定明细档列表
     */
    @RequiresPermissions("finance:reimbDetail:export")
    @Log(title = "报支管理-报支类别集团设定明细档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoItemDetail financeAoItemDetail)
    {
        List<FinanceAoItemDetail> list = financeAoItemDetailService.selectFinanceAoItemDetailList(financeAoItemDetail);
        ExcelUtil<FinanceAoItemDetail> util = new ExcelUtil<FinanceAoItemDetail>(FinanceAoItemDetail.class);
        util.exportExcel(response, list, "报支管理-报支类别集团设定明细档数据");
    }

    /**
     * 获取报支管理-报支类别集团设定明细档详细信息
     */
    @RequiresPermissions("finance:reimbDetail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoItemDetailService.selectFinanceAoItemDetailById(id));
    }

    /**
     * 新增报支管理-报支类别集团设定明细档
     */
    @RequiresPermissions("finance:reimbDetail:add")
    @Log(title = "报支管理-报支类别集团设定明细档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoItemDetail financeAoItemDetail)
    {
        return toAjax(financeAoItemDetailService.insertFinanceAoItemDetail(financeAoItemDetail));
    }

    /**
     * 修改报支管理-报支类别集团设定明细档
     */
    @RequiresPermissions("finance:reimbDetail:edit")
    @Log(title = "报支管理-报支类别集团设定明细档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoItemDetail financeAoItemDetail)
    {
        return toAjax(financeAoItemDetailService.updateFinanceAoItemDetail(financeAoItemDetail));
    }

    /**
     * 删除报支管理-报支类别集团设定明细档
     */
    @RequiresPermissions("finance:reimbDetail:remove")
    @Log(title = "报支管理-报支类别集团设定明细档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoItemDetailService.deleteFinanceAoItemDetailByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:reimbItem:edit")
    @Log(title = "报支管理-状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAoItemDetail financeAoItemDetail)
    {
        return toAjax(financeAoItemDetailService.updateFinanceAoItemDetail(financeAoItemDetail));
    }
}
