package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoItemCompDetail;
import com.jlkj.finance.ao.service.IFinanceAoItemCompDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 报支类别公司设定明细档Controller
 *
 * @author 116514
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/reimbItemCompDetail")
public class FinanceAoItemCompDetailController extends BaseController
{
    @Autowired
    private IFinanceAoItemCompDetailService financeAoItemCompDetailService;

    /**
     * 查询报支类别公司设定明细档列表
     */
    @RequiresPermissions("finance:reimbItemCompDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        startPage();
        List<FinanceAoItemCompDetail> list = financeAoItemCompDetailService.selectFinanceAoItemCompDetailList(financeAoItemCompDetail);
        return getDataTable(list);
    }

    /**
     * 导出报支类别公司设定明细档列表
     */
    @RequiresPermissions("finance:reimbItemCompDetail:export")
    @Log(title = "报支类别公司设定明细档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        List<FinanceAoItemCompDetail> list = financeAoItemCompDetailService.selectFinanceAoItemCompDetailList(financeAoItemCompDetail);
        ExcelUtil<FinanceAoItemCompDetail> util = new ExcelUtil<FinanceAoItemCompDetail>(FinanceAoItemCompDetail.class);
        util.exportExcel(response, list, "报支类别公司设定明细档数据");
    }

    /**
     * 获取报支类别公司设定明细档详细信息
     */
    @RequiresPermissions("finance:reimbItemCompDetail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoItemCompDetailService.selectFinanceAoItemCompDetailById(id));
    }

    /**
     * 新增报支类别公司设定明细档
     */
    @RequiresPermissions("finance:reimbItemCompDetail:add")
    @Log(title = "报支类别公司设定明细档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        return toAjax(financeAoItemCompDetailService.insertFinanceAoItemCompDetail(financeAoItemCompDetail));
    }

    /**
     * 修改报支类别公司设定明细档
     */
    @RequiresPermissions("finance:reimbItemCompDetail:edit")
    @Log(title = "报支类别公司设定明细档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        return toAjax(financeAoItemCompDetailService.updateFinanceAoItemCompDetail(financeAoItemCompDetail));
    }

    /**
     * 删除报支类别公司设定明细档
     */
    @RequiresPermissions("finance:reimbItemCompDetail:remove")
    @Log(title = "报支类别公司设定明细档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoItemCompDetailService.deleteFinanceAoItemCompDetailByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:reimbItemCompDetail:edit")
    @Log(title = "报支管理-状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        return toAjax(financeAoItemCompDetailService.updateFinanceAoItemCompDetail(financeAoItemCompDetail));
    }
/**
*获取报支类别公司设定明细档费用细项
*@author 265799
*@date 2023/7/11  10:27
 * @param financeAoItemCompDetail :
 * @return : java.util.List<java.util.Map<java.lang.String,java.lang.String>>
*/
    @RequiresPermissions("finance:reimbItemComp:query")
    @GetMapping(value = "/selectDetailNoList")
    public List<Map<String,String>> selectItemNoList(FinanceAoItemCompDetail financeAoItemCompDetail)
    {
        return financeAoItemCompDetailService.selectItemNoList(financeAoItemCompDetail);
    }
}
