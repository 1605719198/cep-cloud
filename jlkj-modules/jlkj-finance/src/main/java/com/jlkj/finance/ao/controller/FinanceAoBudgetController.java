package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.dto.FinanceAoBudgetDto;
import com.jlkj.finance.ao.service.IFinanceAoBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 预算设置Controller
 *
 * @author jlkj
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/budget")
public class FinanceAoBudgetController extends BaseController
{
    @Autowired
    private IFinanceAoBudgetService financeAoBudgetService;

    /**
     * 查询预算设置列表
     */

    @RequiresPermissions("ao:budget:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoBudgetDto financeAoBudgetDto)
    {
        startPage();
        List<FinanceAoBudgetDto> list = financeAoBudgetService.selectFinanceAoBudgetList(financeAoBudgetDto);
        return getDataTable(list);
    }

    /**
     * 导出预算设置列表
     */
    @RequiresPermissions("ao:budget:export")
    @Log(title = "预算设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoBudgetDto financeAoBudgetDto)
    {
        List<FinanceAoBudgetDto> list = financeAoBudgetService.selectFinanceAoBudgetList(financeAoBudgetDto);
        ExcelUtil<FinanceAoBudgetDto> util = new ExcelUtil<FinanceAoBudgetDto>(FinanceAoBudgetDto.class);
        util.exportExcel(response, list, "预算设置数据");
    }

    /**
     * 获取预算设置详细信息
     */
    @RequiresPermissions("ao:budget:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoBudgetService.selectFinanceAoBudgetById(id));
    }

    /**
     * 新增预算设置
     */
    @RequiresPermissions("ao:budget:add")
    @Log(title = "预算设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoBudgetDto financeAoBudgetDto)
    {
        return toAjax(financeAoBudgetService.insertFinanceAoBudget(financeAoBudgetDto));
    }

    /**
     * 修改预算设置
     */
    @RequiresPermissions("ao:budget:edit")
    @Log(title = "预算设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoBudgetDto financeAoBudgetDto)
    {
        return toAjax(financeAoBudgetService.updateFinanceAoBudget(financeAoBudgetDto));
    }

    /**
     * 删除预算设置
     */
    @RequiresPermissions("ao:budget:remove")
    @Log(title = "预算设置", businessType = BusinessType.DELETE)
	@DeleteMapping(value = "/deleteList")
    public AjaxResult remove(@RequestBody List<FinanceAoBudgetDto> financeAoBudgetDtos)
    {
        return toAjax(financeAoBudgetService.deleteFinanceAoBudgetByIds(financeAoBudgetDtos));
    }

    /**
     * 导入预算设置
     */
    @Log(title = "导入预算设置", businessType = BusinessType.IMPORT)
    @RequiresPermissions("aa:budget:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<FinanceAoBudgetDto> util = new ExcelUtil<FinanceAoBudgetDto>(FinanceAoBudgetDto.class);
        List<FinanceAoBudgetDto> financeAoBudgetDtos = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message  = financeAoBudgetService.importFinanceAaVoucher(financeAoBudgetDtos, updateSupport, operName);
        return success(message);
}

    /**
     * 导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<FinanceAoBudgetDto> util = new ExcelUtil<FinanceAoBudgetDto>(FinanceAoBudgetDto.class);
        util.importTemplateExcel(response, "预算设置表");
    }
}
