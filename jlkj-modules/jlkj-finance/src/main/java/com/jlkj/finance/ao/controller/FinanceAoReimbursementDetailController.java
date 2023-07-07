package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoReimbursementDetail;
import com.jlkj.finance.ao.service.IFinanceAoReimbursementDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 费用报销明细档Controller
 * @Description:
 * @author 265799
 * @date 2023-06-27
 * @version:V1.0
 */
@RestController
@RequestMapping("/reimbursementDetail")
public class FinanceAoReimbursementDetailController extends BaseController
{
    @Autowired
    private IFinanceAoReimbursementDetailService financeAoReimbursementDetailService;

    /**
     * 查询费用报销明细档列表
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail
     * @return
     */
    @RequiresPermissions("ao:reimbursementDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoReimbursementDetail financeAoReimbursementDetail)
    {
        startPage();
        List<FinanceAoReimbursementDetail> list = financeAoReimbursementDetailService.selectFinanceAoReimbursementDetailList(financeAoReimbursementDetail);
        return getDataTable(list);
    }

    /**
     * 导出费用报销明细档列表
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail
     * @return
     */
    @RequiresPermissions("ao:reimbursementDetail:export")
    @Log(title = "费用报销明细档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoReimbursementDetail financeAoReimbursementDetail)
    {
        List<FinanceAoReimbursementDetail> list = financeAoReimbursementDetailService.selectFinanceAoReimbursementDetailList(financeAoReimbursementDetail);
        ExcelUtil<FinanceAoReimbursementDetail> util = new ExcelUtil<FinanceAoReimbursementDetail>(FinanceAoReimbursementDetail.class);
        util.exportExcel(response, list, "费用报销明细档数据");
    }

    /**
     * 获取费用报销明细档详细信息
     * @author 265799
     * @date 2023-06-27

     * @return
     */
    @RequiresPermissions("ao:reimbursementDetail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoReimbursementDetailService.selectFinanceAoReimbursementDetailById(id));
    }

    /**
     * 新增费用报销明细档
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail
     * @return
     */
    @RequiresPermissions("ao:reimbursementDetail:add")
    @Log(title = "费用报销明细档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoReimbursementDetail financeAoReimbursementDetail)
    {
        return toAjax(financeAoReimbursementDetailService.insertFinanceAoReimbursementDetail(financeAoReimbursementDetail));
    }

    /**
     * 修改费用报销明细档
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursementDetail
     * @return
     */
    @RequiresPermissions("ao:reimbursementDetail:edit")
    @Log(title = "费用报销明细档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoReimbursementDetail financeAoReimbursementDetail)
    {
        return toAjax(financeAoReimbursementDetailService.updateFinanceAoReimbursementDetail(financeAoReimbursementDetail));
    }

    /**
     * 删除费用报销明细档
     * @author 265799
     * @date 2023-06-27

     * @return
     */
    @RequiresPermissions("ao:reimbursementDetail:remove")
    @Log(title = "费用报销明细档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoReimbursementDetailService.deleteFinanceAoReimbursementDetailByIds(ids));
    }
}
