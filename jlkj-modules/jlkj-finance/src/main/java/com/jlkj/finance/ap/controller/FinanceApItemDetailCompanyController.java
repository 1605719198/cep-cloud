package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApItemDetailCompany;
import com.jlkj.finance.ap.service.IFinanceApItemDetailCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 报支类别公司细项Controller
 *
 * @author SunXuTong
 * @date 2023-06-08
 */
@RestController
@RequestMapping("/itemDetailCompany")
public class FinanceApItemDetailCompanyController extends BaseController
{
    @Autowired
    private IFinanceApItemDetailCompanyService financeApItemDetailCompanyService;

    /**
     * 查询报支类别公司细项列表
     */
    @RequiresPermissions("finance:itemDetailCompany:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        startPage();
        List<FinanceApItemDetailCompany> list = financeApItemDetailCompanyService.selectFinanceApItemDetailCompanyList(financeApItemDetailCompany);
        return getDataTable(list);
    }

    /**
     * 导出报支类别公司细项列表
     */
    @RequiresPermissions("finance:itemDetailCompany:export")
    @Log(title = "报支类别公司细项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        List<FinanceApItemDetailCompany> list = financeApItemDetailCompanyService.selectFinanceApItemDetailCompanyList(financeApItemDetailCompany);
        ExcelUtil<FinanceApItemDetailCompany> util = new ExcelUtil<FinanceApItemDetailCompany>(FinanceApItemDetailCompany.class);
        util.exportExcel(response, list, "报支类别公司细项数据");
    }

    /**
     * 获取报支类别公司细项详细信息
     */
    @RequiresPermissions("finance:itemDetailCompany:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApItemDetailCompanyService.selectFinanceApItemDetailCompanyById(id));
    }

    /**
     * 新增报支类别公司细项
     */
    @RequiresPermissions("finance:itemDetailCompany:add")
    @Log(title = "报支类别公司细项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        return toAjax(financeApItemDetailCompanyService.insertFinanceApItemDetailCompany(financeApItemDetailCompany));
    }

    /**
     * 修改报支类别公司细项
     */
    @RequiresPermissions("finance:itemDetailCompany:edit")
    @Log(title = "报支类别公司细项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        return toAjax(financeApItemDetailCompanyService.updateFinanceApItemDetailCompany(financeApItemDetailCompany));
    }

    /**
     * 删除报支类别公司细项
     */
    @RequiresPermissions("finance:itemDetailCompany:remove")
    @Log(title = "报支类别公司细项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApItemDetailCompanyService.deleteFinanceApItemDetailCompanyByIds(ids));
    }
    @RequiresPermissions("finance:reimbItemComp:query")
    @GetMapping(value = "/selectDetailNoList")
    public List<Map<String,String>> selectItemNoList(FinanceApItemDetailCompany financeApItemDetailCompany)
    {
        return financeApItemDetailCompanyService.selectItemNoList(financeApItemDetailCompany);
    }
}

