package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaBranch;
import com.jlkj.finance.aa.service.IFinanceAaBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 金融机构管理分行Controller
 *
 * @author SunXuTong
 * @date 2023-04-14
 */
@RestController
@RequestMapping("/branch")
public class FinanceAaBranchController extends BaseController
{
    @Autowired
    private IFinanceAaBranchService financeAaBranchService;

    /**
     * 查询金融机构管理分行列表
     */
    @RequiresPermissions("finance:branch:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaBranch financeAaBranch)
    {
        startPage();
        List<FinanceAaBranch> list = financeAaBranchService.selectFinanceAaBranchList(financeAaBranch);
        return getDataTable(list);
    }

    /**
     * 导出金融机构管理分行列表
     */
    @RequiresPermissions("finance:branch:export")
    @Log(title = "金融机构管理分行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaBranch financeAaBranch)
    {
        List<FinanceAaBranch> list = financeAaBranchService.selectFinanceAaBranchList(financeAaBranch);
        ExcelUtil<FinanceAaBranch> util = new ExcelUtil<FinanceAaBranch>(FinanceAaBranch.class);
        util.exportExcel(response, list, "金融机构管理分行数据");
    }

    /**
     * 获取金融机构管理分行详细信息
     */
    @RequiresPermissions("finance:branch:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaBranchService.selectFinanceAaBranchById(id));
    }

    /**
     * 新增金融机构管理分行
     */
    @RequiresPermissions("finance:branch:add")
    @Log(title = "金融机构管理分行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaBranch financeAaBranch)
    {
        return toAjax(financeAaBranchService.insertFinanceAaBranch(financeAaBranch));
    }

    /**
     * 修改金融机构管理分行
     */
    @RequiresPermissions("finance:branch:edit")
    @Log(title = "金融机构管理分行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaBranch financeAaBranch)
    {
        return toAjax(financeAaBranchService.updateFinanceAaBranch(financeAaBranch));
    }

    /**
     * 删除金融机构管理分行
     */
    @RequiresPermissions("finance:branch:remove")
    @Log(title = "金融机构管理分行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaBranchService.deleteFinanceAaBranchByIds(ids));
    }
}
