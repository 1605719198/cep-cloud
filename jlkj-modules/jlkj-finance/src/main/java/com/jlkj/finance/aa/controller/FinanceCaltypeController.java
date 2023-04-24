package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
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
import com.jlkj.finance.aa.domain.FinanceCaltype;
import com.jlkj.finance.aa.service.IFinanceCaltypeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 核算项目-类别维护Controller
 *
 * @author 116514
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/calType")
public class FinanceCaltypeController extends BaseController
{
    @Autowired
    private IFinanceCaltypeService financeCaltypeService;

    /**
     * 查询核算项目-类别维护列表
     */
    @RequiresPermissions("finance:calType:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceCaltype financeCaltype)
    {
        startPage();
        List<FinanceCaltype> list = financeCaltypeService.selectFinanceCaltypeList(financeCaltype);
        return getDataTable(list);
    }
    /**
     * 查询核算项目类别(下拉选单用)
     */
    @RequiresPermissions("finance:calType:list")
    @GetMapping("/calTypeList")
    public List<Map<String,String>> selectCalTypeList()
    {
        return financeCaltypeService.selectCalTypeList();
    }

    /**
     * 导出核算项目-类别维护列表
     */
    @RequiresPermissions("finance:calType:export")
    @Log(title = "核算项目-类别维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceCaltype financeCaltype)
    {
        List<FinanceCaltype> list = financeCaltypeService.selectFinanceCaltypeList(financeCaltype);
        ExcelUtil<FinanceCaltype> util = new ExcelUtil<FinanceCaltype>(FinanceCaltype.class);
        util.exportExcel(response, list, "核算项目-类别维护数据");
    }

    /**
     * 获取核算项目-类别维护详细信息
     */
    @RequiresPermissions("finance:calType:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeCaltypeService.selectFinanceCaltypeById(id));
    }

    /**
     * 新增核算项目-类别维护
     */
    @RequiresPermissions("finance:calType:add")
    @Log(title = "核算项目-类别维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceCaltype financeCaltype)
    {
        return toAjax(financeCaltypeService.insertFinanceCaltype(financeCaltype));
    }

    /**
     * 修改核算项目-类别维护
     */
    @RequiresPermissions("finance:calType:edit")
    @Log(title = "核算项目-类别维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceCaltype financeCaltype)
    {
        return toAjax(financeCaltypeService.updateFinanceCaltype(financeCaltype));
    }

    /**
     * 删除核算项目-类别维护
     */
    @RequiresPermissions("finance:calType:remove")
    @Log(title = "核算项目-类别维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeCaltypeService.deleteFinanceCaltypeByIds(ids));
    }
}
