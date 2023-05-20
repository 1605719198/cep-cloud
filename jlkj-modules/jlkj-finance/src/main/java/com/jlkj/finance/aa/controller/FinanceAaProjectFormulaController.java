package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaProjectFormula;
import com.jlkj.finance.aa.service.IFinanceAaProjectFormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 集团管理-纵向印项目公式Controller
 *
 * @author 116514
 * @date 2023-05-10
 */
@RestController
@RequestMapping("/projectFormula")
public class FinanceAaProjectFormulaController extends BaseController
{
    @Autowired
    private IFinanceAaProjectFormulaService financeAaProjectFormulaService;

    /**
     * 查询集团管理-纵向印项目公式列表
     */
    @RequiresPermissions("finance:reportDefin:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaProjectFormula financeAaProjectFormula)
    {
        startPage();
        List<FinanceAaProjectFormula> list = financeAaProjectFormulaService.selectFinanceAaProjectFormulaList(financeAaProjectFormula);
        return getDataTable(list);
    }

    /**
     * 导出集团管理-纵向印项目公式列表
     */
    @RequiresPermissions("finance:reportDefin:export")
    @Log(title = "集团管理-纵向印项目公式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaProjectFormula financeAaProjectFormula)
    {
        List<FinanceAaProjectFormula> list = financeAaProjectFormulaService.selectFinanceAaProjectFormulaList(financeAaProjectFormula);
        ExcelUtil<FinanceAaProjectFormula> util = new ExcelUtil<FinanceAaProjectFormula>(FinanceAaProjectFormula.class);
        util.exportExcel(response, list, "集团管理-纵向印项目公式数据");
    }

    /**
     * 获取集团管理-纵向印项目公式详细信息
     */
    @RequiresPermissions("finance:reportDefin:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaProjectFormulaService.selectFinanceAaProjectFormulaById(id));
    }

    /**
     * 新增集团管理-纵向印项目公式
     */
    @RequiresPermissions("finance:reportDefin:add")
    @Log(title = "集团管理-纵向印项目公式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaProjectFormula financeAaProjectFormula)
    {
        List<FinanceAaProjectFormula> dataList = financeAaProjectFormula.getDataList();
        return toAjax(financeAaProjectFormulaService.insertFinanceAaProjectFormulaItem(dataList));
    }

    /**
     * 修改集团管理-纵向印项目公式
     */
    @RequiresPermissions("finance:reportDefin:edit")
    @Log(title = "集团管理-纵向印项目公式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaProjectFormula financeAaProjectFormula)
    {
        return toAjax(financeAaProjectFormulaService.updateFinanceAaProjectFormula(financeAaProjectFormula));
    }

    /**
     * 删除集团管理-纵向印项目公式
     */
    @RequiresPermissions("finance:reportDefin:remove")
    @Log(title = "集团管理-纵向印项目公式", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaProjectFormulaService.deleteFinanceAaProjectFormulaByIds(ids));
    }
    /**
     * 状态修改
     */
    @RequiresPermissions("finance:projectContent:edit")
    @Log(title = "集团管理-纵向印项目公式", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAaProjectFormula financeAaProjectFormula)
    {
        return toAjax(financeAaProjectFormulaService.updateFinanceAaProjectFormula(financeAaProjectFormula));
    }
}
