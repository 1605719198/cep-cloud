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
import com.jlkj.finance.aa.domain.FinanceAaVoucher;
import com.jlkj.finance.aa.service.IFinanceAaVoucherService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 凭证维护-主Controller
 *
 * @author 265799
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/voucher")
public class FinanceAaVoucherController extends BaseController
{
    @Autowired
    private IFinanceAaVoucherService financeAaVoucherService;

    /**
     * 查询凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaVoucher financeAaVoucher)
    {
        startPage();
        List<FinanceAaVoucher> list = financeAaVoucherService.selectFinanceAaVoucherList(financeAaVoucher);
        return getDataTable(list);
    }
    /**
     * 查询凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/listVoucherDetailSelect")
    public TableDataInfo listVoucherDetailSelect(FinanceAaVoucher financeAaVoucher)
    {
        startPage();

        List<FinanceAaVoucher> list = financeAaVoucherService.selectFinanceAaVoucherDetailList(financeAaVoucher);
        return getDataTable(list);
    }
    /**
     * 查询凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/listVoucher")
    public AjaxResult listVoucher(FinanceAaVoucher financeAaVoucher)
    {
        return success(financeAaVoucherService.selectFinanceAaVoucher(financeAaVoucher));
    }
    /**
     * 次笔查询凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/listOrderVoucher")
    public AjaxResult listOrderVoucher(FinanceAaVoucher financeAaVoucher)
    {
        return success(financeAaVoucherService.selectFinanceAaVoucherOrder(financeAaVoucher));
    }
    /**
     * 前笔查询凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/listFrontVoucher")
    public AjaxResult listFrontVoucher(FinanceAaVoucher financeAaVoucher)
    {
        return success(financeAaVoucherService.selectFinanceAaVoucherFront(financeAaVoucher));
    }
    /**
     * 末笔按钮操作-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/listLastVoucher")
    public AjaxResult listLastVoucher(FinanceAaVoucher financeAaVoucher)
    {
        return success(financeAaVoucherService.selectFinanceAaVoucherLast(financeAaVoucher));
    }
    /**
     * 获取凭证维护-主详细信息
     */
    @RequiresPermissions("aa:voucher:query")
    @GetMapping(value = "/listHeadVoucher")
    public AjaxResult listHeadVoucher(FinanceAaVoucher financeAaVoucher)
    {
        return success(financeAaVoucherService.selectFinanceAaVoucherListHeadVoucher(financeAaVoucher));
    }
    /**
     * 导出凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:export")
    @Log(title = "凭证维护-主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaVoucher financeAaVoucher)
    {
        List<FinanceAaVoucher> list = financeAaVoucherService.selectFinanceAaVoucherDetailList(financeAaVoucher);
        ExcelUtil<FinanceAaVoucher> util = new ExcelUtil<FinanceAaVoucher>(FinanceAaVoucher.class);
        util.exportExcel(response, list, "凭证维护-主数据");
    }

    /**
     * 获取凭证维护-主详细信息
     */
    @RequiresPermissions("aa:voucher:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaVoucherService.selectFinanceAaVoucherById(id));
    }

    /**
     * 新增凭证维护-主
     */
    @RequiresPermissions("aa:voucher:add")
    @Log(title = "凭证维护-主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setCreateBy(getUsername());
        return toAjax(financeAaVoucherService.insertFinanceAaVoucher(financeAaVoucher));
    }

    /**
     * 修改凭证维护-主
     */
    @RequiresPermissions("aa:voucher:edit")
    @Log(title = "凭证维护-主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateBy(getUsername());
        return toAjax(financeAaVoucherService.updateFinanceAaVoucher(financeAaVoucher));
    }
    /**
     * 确认和取消确认凭证维护-主
     */
    @RequiresPermissions("aa:voucher:edit")
    @Log(title = "凭证维护-主", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult editStatus(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateBy(getUsername());
        return toAjax(financeAaVoucherService.updateFinanceAaVoucherStatus(financeAaVoucher));
    }
    /**
     * 过账凭证维护-主
     */
    @RequiresPermissions("aa:voucher:edit")
    @Log(title = "凭证维护-主", businessType = BusinessType.UPDATE)
    @PutMapping("/cross")
    public AjaxResult editCross(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateBy(getUsername());
        return toAjax(financeAaVoucherService.updateFinanceAaVoucherCross(financeAaVoucher));
    }
    /**
     * 删除凭证维护-主
     */
    @RequiresPermissions("aa:voucher:remove")
    @Log(title = "凭证维护-主", businessType = BusinessType.DELETE)
	@DeleteMapping
    public AjaxResult remove(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        return toAjax(financeAaVoucherService.deleteFinanceAaVoucherByIds(financeAaVoucher));
    }
}
