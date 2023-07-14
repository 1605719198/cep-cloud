package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoExpenseBills;
import com.jlkj.finance.ao.service.IFinanceAoExpenseBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 我的发票Controller
 * @Description:
 * @author 116514
 * @date 2023-05-18
 * @version:V1.0
 */
@RestController
@RequestMapping("/expenseBills")
public class FinanceAoExpenseBillsController extends BaseController
{
    @Autowired
    private IFinanceAoExpenseBillsService financeAoExpenseBillsService;

    /**
     * 查询我的发票列表
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills
     * @return
     */
    @RequiresPermissions("ao:expenseBills:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoExpenseBills financeAoExpenseBills)
    {
        startPage();
        List<FinanceAoExpenseBills> list = financeAoExpenseBillsService.selectFinanceAoExpenseBillsList(financeAoExpenseBills);
        return getDataTable(list);
    }

    /**
     * 导出我的发票列表
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills
     * @return
     */
    @RequiresPermissions("ao:expenseBills:export")
    @Log(title = "我的发票", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoExpenseBills financeAoExpenseBills)
    {
        List<FinanceAoExpenseBills> list = financeAoExpenseBillsService.selectFinanceAoExpenseBillsList(financeAoExpenseBills);
        ExcelUtil<FinanceAoExpenseBills> util = new ExcelUtil<FinanceAoExpenseBills>(FinanceAoExpenseBills.class);
        util.exportExcel(response, list, "我的发票数据");
    }

    /**
     * 获取我的发票详细信息
     * @author 116514
     * @date 2023-05-18
     * @param id
     * @return
     */
    @RequiresPermissions("ao:expenseBills:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoExpenseBillsService.selectFinanceAoExpenseBillsById(id));
    }

    /**
     * 新增我的发票
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills
     * @return
     */
    @RequiresPermissions("ao:expenseBills:add")
    @Log(title = "我的发票", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoExpenseBills financeAoExpenseBills)
    {
        return toAjax(financeAoExpenseBillsService.insertFinanceAoExpenseBills(financeAoExpenseBills));
    }

    /**
     * 修改我的发票
     * @author 116514
     * @date 2023-05-18
     * @param financeAoExpenseBills
     * @return
     */
    @RequiresPermissions("ao:expenseBills:edit")
    @Log(title = "我的发票", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoExpenseBills financeAoExpenseBills)
    {
        return toAjax(financeAoExpenseBillsService.updateFinanceAoExpenseBills(financeAoExpenseBills));
    }

    /**
     * 删除我的发票
     * @author 116514
     * @date 2023-05-18
     * @param ids
     * @return
     */
    @RequiresPermissions("ao:expenseBills:remove")
    @Log(title = "我的发票", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoExpenseBillsService.deleteFinanceAoExpenseBillsByIds(ids));
    }
}
