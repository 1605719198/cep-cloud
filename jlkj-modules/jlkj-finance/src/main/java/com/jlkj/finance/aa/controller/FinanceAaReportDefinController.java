package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaReportDefin;
import com.jlkj.finance.aa.service.IFinanceAaReportDefinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 集团管理-报定义Controller
 *
 * @author 116514
 * @date 2023-05-04
 */
@RestController
@RequestMapping("/reportDefin")
public class FinanceAaReportDefinController extends BaseController
{
    @Autowired
    private IFinanceAaReportDefinService financeAaReportDefinService;

    /**
     * 查询集团管理-报定义列表
     */
    @RequiresPermissions("finance:reportDefin:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaReportDefin financeAaReportDefin)
    {
        startPage();
        List<FinanceAaReportDefin> list = financeAaReportDefinService.selectFinanceAaReportDefinList(financeAaReportDefin);
        return getDataTable(list);
    }

    /**
     * 导出集团管理-报定义列表
     */
    @RequiresPermissions("finance:reportDefin:export")
    @Log(title = "集团管理-报定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaReportDefin financeAaReportDefin)
    {
        List<FinanceAaReportDefin> list = financeAaReportDefinService.selectFinanceAaReportDefinList(financeAaReportDefin);
        ExcelUtil<FinanceAaReportDefin> util = new ExcelUtil<FinanceAaReportDefin>(FinanceAaReportDefin.class);
        util.exportExcel(response, list, "集团管理-报定义数据");
    }

    /**
     * 获取集团管理-报定义详细信息
     */
    @RequiresPermissions("finance:reportDefin:query")
    @GetMapping(value = "/{reportId}")
    public AjaxResult getInfo(@PathVariable("reportId") String reportId)
    {
        return success(financeAaReportDefinService.selectFinanceAaReportDefinByReportId(reportId));
    }

    /**
     * 新增集团管理-报定义
     */
    @RequiresPermissions("finance:reportDefin:add")
    @Log(title = "集团管理-报定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaReportDefin financeAaReportDefin)
    {
        return toAjax(financeAaReportDefinService.insertFinanceAaReportDefin(financeAaReportDefin));
    }

    /**
     * 修改集团管理-报定义
     */
    @RequiresPermissions("finance:reportDefin:edit")
    @Log(title = "集团管理-报定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaReportDefin financeAaReportDefin)
    {
        return toAjax(financeAaReportDefinService.updateFinanceAaReportDefin(financeAaReportDefin));
    }

    /**
     * 删除集团管理-报定义
     */
    @RequiresPermissions("finance:reportDefin:remove")
    @Log(title = "集团管理-报定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reportIds}")
    public AjaxResult remove(@PathVariable String[] reportIds)
    {
        return toAjax(financeAaReportDefinService.deleteFinanceAaReportDefinByReportIds(reportIds));
    }

    @GetMapping("/reportNoList/{compId}")
    public List<Map<String,String>> selectReportNoList(@PathVariable("compId") String compId)
    {
        return financeAaReportDefinService.selectReportNoList(compId);
    }
}
