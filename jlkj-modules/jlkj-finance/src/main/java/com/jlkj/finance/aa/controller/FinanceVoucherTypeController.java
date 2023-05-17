package com.jlkj.finance.aa.controller;

import java.util.List;
import java.util.Map;
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
import com.jlkj.finance.aa.domain.FinanceVoucherType;
import com.jlkj.finance.aa.service.IFinanceVoucherTypeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 凭证类别维护Controller
 *
 * @author 116514
 * @date 2023-04-13
 */
@RestController
@RequestMapping("/voucherType")
public class FinanceVoucherTypeController extends BaseController
{
    @Autowired
    private IFinanceVoucherTypeService financeVoucherTypeService;

    /**
     * 查询凭证类别维护列表
     */
    @RequiresPermissions("finance:voucherType:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceVoucherType financeVoucherType)
    {
        startPage();
        List<FinanceVoucherType> list = financeVoucherTypeService.selectFinanceVoucherTypeList(financeVoucherType);
        return getDataTable(list);
    }

    /**
     * 导出凭证类别维护列表
     */
    @RequiresPermissions("finance:voucherType:export")
    @Log(title = "凭证类别维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceVoucherType financeVoucherType)
    {
        List<FinanceVoucherType> list = financeVoucherTypeService.selectFinanceVoucherTypeList(financeVoucherType);
        ExcelUtil<FinanceVoucherType> util = new ExcelUtil<FinanceVoucherType>(FinanceVoucherType.class);
        util.exportExcel(response, list, "凭证类别维护数据");
    }

    /**
     * 获取凭证类别维护详细信息
     */
    @RequiresPermissions("finance:voucherType:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeVoucherTypeService.selectFinanceVoucherTypeById(id));
    }

    /**
     * 新增凭证类别维护
     */
    @RequiresPermissions("finance:voucherType:add")
    @Log(title = "凭证类别维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceVoucherType financeVoucherType)
    {
        return toAjax(financeVoucherTypeService.insertFinanceVoucherType(financeVoucherType));
    }

    /**
     * 修改凭证类别维护
     */
    @RequiresPermissions("finance:voucherType:edit")
    @Log(title = "凭证类别维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceVoucherType financeVoucherType)
    {
        return toAjax(financeVoucherTypeService.updateFinanceVoucherType(financeVoucherType));
    }

    /**
     * 删除凭证类别维护
     */
    @RequiresPermissions("finance:voucherType:remove")
    @Log(title = "凭证类别维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeVoucherTypeService.deleteFinanceVoucherTypeByIds(ids));
    }

    /**
     * 查询凭证类别列表(下拉选单用)
     */
    @GetMapping("/voucherTypeList")
    public List<Map<String,String>> selectVoucherTypeList()
    {
        return financeVoucherTypeService.selectVoucherTypeList();
    }
}
