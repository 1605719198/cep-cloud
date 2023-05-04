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
import com.jlkj.finance.aa.domain.FinanceAaVoucherDetail;
import com.jlkj.finance.aa.service.IFinanceAaVoucherDetailService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 凭证维护-明细Controller
 *
 * @author 265799
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/aa/detail")
public class FinanceAaVoucherDetailController extends BaseController
{
    @Autowired
    private IFinanceAaVoucherDetailService financeAaVoucherDetailService;

    /**
     * 查询凭证维护-明细列表
     */
    @RequiresPermissions("aa:detail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        startPage();
        List<FinanceAaVoucherDetail> list = financeAaVoucherDetailService.selectFinanceAaVoucherDetailList(financeAaVoucherDetail);
        return getDataTable(list);
    }
    /**
     * 查询凭证维护-明细列表
     */
    @RequiresPermissions("aa:detail:list")
    @GetMapping("/listVoucherSelect")
    public TableDataInfo listVoucherSelect(FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        startPage();
        List<FinanceAaVoucherDetail> list = financeAaVoucherDetailService.selectFinanceVoucherList(financeAaVoucherDetail);
        return getDataTable(list);
    }

    /**
     * 导出凭证维护-明细列表
     */
    @RequiresPermissions("aa:detail:export")
    @Log(title = "凭证维护-明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        List<FinanceAaVoucherDetail> list = financeAaVoucherDetailService.selectFinanceAaVoucherDetailList(financeAaVoucherDetail);
        ExcelUtil<FinanceAaVoucherDetail> util = new ExcelUtil<FinanceAaVoucherDetail>(FinanceAaVoucherDetail.class);
        util.exportExcel(response, list, "凭证维护-明细数据");
    }

    /**
     * 获取凭证维护-明细详细信息
     */
    @RequiresPermissions("aa:detail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaVoucherDetailService.selectFinanceAaVoucherDetailById(id));
    }

    /**
     * 新增凭证维护-明细
     */
    @RequiresPermissions("aa:detail:add")
    @Log(title = "凭证维护-明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        return toAjax(financeAaVoucherDetailService.insertFinanceAaVoucherDetail(financeAaVoucherDetail));
    }

    /**
     * 修改凭证维护-明细
     */
    @RequiresPermissions("aa:detail:edit")
    @Log(title = "凭证维护-明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaVoucherDetail financeAaVoucherDetail)
    {
        return toAjax(financeAaVoucherDetailService.updateFinanceAaVoucherDetail(financeAaVoucherDetail));
    }

    /**
     * 删除凭证维护-明细
     */
    @RequiresPermissions("aa:detail:remove")
    @Log(title = "凭证维护-明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaVoucherDetailService.deleteFinanceAaVoucherDetailByIds(ids));
    }
}
