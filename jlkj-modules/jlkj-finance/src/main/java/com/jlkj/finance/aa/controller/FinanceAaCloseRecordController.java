package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaCloseRecord;
import com.jlkj.finance.aa.service.IFinanceAaCloseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 关账执行记录Controller
 *
 * @author 265799
 * @date 2023-05-23
 */
@RestController
@RequestMapping("/recordClosing")
public class FinanceAaCloseRecordController extends BaseController
{
    @Autowired
    private IFinanceAaCloseRecordService financeAaCloseRecordService;

    /**
     * 查询关账执行记录列表
     */
    @RequiresPermissions("aa:record:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCloseRecord financeAaCloseRecord)
    {
        startPage();
        List<FinanceAaCloseRecord> list = financeAaCloseRecordService.selectFinanceAaCloseRecordList(financeAaCloseRecord);
        return getDataTable(list);
    }

    /**
     * 导出关账执行记录列表
     */
    @RequiresPermissions("aa:record:export")
    @Log(title = "关账执行记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCloseRecord financeAaCloseRecord)
    {
        List<FinanceAaCloseRecord> list = financeAaCloseRecordService.selectFinanceAaCloseRecordList(financeAaCloseRecord);
        ExcelUtil<FinanceAaCloseRecord> util = new ExcelUtil<FinanceAaCloseRecord>(FinanceAaCloseRecord.class);
        util.exportExcel(response, list, "关账执行记录数据");
    }

    /**
     * 获取关账执行记录详细信息
     */
    @RequiresPermissions("aa:record:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCloseRecordService.selectFinanceAaCloseRecordById(id));
    }

    /**
     * 新增关账执行记录
     */
    @RequiresPermissions("aa:record:add")
    @Log(title = "关账执行记录", businessType = BusinessType.INSERT)
    @PostMapping("/addClosingExecute")
    public AjaxResult add(@RequestBody FinanceAaCloseRecord financeAaCloseRecord)
    {
        return success(financeAaCloseRecordService.insertFinanceAaCloseRecord(financeAaCloseRecord));
    }



    /**
     * 修改关账执行记录
     */
    @RequiresPermissions("aa:record:edit")
    @Log(title = "关账执行记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCloseRecord financeAaCloseRecord)
    {
        return toAjax(financeAaCloseRecordService.updateFinanceAaCloseRecord(financeAaCloseRecord));
    }

    /**
     * 删除关账执行记录
     */
    @RequiresPermissions("aa:record:remove")
    @Log(title = "关账执行记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCloseRecordService.deleteFinanceAaCloseRecordByIds(ids));
    }
}
