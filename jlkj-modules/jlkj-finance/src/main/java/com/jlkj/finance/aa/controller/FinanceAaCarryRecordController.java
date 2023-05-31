package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaCarryRecord;
import com.jlkj.finance.aa.service.IFinanceAaCarryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自动结转规则-执行记录Controller
 *
 * @author 265799
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/record")
public class FinanceAaCarryRecordController extends BaseController
{
    @Autowired
    private IFinanceAaCarryRecordService financeAaCarryRecordService;

    /**
     * 查询自动结转规则-执行记录列表
     */
    @RequiresPermissions("aa:record:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCarryRecord financeAaCarryRecord)
    {
        startPage();
        List<FinanceAaCarryRecord> list = financeAaCarryRecordService.selectFinanceAaCarryRecordList(financeAaCarryRecord);
        return getDataTable(list);
    }
    /**
     * 查询自动结转规则-执行记录列表
     */
    @RequiresPermissions("aa:record:list")
    @GetMapping("/listCompanyId")
    public TableDataInfo listCompanyId(FinanceAaCarryRecord financeAaCarryRecord)
    {
        startPage();
        List<FinanceAaCarryRecord> list = financeAaCarryRecordService.selectFinanceAaCarryRecordCompanyIdList(financeAaCarryRecord);
        return getDataTable(list);
    }

    /**
     * 导出自动结转规则-执行记录列表
     */
    @RequiresPermissions("aa:record:export")
    @Log(title = "自动结转规则-执行记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCarryRecord financeAaCarryRecord)
    {
        List<FinanceAaCarryRecord> list = financeAaCarryRecordService.selectFinanceAaCarryRecordList(financeAaCarryRecord);
        ExcelUtil<FinanceAaCarryRecord> util = new ExcelUtil<FinanceAaCarryRecord>(FinanceAaCarryRecord.class);
        util.exportExcel(response, list, "自动结转规则-执行记录数据");
    }

    /**
     * 获取自动结转规则-执行记录详细信息
     */
    @RequiresPermissions("aa:record:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCarryRecordService.selectFinanceAaCarryRecordById(id));
    }
    /**
     * 执行自动结转规则-执行记录
     */
    @RequiresPermissions("aa:record:add")
    @Log(title = "自动结转规则-执行记录", businessType = BusinessType.INSERT)
    @PostMapping("/addExecute")
    public AjaxResult addExecute(@RequestBody FinanceAaCarryRecord financeAaCarryRecord)
    {
        return success(financeAaCarryRecordService.insertFinanceAaCarryRecordExecute(financeAaCarryRecord));
    }
    /**
     * 取消修改自动结转规则-执行记录
     */
    @RequiresPermissions("aa:record:edit")
    @Log(title = "取消修改自动结转规则-执行记录", businessType = BusinessType.UPDATE)
    @PutMapping("/updateCancel")
    public AjaxResult updateCancel(@RequestBody FinanceAaCarryRecord financeAaCarryRecord)
    {
        return toAjax(financeAaCarryRecordService.updateCancelFinanceAaCarryRecord(financeAaCarryRecord));
    }
    /**
     * 新增自动结转规则-执行记录
     */
    @RequiresPermissions("aa:record:add")
    @Log(title = "自动结转规则-执行记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCarryRecord financeAaCarryRecord)
    {
        return toAjax(financeAaCarryRecordService.insertFinanceAaCarryRecord(financeAaCarryRecord));
    }

    /**
     * 修改自动结转规则-执行记录
     */
    @RequiresPermissions("aa:record:edit")
    @Log(title = "自动结转规则-执行记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCarryRecord financeAaCarryRecord)
    {
        return toAjax(financeAaCarryRecordService.updateFinanceAaCarryRecord(financeAaCarryRecord));
    }

    /**
     * 删除自动结转规则-执行记录
     */
    @RequiresPermissions("aa:record:remove")
    @Log(title = "自动结转规则-执行记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCarryRecordService.deleteFinanceAaCarryRecordByIds(ids));
    }
}
