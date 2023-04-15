package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaBank;
import com.jlkj.finance.aa.service.IFinanceAaBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 金融机构管理Controller
 *
 * @author SunXuTong
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/bank")
public class FinanceAaBankController extends BaseController
{
    @Autowired
    private IFinanceAaBankService financeAaBankService;

    /**
     * 查询金融机构管理列表
     */
    @RequiresPermissions("finance:bank:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaBank financeAaBank)
    {
        startPage();
        List<FinanceAaBank> list = financeAaBankService.selectFinanceAaBankList(financeAaBank);
        return getDataTable(list);
    }

    /**
     * 导出金融机构管理列表
     */
    @RequiresPermissions("finance:bank:export")
    @Log(title = "金融机构管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaBank financeAaBank)
    {
        List<FinanceAaBank> list = financeAaBankService.selectFinanceAaBankList(financeAaBank);
        ExcelUtil<FinanceAaBank> util = new ExcelUtil<FinanceAaBank>(FinanceAaBank.class);
        util.exportExcel(response, list, "金融机构管理数据");
    }

    /**
     * 获取金融机构管理详细信息
     */
    @RequiresPermissions("finance:bank:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaBankService.selectFinanceAaBankById(id));
    }

    /**
     * 新增金融机构管理
     */
    @RequiresPermissions("finance:bank:add")
    @Log(title = "金融机构管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaBank financeAaBank)
    {
        return toAjax(financeAaBankService.insertFinanceAaBank(financeAaBank));
    }

    /**
     * 修改金融机构管理
     */
    @RequiresPermissions("finance:bank:edit")
    @Log(title = "金融机构管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaBank financeAaBank)
    {
        return toAjax(financeAaBankService.updateFinanceAaBank(financeAaBank));
    }

    /**
     * 删除金融机构管理
     */
    @RequiresPermissions("finance:bank:remove")
    @Log(title = "金融机构管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaBankService.deleteFinanceAaBankByIds(ids));
    }
}
