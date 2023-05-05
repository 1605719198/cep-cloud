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
import com.jlkj.finance.aa.domain.FinanceAaCashflowItemCompid;
import com.jlkj.finance.aa.service.IFinanceAaCashflowItemCompidService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 现金流量代码公司级-对应会计科目Controller
 *
 * @author 265799
 * @date 2023-05-04
 */
@RestController
@RequestMapping("/itemCompid")
public class FinanceAaCashflowItemCompidController extends BaseController
{
    @Autowired
    private IFinanceAaCashflowItemCompidService financeAaCashflowItemCompidService;

    /**
     * 查询现金流量代码公司级-对应会计科目列表
     */
    @RequiresPermissions("aa:itemCompid:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCashflowItemCompid financeAaCashflowItemCompid)
    {
        startPage();
        List<FinanceAaCashflowItemCompid> list = financeAaCashflowItemCompidService.selectFinanceAaCashflowItemCompidList(financeAaCashflowItemCompid);
        return getDataTable(list);
    }

    /**
     * 导出现金流量代码公司级-对应会计科目列表
     */
    @RequiresPermissions("aa:itemCompid:export")
    @Log(title = "现金流量代码公司级-对应会计科目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCashflowItemCompid financeAaCashflowItemCompid)
    {
        List<FinanceAaCashflowItemCompid> list = financeAaCashflowItemCompidService.selectFinanceAaCashflowItemCompidList(financeAaCashflowItemCompid);
        ExcelUtil<FinanceAaCashflowItemCompid> util = new ExcelUtil<FinanceAaCashflowItemCompid>(FinanceAaCashflowItemCompid.class);
        util.exportExcel(response, list, "现金流量代码公司级-对应会计科目数据");
    }

    /**
     * 获取现金流量代码公司级-对应会计科目详细信息
     */
    @RequiresPermissions("aa:itemCompid:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCashflowItemCompidService.selectFinanceAaCashflowItemCompidById(id));
    }

    /**
     * 新增现金流量代码公司级-对应会计科目
     */
    @RequiresPermissions("aa:itemCompid:add")
    @Log(title = "现金流量代码公司级-对应会计科目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCashflowItemCompid financeAaCashflowItemCompid)
    {
        return toAjax(financeAaCashflowItemCompidService.insertFinanceAaCashflowItemCompid(financeAaCashflowItemCompid));
    }

    /**
     * 修改现金流量代码公司级-对应会计科目
     */
    @RequiresPermissions("aa:itemCompid:edit")
    @Log(title = "现金流量代码公司级-对应会计科目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCashflowItemCompid financeAaCashflowItemCompid)
    {
        return toAjax(financeAaCashflowItemCompidService.updateFinanceAaCashflowItemCompid(financeAaCashflowItemCompid));
    }

    /**
     * 删除现金流量代码公司级-对应会计科目
     */
    @RequiresPermissions("aa:itemCompid:remove")
    @Log(title = "现金流量代码公司级-对应会计科目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCashflowItemCompidService.deleteFinanceAaCashflowItemCompidByIds(ids));
    }
}
