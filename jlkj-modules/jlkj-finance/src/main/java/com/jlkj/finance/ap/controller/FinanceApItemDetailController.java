package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApItemDetail;
import com.jlkj.finance.ap.service.IFinanceApItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报支类别集团细项Controller
 *
 * @author SunXuTong
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/apDetail")
public class FinanceApItemDetailController extends BaseController
{
    @Autowired
    private IFinanceApItemDetailService financeApItemDetailService;

    /**
     * 查询报支类别集团细项列表
     */
    @RequiresPermissions("finance:detail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApItemDetail financeApItemDetail)
    {
        startPage();
        List<FinanceApItemDetail> list = financeApItemDetailService.selectFinanceApItemDetailList(financeApItemDetail);
        return getDataTable(list);
    }

    /**
     * 导出报支类别集团细项列表
     */
    @RequiresPermissions("finance:detail:export")
    @Log(title = "报支类别集团细项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApItemDetail financeApItemDetail)
    {
        List<FinanceApItemDetail> list = financeApItemDetailService.selectFinanceApItemDetailList(financeApItemDetail);
        ExcelUtil<FinanceApItemDetail> util = new ExcelUtil<FinanceApItemDetail>(FinanceApItemDetail.class);
        util.exportExcel(response, list, "报支类别集团细项数据");
    }

    /**
     * 获取报支类别集团细项详细信息
     */
    @RequiresPermissions("finance:detail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApItemDetailService.selectFinanceApItemDetailById(id));
    }

    /**
     * 新增报支类别集团细项
     */
    @RequiresPermissions("finance:detail:add")
    @Log(title = "报支类别集团细项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApItemDetail financeApItemDetail)
    {
        return toAjax(financeApItemDetailService.insertFinanceApItemDetail(financeApItemDetail));
    }

    /**
     * 修改报支类别集团细项
     */
    @RequiresPermissions("finance:detail:edit")
    @Log(title = "报支类别集团细项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApItemDetail financeApItemDetail)
    {
        return toAjax(financeApItemDetailService.updateFinanceApItemDetail(financeApItemDetail));
    }

    /**
     * 删除报支类别集团细项
     */
    @RequiresPermissions("finance:detail:remove")
    @Log(title = "报支类别集团细项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApItemDetailService.deleteFinanceApItemDetailByIds(ids));
    }
}
