package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import com.jlkj.finance.ip.dto.FinanceCostDownTypeTreeDTO;
import io.swagger.v3.oas.annotations.Operation;
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
import com.jlkj.finance.aa.domain.FinanceAaCashflowCode;
import com.jlkj.finance.aa.service.IFinanceAaCashflowCodeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 现金流量代码Controller
 *
 * @author 265799
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/code")
public class FinanceAaCashflowCodeController extends BaseController
{
    @Autowired
    private IFinanceAaCashflowCodeService financeAaCashflowCodeService;

    /**
     * 查询现金流量代码列表
     */
    @RequiresPermissions("aa:code:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCashflowCode financeAaCashflowCode)
    {
        startPage();
        List<FinanceAaCashflowCode> list = financeAaCashflowCodeService.selectFinanceAaCashflowCodeList(financeAaCashflowCode);
        return getDataTable(list);
    }

    /**
     * 导出现金流量代码列表
     */
    @RequiresPermissions("aa:code:export")
    @Log(title = "现金流量代码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCashflowCode financeAaCashflowCode)
    {
        List<FinanceAaCashflowCode> list = financeAaCashflowCodeService.selectFinanceAaCashflowCodeList(financeAaCashflowCode);
        ExcelUtil<FinanceAaCashflowCode> util = new ExcelUtil<FinanceAaCashflowCode>(FinanceAaCashflowCode.class);
        util.exportExcel(response, list, "现金流量代码数据");
    }

    /**
     * 获取现金流量代码详细信息
     */
    @RequiresPermissions("aa:code:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCashflowCodeService.selectFinanceAaCashflowCodeById(id));
    }

    /**
     * 新增现金流量代码
     */
    @RequiresPermissions("aa:code:add")
    @Log(title = "现金流量代码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCashflowCodeDTO financeAaCashflowCodeDTO)
    {

        List<FinanceAaCashflowCodeDTO> projectIds = financeAaCashflowCodeDTO.getCodeList();
                return toAjax(financeAaCashflowCodeService.insertFinanceAaCashflowCode(projectIds));

    }

    /**
     * 修改现金流量代码
     */
    @RequiresPermissions("aa:code:edit")
    @Log(title = "现金流量代码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCashflowCode financeAaCashflowCode)
    {
        return toAjax(financeAaCashflowCodeService.updateFinanceAaCashflowCode(financeAaCashflowCode));
    }

    /**
     * 删除现金流量代码
     */
    @RequiresPermissions("aa:code:remove")
    @Log(title = "现金流量代码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCashflowCodeService.deleteFinanceAaCashflowCodeByIds(ids));
    }
}
