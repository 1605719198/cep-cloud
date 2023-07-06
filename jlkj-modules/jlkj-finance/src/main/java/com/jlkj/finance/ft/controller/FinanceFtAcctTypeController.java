package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtAcctType;
import com.jlkj.finance.ft.service.IFinanceFtAcctTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账务代码设定Controller
 *
 * @author SunXuTong
 * @date 2023-06-27
 */
@RestController
@RequestMapping("/acctType")
public class FinanceFtAcctTypeController extends BaseController
{
    @Autowired
    private IFinanceFtAcctTypeService financeFtAcctTypeService;

    /**
     * 查询账务代码设定列表
     */
    @RequiresPermissions("finance:acctType:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtAcctType financeFtAcctType)
    {
        startPage();
        List<FinanceFtAcctType> list = financeFtAcctTypeService.selectFinanceFtAcctTypeList(financeFtAcctType);
        return getDataTable(list);
    }

    /**
     * 导出账务代码设定列表
     */
    @RequiresPermissions("finance:acctType:export")
    @Log(title = "账务代码设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtAcctType financeFtAcctType)
    {
        List<FinanceFtAcctType> list = financeFtAcctTypeService.selectFinanceFtAcctTypeList(financeFtAcctType);
        ExcelUtil<FinanceFtAcctType> util = new ExcelUtil<FinanceFtAcctType>(FinanceFtAcctType.class);
        util.exportExcel(response, list, "账务代码设定数据");
    }

    /**
     * 获取账务代码设定详细信息
     */
    @RequiresPermissions("finance:acctType:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtAcctTypeService.selectFinanceFtAcctTypeByUuid(uuid));
    }

    /**
     * 新增账务代码设定
     */
    @RequiresPermissions("finance:acctType:add")
    @Log(title = "账务代码设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtAcctType financeFtAcctType)
    {
        return toAjax(financeFtAcctTypeService.insertFinanceFtAcctType(financeFtAcctType));
    }

    /**
     * 修改账务代码设定
     */
    @RequiresPermissions("finance:acctType:edit")
    @Log(title = "账务代码设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtAcctType financeFtAcctType)
    {
        return toAjax(financeFtAcctTypeService.updateFinanceFtAcctType(financeFtAcctType));
    }

    /**
     * 删除账务代码设定
     */
    @RequiresPermissions("finance:acctType:remove")
    @Log(title = "账务代码设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtAcctTypeService.deleteFinanceFtAcctTypeByUuids(uuids));
    }
}
