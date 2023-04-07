package com.jlkj.finance.gp.controller;

import java.util.List;


import javax.servlet.http.HttpServletResponse;

import com.jlkj.common.core.utils.uuid.UUID;
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
import com.jlkj.finance.gp.domain.FinanceGpCorrelation;
import com.jlkj.finance.gp.service.IFinanceGpCorrelationService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 是否关联方更新记录Controller
 *
 * @author 265799
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/correlation")
public class FinanceGpCorrelationController extends BaseController
{
    @Autowired
    private IFinanceGpCorrelationService financeGpCorrelationService;

    /**
     * 查询是否关联方更新记录列表
     */
    @RequiresPermissions("finance:correlation:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceGpCorrelation financeGpCorrelation)
    {
        startPage();
        List<FinanceGpCorrelation> list = financeGpCorrelationService.selectFinanceGpCorrelationList(financeGpCorrelation);
        return getDataTable(list);
    }

    /**
     * 导出是否关联方更新记录列表
     */
    @RequiresPermissions("finance:correlation:export")
    @Log(title = "是否关联方更新记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceGpCorrelation financeGpCorrelation)
    {
        List<FinanceGpCorrelation> list = financeGpCorrelationService.selectFinanceGpCorrelationList(financeGpCorrelation);
        ExcelUtil<FinanceGpCorrelation> util = new ExcelUtil<FinanceGpCorrelation>(FinanceGpCorrelation.class);
        util.exportExcel(response, list, "是否关联方更新记录数据");
    }

    /**
     * 获取是否关联方更新记录详细信息
     */
    @RequiresPermissions("finance:correlation:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeGpCorrelationService.selectFinanceGpCorrelationByUuid(uuid));
    }

    /**
     * 新增是否关联方更新记录
     */
    @RequiresPermissions("finance:correlation:add")
    @Log(title = "是否关联方更新记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceGpCorrelation financeGpCorrelation)
    {

        return toAjax(financeGpCorrelationService.insertFinanceGpCorrelation(financeGpCorrelation));
    }

    /**
     * 修改是否关联方更新记录
     */
    @RequiresPermissions("finance:correlation:edit")
    @Log(title = "是否关联方更新记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceGpCorrelation financeGpCorrelation)
    {
        return toAjax(financeGpCorrelationService.updateFinanceGpCorrelation(financeGpCorrelation));
    }

    /**
     * 删除是否关联方更新记录
     */
    @RequiresPermissions("finance:correlation:remove")
    @Log(title = "是否关联方更新记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeGpCorrelationService.deleteFinanceGpCorrelationByUuids(uuids));
    }
}
