package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaVoucher;
import com.jlkj.finance.aa.dto.FinanceAaVoucherDTO;
import com.jlkj.finance.aa.service.IFinanceAaVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * 查询凭证维护-主列表(不去重)
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/listVoucherLinkSelect")
    public TableDataInfo listVoucherLinkSelect(FinanceAaVoucher financeAaVoucher)
    {
        startPage();

        List<FinanceAaVoucher> list = financeAaVoucherService.selectFinanceAaVoucherLinkList(financeAaVoucher);
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
     * 首笔笔查询凭证维护-主列表
     */
    @RequiresPermissions("aa:voucher:list")
    @GetMapping("/listFrontVoucherHead")
    public AjaxResult listFrontVoucherHead(FinanceAaVoucher financeAaVoucher)
    {
        return success(financeAaVoucherService.selectFinanceAaVoucherHead(financeAaVoucher));
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
        return success(financeAaVoucherService.insertFinanceAaVoucher(financeAaVoucher));
    }
    /**
     * 新增凭证维护-主
     */
    @RequiresPermissions("aa:voucher:add")
    @Log(title = "凭证维护-主", businessType = BusinessType.INSERT)
    @PostMapping("/addVoucher")
    public AjaxResult addVoucher(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setCreateBy(getUsername());
        return success(financeAaVoucherService.insertFinanceAaVoucherSubmitForm(financeAaVoucher));
    }
    /**
     * 红冲凭证维护-主
     */
    @RequiresPermissions("aa:voucher:add")
    @Log(title = "凭证维护-主", businessType = BusinessType.INSERT)
    @PostMapping("/addHongChongVoucher")
    public AjaxResult addHongChongVoucher(@RequestBody FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setCreateBy(getUsername());
        return success(financeAaVoucherService.insertFinanceAaVoucherHongChong(financeAaVoucher));
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
        return success(financeAaVoucherService.updateFinanceAaVoucher(financeAaVoucher));
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

    /**
     * 导入凭证
     */
    @Log(title = "导入凭证", businessType = BusinessType.IMPORT)
    @RequiresPermissions("aa:voucher:import")
    @PostMapping("/importData/{companyId}")
    public AjaxResult importData(MultipartFile file, boolean updateSupport,@PathVariable String companyId) throws Exception
    {
        ExcelUtil<FinanceAaVoucherDTO> util = new ExcelUtil<FinanceAaVoucherDTO>(FinanceAaVoucherDTO.class);
        List<FinanceAaVoucherDTO> financeAaVoucher = util.importExcel(file.getInputStream());
        String operName = getUsername();
        List<FinanceAaVoucherDTO> financeAaVoucherDTOS = financeAaVoucherService.importFinanceAaVoucher(financeAaVoucher, updateSupport, operName,  companyId);
        return AjaxResult.success(financeAaVoucherDTOS);
    }
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<FinanceAaVoucherDTO> util = new ExcelUtil<FinanceAaVoucherDTO>(FinanceAaVoucherDTO.class);
        util.importTemplateExcel(response, "凭证信息表");
    }
}
