package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtGroup;
import com.jlkj.finance.ft.service.IFinanceFtGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产大类基本资料Controller
 *
 * @author SunXuTong
 * @date 2023-06-21
 */
@RestController
@RequestMapping("/group")
public class FinanceFtGroupController extends BaseController
{
    @Autowired
    private IFinanceFtGroupService financeFtGroupService;

    /**
     * 查询资产大类基本资料列表
     * @param financeFtGroup 查询条件
     * @return 分页数据
     */
    @RequiresPermissions("finance:group:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtGroup financeFtGroup)
    {
        startPage();
        List<FinanceFtGroup> list = financeFtGroupService.selectFinanceFtGroupList(financeFtGroup);
        return getDataTable(list);
    }

    /**
     * 导出资产大类基本资料列表
     */
    @RequiresPermissions("finance:group:export")
    @Log(title = "资产大类基本资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtGroup financeFtGroup)
    {
        List<FinanceFtGroup> list = financeFtGroupService.selectFinanceFtGroupList(financeFtGroup);
        ExcelUtil<FinanceFtGroup> util = new ExcelUtil<FinanceFtGroup>(FinanceFtGroup.class);
        util.exportExcel(response, list, "资产大类基本资料数据");
    }

    /**
     * 获取资产大类基本资料详细信息
     */
    @RequiresPermissions("finance:group:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeFtGroupService.selectFinanceFtGroupById(id));
    }

    /**
     * 新增资产大类基本资料
     */
    @RequiresPermissions("finance:group:add")
    @Log(title = "资产大类基本资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FinanceFtGroup financeFtGroup)
    {
        return toAjax(financeFtGroupService.insertFinanceFtGroup(financeFtGroup));
    }

    /**
     * 修改资产大类基本资料
     */
    @RequiresPermissions("finance:group:edit")
    @Log(title = "资产大类基本资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtGroup financeFtGroup)
    {
        return toAjax(financeFtGroupService.updateFinanceFtGroup(financeFtGroup));
    }

    /**
     * 删除资产大类基本资料
     */
    @RequiresPermissions("finance:group:remove")
    @Log(title = "资产大类基本资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeFtGroupService.deleteFinanceFtGroupByIds(ids));
    }
}
