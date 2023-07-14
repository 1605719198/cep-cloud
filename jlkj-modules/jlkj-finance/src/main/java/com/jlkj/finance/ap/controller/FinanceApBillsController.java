package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApBills;
import com.jlkj.finance.ap.service.IFinanceApBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 发票信息Controller
 * @Description:
 * @author 265799
 * @date 2023-07-13
 * @version:V1.0
 */
@RestController
@RequestMapping("/ap/bills")
public class FinanceApBillsController extends BaseController
{
    @Autowired
    private IFinanceApBillsService financeApBillsService;

    /**
     * 查询发票信息列表
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills
     * @return
     */
    @RequiresPermissions("ap:bills:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApBills financeApBills)
    {
        startPage();
        List<FinanceApBills> list = financeApBillsService.selectFinanceApBillsList(financeApBills);
        return getDataTable(list);
    }

    /**
     * 导出发票信息列表
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills
     * @return
     */
    @RequiresPermissions("ap:bills:export")
    @Log(title = "发票信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApBills financeApBills)
    {
        List<FinanceApBills> list = financeApBillsService.selectFinanceApBillsList(financeApBills);
        ExcelUtil<FinanceApBills> util = new ExcelUtil<FinanceApBills>(FinanceApBills.class);
        util.exportExcel(response, list, "发票信息数据");
    }

    /**
     * 获取发票信息详细信息
     * @author 265799
     * @date 2023-07-13
     * @param id
     * @return
     */
    @RequiresPermissions("ap:bills:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApBillsService.selectFinanceApBillsById(id));
    }

    /**
     * 新增发票信息
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills
     * @return
     */
    @RequiresPermissions("ap:bills:add")
    @Log(title = "发票信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApBills financeApBills)
    {
        return toAjax(financeApBillsService.insertFinanceApBills(financeApBills));
    }

    /**
     * 修改发票信息
     * @author 265799
     * @date 2023-07-13
     * @param financeApBills
     * @return
     */
    @RequiresPermissions("ap:bills:edit")
    @Log(title = "发票信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApBills financeApBills)
    {
        return toAjax(financeApBillsService.updateFinanceApBills(financeApBills));
    }

    /**
     * 删除发票信息
     * @author 265799
     * @date 2023-07-13
     * @param ids
     * @return
     */
    @RequiresPermissions("ap:bills:remove")
    @Log(title = "发票信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApBillsService.deleteFinanceApBillsByIds(ids));
    }
}
