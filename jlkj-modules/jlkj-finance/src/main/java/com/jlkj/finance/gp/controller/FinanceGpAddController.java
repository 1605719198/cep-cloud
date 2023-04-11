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
import com.jlkj.finance.gp.domain.FinanceGpAdd;
import com.jlkj.finance.gp.service.IFinanceGpAddService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 厂商增户申请Controller
 *
 * @author 265799
 * @date 2023-04-10
 */
@RestController
@RequestMapping("/finance/add")
public class FinanceGpAddController extends BaseController
{
    @Autowired
    private IFinanceGpAddService financeGpAddService;

    /**
     * 查询厂商增户申请列表
     */
    @RequiresPermissions("gp:add:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceGpAdd financeGpAdd)
    {
        startPage();
        List<FinanceGpAdd> list = financeGpAddService.selectFinanceGpAddList(financeGpAdd);
        return getDataTable(list);
    }

    /**
     * 导出厂商增户申请列表
     */
    @RequiresPermissions("gp:add:export")
    @Log(title = "厂商增户申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceGpAdd financeGpAdd)
    {
        List<FinanceGpAdd> list = financeGpAddService.selectFinanceGpAddList(financeGpAdd);
        ExcelUtil<FinanceGpAdd> util = new ExcelUtil<FinanceGpAdd>(FinanceGpAdd.class);
        util.exportExcel(response, list, "厂商增户申请数据");
    }

    /**
     * 获取厂商增户申请详细信息
     */
    @RequiresPermissions("gp:add:query")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfo(@PathVariable("applyId") String applyId)
    {
        return success(financeGpAddService.selectFinanceGpAddByApplyId(applyId));
    }

    /**
     * 新增厂商增户申请
     */
    @RequiresPermissions("gp:add:add")
    @Log(title = "厂商增户申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceGpAdd financeGpAdd)
    {
        financeGpAdd.setCreateBy(getUsername());
        return toAjax(financeGpAddService.insertFinanceGpAdd(financeGpAdd));
    }

    /**
     * 修改厂商增户申请
     */
    @RequiresPermissions("gp:add:edit")
    @Log(title = "厂商增户申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceGpAdd financeGpAdd)
    {
        financeGpAdd.setUpdateBy(getUsername());
        return toAjax(financeGpAddService.updateFinanceGpAdd(financeGpAdd));
    }

    /**
     * 删除厂商增户申请
     */
    @RequiresPermissions("gp:add:remove")
    @Log(title = "厂商增户申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{applyIds}")
    public AjaxResult remove(@PathVariable String[] applyIds)
    {
        return toAjax(financeGpAddService.deleteFinanceGpAddByApplyIds(applyIds));
    }
}
