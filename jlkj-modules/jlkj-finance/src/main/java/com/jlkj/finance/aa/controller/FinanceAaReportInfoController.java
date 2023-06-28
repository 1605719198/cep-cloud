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
import com.jlkj.finance.aa.domain.FinanceAaReportInfo;
import com.jlkj.finance.aa.service.IFinanceAaReportInfoService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 印项目计算记录Controller
 *
 * @author jlkj
 * @date 2023-06-28
 */
@RestController
@RequestMapping("/reportInfo")
public class FinanceAaReportInfoController extends BaseController
{
    @Autowired
    private IFinanceAaReportInfoService financeAaReportInfoService;

    /**
     * 查询印项目计算记录列表
     */
    @RequiresPermissions("finance:reportInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaReportInfo financeAaReportInfo)
    {
        startPage();
        List<FinanceAaReportInfo> list = financeAaReportInfoService.selectFinanceAaReportInfoList(financeAaReportInfo);
        return getDataTable(list);
    }

    /**
     * 导出印项目计算记录列表
     */
    @RequiresPermissions("finance:reportInfo:export")
    @Log(title = "印项目计算记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaReportInfo financeAaReportInfo)
    {
        List<FinanceAaReportInfo> list = financeAaReportInfoService.selectFinanceAaReportInfoList(financeAaReportInfo);
        ExcelUtil<FinanceAaReportInfo> util = new ExcelUtil<FinanceAaReportInfo>(FinanceAaReportInfo.class);
        util.exportExcel(response, list, "印项目计算记录数据");
    }

    /**
     * 获取印项目计算记录详细信息
     */
    @RequiresPermissions("finance:reportInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaReportInfoService.selectFinanceAaReportInfoById(id));
    }

    /**
     * 新增印项目计算记录
     */
    @RequiresPermissions("finance:reportInfo:add")
    @Log(title = "印项目计算记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaReportInfo financeAaReportInfo)
    {
        return toAjax(financeAaReportInfoService.insertFinanceAaReportInfo(financeAaReportInfo));
    }

    /**
     * 修改印项目计算记录
     */
    @RequiresPermissions("finance:reportInfo:edit")
    @Log(title = "印项目计算记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaReportInfo financeAaReportInfo)
    {
        return toAjax(financeAaReportInfoService.updateFinanceAaReportInfo(financeAaReportInfo));
    }

    /**
     * 删除印项目计算记录
     */
    @RequiresPermissions("finance:reportInfo:remove")
    @Log(title = "印项目计算记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaReportInfoService.deleteFinanceAaReportInfoByIds(ids));
    }
}
