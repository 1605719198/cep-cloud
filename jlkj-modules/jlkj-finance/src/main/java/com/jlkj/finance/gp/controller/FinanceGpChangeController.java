package com.jlkj.finance.gp.controller;

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
import com.jlkj.finance.gp.domain.FinanceGpChange;
import com.jlkj.finance.gp.service.IFinanceGpChangeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 厂商异动申请Controller
 *
 * @author 265799
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/finance/change")
public class FinanceGpChangeController extends BaseController
{
    @Autowired
    private IFinanceGpChangeService financeGpChangeService;

    /**
     * 查询厂商异动申请列表
     */
    @RequiresPermissions("gp:change:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceGpChange financeGpChange)
    {
        startPage();
        List<FinanceGpChange> list = financeGpChangeService.selectFinanceGpChangeList(financeGpChange);
        return getDataTable(list);
    }

    /**
     * 导出厂商异动申请列表
     */
    @RequiresPermissions("gp:change:export")
    @Log(title = "厂商异动申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceGpChange financeGpChange)
    {
        List<FinanceGpChange> list = financeGpChangeService.selectFinanceGpChangeList(financeGpChange);
        ExcelUtil<FinanceGpChange> util = new ExcelUtil<FinanceGpChange>(FinanceGpChange.class);
        util.exportExcel(response, list, "厂商异动申请数据");
    }

    /**
     * 获取厂商异动申请详细信息
     */
    @RequiresPermissions("gp:change:query")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfo(@PathVariable("applyId") String applyId)
    {
        return success(financeGpChangeService.selectFinanceGpChangeByApplyId(applyId));
    }

    /**
     * 新增厂商异动申请
     */
    @RequiresPermissions("gp:change:add")
    @Log(title = "厂商异动申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceGpChange financeGpChange)
    {
        financeGpChange.setCreateBy(getUsername());
        return toAjax(financeGpChangeService.insertFinanceGpChange(financeGpChange));
    }

    /**
     * 修改厂商异动申请
     */
    @RequiresPermissions("gp:change:edit")
    @Log(title = "厂商异动申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceGpChange financeGpChange)
    {
        financeGpChange.setUpdateBy(getUsername());

        return toAjax(financeGpChangeService.updateFinanceGpChange(financeGpChange));
    }

    /**
     * 删除厂商异动申请
     */
    @RequiresPermissions("gp:change:remove")
    @Log(title = "厂商异动申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{applyIds}")
    public AjaxResult remove(@PathVariable String[] applyIds)
    {
        return toAjax(financeGpChangeService.deleteFinanceGpChangeByApplyIds(applyIds));
    }
}
