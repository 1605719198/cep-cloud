package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.system.api.domain.SysUser;
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
import com.jlkj.finance.aa.domain.FinanceCalcode;
import com.jlkj.finance.aa.service.IFinanceCalcodeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 核算项目-内容维护Controller
 *
 * @author 116514
 * @date 2023-04-10
 */
@RestController
@RequestMapping("/calcode")
public class FinanceCalcodeController extends BaseController
{
    @Autowired
    private IFinanceCalcodeService financeCalcodeService;

    /**
     * 查询核算项目-内容维护列表
     */
    @RequiresPermissions("finance:calcode:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceCalcode financeCalcode)
    {
        startPage();
        List<FinanceCalcode> list = financeCalcodeService.selectFinanceCalcodeList(financeCalcode);
        return getDataTable(list);
    }

    /**
     * 导出核算项目-内容维护列表
     */
    @RequiresPermissions("finance:calcode:export")
    @Log(title = "核算项目-内容维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceCalcode financeCalcode)
    {
        List<FinanceCalcode> list = financeCalcodeService.selectFinanceCalcodeList(financeCalcode);
        ExcelUtil<FinanceCalcode> util = new ExcelUtil<FinanceCalcode>(FinanceCalcode.class);
        util.exportExcel(response, list, "核算项目-内容维护数据");
    }

    /**
     * 获取核算项目-内容维护详细信息
     */
    @RequiresPermissions("finance:calcode:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeCalcodeService.selectFinanceCalcodeById(id));
    }

    /**
     * 新增核算项目-内容维护
     */
    @RequiresPermissions("finance:calcode:add")
    @Log(title = "核算项目-内容维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceCalcode financeCalcode)
    {
        return toAjax(financeCalcodeService.insertFinanceCalcode(financeCalcode));
    }

    /**
     * 修改核算项目-内容维护
     */
    @RequiresPermissions("finance:calcode:edit")
    @Log(title = "核算项目-内容维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceCalcode financeCalcode)
    {
        return toAjax(financeCalcodeService.updateFinanceCalcode(financeCalcode));
    }

    /**
     * 删除核算项目-内容维护
     */
    @RequiresPermissions("finance:calcode:remove")
    @Log(title = "核算项目-内容维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeCalcodeService.deleteFinanceCalcodeByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:calcode:edit")
    @Log(title = "核算项目-内容维护", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceCalcode financeCalcode)
    {
        financeCalcode.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(financeCalcodeService.updateFinanceCalcode(financeCalcode));
    }
}
