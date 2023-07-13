package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApLoanDetail;
import com.jlkj.finance.ap.service.IFinanceApLoanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 预付申请明细档Controller
 * @Description:
 * @author 265799
 * @date 2023-07-10
 * @version:V1.0
 */
@RestController
@RequestMapping("ap/loanDetail")
public class FinanceApLoanDetailController extends BaseController
{
    @Autowired
    private IFinanceApLoanDetailService financeApLoanDetailService;

    /**
     * 查询预付申请明细档列表
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail
     * @return
     */
    @RequiresPermissions("ap:detail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApLoanDetail financeApLoanDetail)
    {
        startPage();
        List<FinanceApLoanDetail> list = financeApLoanDetailService.selectFinanceApLoanDetailList(financeApLoanDetail);
        return getDataTable(list);
    }

    /**
     * 导出预付申请明细档列表
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail
     * @return
     */
    @RequiresPermissions("ap:detail:export")
    @Log(title = "预付申请明细档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApLoanDetail financeApLoanDetail)
    {
        List<FinanceApLoanDetail> list = financeApLoanDetailService.selectFinanceApLoanDetailList(financeApLoanDetail);
        ExcelUtil<FinanceApLoanDetail> util = new ExcelUtil<FinanceApLoanDetail>(FinanceApLoanDetail.class);
        util.exportExcel(response, list, "预付申请明细档数据");
    }

    /**
     * 获取预付申请明细档详细信息
     * @author 265799
     * @date 2023-07-10
     * @param id
     * @return
     */
    @RequiresPermissions("ap:detail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApLoanDetailService.selectFinanceApLoanDetailById(id));
    }

    /**
     * 新增预付申请明细档
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail
     * @return
     */
    @RequiresPermissions("ap:detail:add")
    @Log(title = "预付申请明细档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApLoanDetail financeApLoanDetail)
    {
        return toAjax(financeApLoanDetailService.insertFinanceApLoanDetail(financeApLoanDetail));
    }

    /**
     * 修改预付申请明细档
     * @author 265799
     * @date 2023-07-10
     * @param financeApLoanDetail
     * @return
     */
    @RequiresPermissions("ap:detail:edit")
    @Log(title = "预付申请明细档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApLoanDetail financeApLoanDetail)
    {
        return toAjax(financeApLoanDetailService.updateFinanceApLoanDetail(financeApLoanDetail));
    }

    /**
     * 删除预付申请明细档
     * @author 265799
     * @date 2023-07-10
     * @param ids
     * @return
     */
    @RequiresPermissions("ap:detail:remove")
    @Log(title = "预付申请明细档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApLoanDetailService.deleteFinanceApLoanDetailByIds(ids));
    }
}
