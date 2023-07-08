package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoReimbursement;
import com.jlkj.finance.ao.service.IFinanceAoReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 费用报销主档Controller
 * @Description:
 * @author 265799
 * @date 2023-06-27
 * @version:V1.0
 */
@RestController
@RequestMapping("/reimbursement")
public class FinanceAoReimbursementController extends BaseController
{
    @Autowired
    private IFinanceAoReimbursementService financeAoReimbursementService;

    /**
     * 查询费用报销主档列表
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursement
     * @return
     */
    @RequiresPermissions("ao:reimbursement:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoReimbursement financeAoReimbursement)
    {
        startPage();
        List<FinanceAoReimbursement> list = financeAoReimbursementService.selectFinanceAoReimbursementList(financeAoReimbursement);
        return getDataTable(list);
    }

    /**
     * 导出费用报销主档列表
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursement
     * @return
     */
    @RequiresPermissions("ao:reimbursement:export")
    @Log(title = "费用报销主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoReimbursement financeAoReimbursement)
    {
        List<FinanceAoReimbursement> list = financeAoReimbursementService.selectFinanceAoReimbursementList(financeAoReimbursement);
        ExcelUtil<FinanceAoReimbursement> util = new ExcelUtil<FinanceAoReimbursement>(FinanceAoReimbursement.class);
        util.exportExcel(response, list, "费用报销主档数据");
    }

    /**
     * 获取费用报销主档详细信息
     * @author 265799
     * @date 2023-06-27

     * @return
     */
    @RequiresPermissions("ao:reimbursement:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoReimbursementService.selectFinanceAoReimbursementById(id));
    }

    /**
     * 新增费用报销主档
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursement
     * @return
     */
    @RequiresPermissions("ao:reimbursement:add")
    @Log(title = "费用报销主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoReimbursement financeAoReimbursement)
    {
        return toAjax(financeAoReimbursementService.insertFinanceAoReimbursement(financeAoReimbursement));
    }

    /**
     * 修改费用报销主档
     * @author 265799
     * @date 2023-06-27
     * @param financeAoReimbursement
     * @return
     */
    @RequiresPermissions("ao:reimbursement:edit")
    @Log(title = "费用报销主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoReimbursement financeAoReimbursement)
    {
        return toAjax(financeAoReimbursementService.updateFinanceAoReimbursement(financeAoReimbursement));
    }

    /**
     * 删除费用报销主档
     * @author 265799
     * @date 2023-06-27

     * @return
     */
    @RequiresPermissions("ao:reimbursement:remove")
    @Log(title = "费用报销主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoReimbursementService.deleteFinanceAoReimbursementByIds(ids));
    }
}
