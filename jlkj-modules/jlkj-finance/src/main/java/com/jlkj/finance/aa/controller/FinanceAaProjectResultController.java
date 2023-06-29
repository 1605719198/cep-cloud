package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
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
import com.jlkj.finance.aa.domain.FinanceAaProjectResult;
import com.jlkj.finance.aa.service.IFinanceAaProjectResultService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 印项目计算结果Controller
 *
 * @author jlkj
 * @date 2023-06-28
 */
@RestController
@RequestMapping("/projectResult")
public class FinanceAaProjectResultController extends BaseController
{
    @Autowired
    private IFinanceAaProjectResultService financeAaProjectResultService;

    /**
     * 查询印项目计算结果列表
     */
    @RequiresPermissions("finance:projectResult:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaProjectResult financeAaProjectResult)
    {
        startPage();
        List<FinanceAaProjectResult> list = financeAaProjectResultService.selectFinanceAaProjectResultList(financeAaProjectResult);
        return getDataTable(list);
    }

    /**
     * 导出印项目计算结果列表
     */
    @RequiresPermissions("finance:projectResult:export")
    @Log(title = "印项目计算结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaProjectResult financeAaProjectResult)
    {
        List<FinanceAaProjectResult> list = financeAaProjectResultService.selectFinanceAaProjectResultList(financeAaProjectResult);
        ExcelUtil<FinanceAaProjectResult> util = new ExcelUtil<FinanceAaProjectResult>(FinanceAaProjectResult.class);
        util.exportExcel(response, list, "印项目计算结果数据");
    }

    /**
     * 获取印项目计算结果详细信息
     */
    @RequiresPermissions("finance:projectResult:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(financeAaProjectResultService.selectFinanceAaProjectResultById(id));
    }

    /**
     * 新增印项目计算结果
     */
    @RequiresPermissions("finance:projectResult:add")
    @Log(title = "印项目计算结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaProjectResult financeAaProjectResult)
    {
        return toAjax(financeAaProjectResultService.insertFinanceAaProjectResult(financeAaProjectResult));
    }

    /**
     * 修改印项目计算结果
     */
    @RequiresPermissions("finance:projectResult:edit")
    @Log(title = "印项目计算结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaProjectResult financeAaProjectResult)
    {
        return toAjax(financeAaProjectResultService.updateFinanceAaProjectResult(financeAaProjectResult));
    }

    /**
     * 删除印项目计算结果
     */
    @RequiresPermissions("finance:projectResult:remove")
    @Log(title = "印项目计算结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(financeAaProjectResultService.deleteFinanceAaProjectResultByIds(ids));
    }
}
