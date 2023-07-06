package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtAcctMain;
import com.jlkj.finance.ft.dto.FinanceFtAcctMainDTO;
import com.jlkj.finance.ft.service.IFinanceFtAcctMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账务代码Controller
 *
 * @author SunXuTong
 * @date 2023-06-27
 */
@RestController
@RequestMapping("/acctMain")
public class FinanceFtAcctMainController extends BaseController
{
    @Autowired
    private IFinanceFtAcctMainService financeFtAcctMainService;

    /**
     * 查询账务代码列表
     */
    @RequiresPermissions("finance:acctMain:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtAcctMain financeFtAcctMain)
    {
        startPage();
        List<FinanceFtAcctMain> list = financeFtAcctMainService.selectFinanceFtAcctMainList(financeFtAcctMain);
        return getDataTable(list);
    }

    /**
     * 导出账务代码列表
     */
    @RequiresPermissions("finance:acctMain:export")
    @Log(title = "账务代码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtAcctMain financeFtAcctMain)
    {
        List<FinanceFtAcctMain> list = financeFtAcctMainService.selectFinanceFtAcctMainList(financeFtAcctMain);
        ExcelUtil<FinanceFtAcctMain> util = new ExcelUtil<FinanceFtAcctMain>(FinanceFtAcctMain.class);
        util.exportExcel(response, list, "账务代码数据");
    }

    /**
     * 获取账务代码详细信息
     */
    @RequiresPermissions("finance:acctMain:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtAcctMainService.selectFinanceFtAcctMainByUuid(uuid));
    }

    /**
     * 新增账务代码
     */
    @RequiresPermissions("finance:acctMain:add")
    @Log(title = "账务代码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtAcctMainDTO financeFtAcctMainDTO)
    {
        return toAjax(financeFtAcctMainService.insertFinanceFtAcctMain(financeFtAcctMainDTO));
    }

    /**
     * 修改账务代码
     */
    @RequiresPermissions("finance:acctMain:edit")
    @Log(title = "账务代码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtAcctMain financeFtAcctMain)
    {
        return toAjax(financeFtAcctMainService.updateFinanceFtAcctMain(financeFtAcctMain));
    }

    /**
     * 删除账务代码
     */
    @RequiresPermissions("finance:acctMain:remove")
    @Log(title = "账务代码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtAcctMainService.deleteFinanceFtAcctMainByUuids(uuids));
    }
}
