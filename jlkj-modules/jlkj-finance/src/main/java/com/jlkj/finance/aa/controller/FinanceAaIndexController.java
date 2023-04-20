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
import com.jlkj.finance.aa.domain.FinanceAaIndex;
import com.jlkj.finance.aa.service.IFinanceAaIndexService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 系统选单-索引设定Controller
 *
 * @author 265799
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/index")
public class FinanceAaIndexController extends BaseController
{
    @Autowired
    private IFinanceAaIndexService financeAaIndexService;

    /**
     * 查询系统选单-索引设定列表
     */
    @RequiresPermissions("aa:index:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaIndex financeAaIndex)
    {
        startPage();
        List<FinanceAaIndex> list = financeAaIndexService.selectFinanceAaIndexList(financeAaIndex);
        return getDataTable(list);
    }

    /**
     * 导出系统选单-索引设定列表
     */
    @RequiresPermissions("aa:index:export")
    @Log(title = "系统选单-索引设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaIndex financeAaIndex)
    {
        List<FinanceAaIndex> list = financeAaIndexService.selectFinanceAaIndexList(financeAaIndex);
        ExcelUtil<FinanceAaIndex> util = new ExcelUtil<FinanceAaIndex>(FinanceAaIndex.class);
        util.exportExcel(response, list, "系统选单-索引设定数据");
    }

    /**
     * 获取系统选单-索引设定详细信息
     */
    @RequiresPermissions("aa:index:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaIndexService.selectFinanceAaIndexById(id));
    }

    /**
     * 新增系统选单-索引设定
     */
    @RequiresPermissions("aa:index:add")
    @Log(title = "系统选单-索引设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaIndex financeAaIndex)
    {
        financeAaIndex.setCreateBy(getUsername());
        return toAjax(financeAaIndexService.insertFinanceAaIndex(financeAaIndex));
    }

    /**
     * 修改系统选单-索引设定
     */
    @RequiresPermissions("aa:index:edit")
    @Log(title = "系统选单-索引设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaIndex financeAaIndex)
    {
        financeAaIndex.setUpdateBy(getUsername());
        return toAjax(financeAaIndexService.updateFinanceAaIndex(financeAaIndex));
    }

    /**
     * 删除系统选单-索引设定
     */
    @RequiresPermissions("aa:index:remove")
    @Log(title = "系统选单-索引设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaIndexService.deleteFinanceAaIndexByIds(ids));
    }
}
