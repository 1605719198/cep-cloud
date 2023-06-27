package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.InnerAuth;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaAssetcategory;
import com.jlkj.finance.aa.service.IFinanceAaAssetcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 资产大类维护Controller
 *
 * @author SunXuTong
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/assetCategory")
public class FinanceAaAssetcategoryController extends BaseController
{
    @Autowired
    private IFinanceAaAssetcategoryService financeAaAssetcategoryService;

    /**
     * 查询资产大类维护列表
     */
    @RequiresPermissions("finance:assetCategory:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaAssetcategory financeAaAssetcategory)
    {
        startPage();
        List<FinanceAaAssetcategory> list = financeAaAssetcategoryService.selectFinanceAaAssetcategoryList(financeAaAssetcategory);
        return getDataTable(list);
    }

    /**
     * 导出资产大类维护列表
     */
    @RequiresPermissions("finance:assetCategory:export")
    @Log(title = "资产大类维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaAssetcategory financeAaAssetcategory)
    {
        List<FinanceAaAssetcategory> list = financeAaAssetcategoryService.selectFinanceAaAssetcategoryList(financeAaAssetcategory);
        ExcelUtil<FinanceAaAssetcategory> util = new ExcelUtil<FinanceAaAssetcategory>(FinanceAaAssetcategory.class);
        util.exportExcel(response, list, "资产大类维护数据");
    }

    /**
     * 获取资产大类维护详细信息
     */
    @RequiresPermissions("finance:assetCategory:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaAssetcategoryService.selectFinanceAaAssetcategoryById(id));
    }

    /**
     * 新增资产大类维护
     */
    @RequiresPermissions("finance:assetCategory:add")
    @Log(title = "资产大类维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaAssetcategory financeAaAssetcategory)
    {
        return toAjax(financeAaAssetcategoryService.insertFinanceAaAssetcategory(financeAaAssetcategory));
    }

    /**
     * 修改资产大类维护
     */
    @RequiresPermissions("finance:assetCategory:edit")
    @Log(title = "资产大类维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaAssetcategory financeAaAssetcategory)
    {
        return toAjax(financeAaAssetcategoryService.updateFinanceAaAssetcategory(financeAaAssetcategory));
    }

    /**
     * 删除资产大类维护
     */
    @RequiresPermissions("finance:assetCategory:remove")
    @Log(title = "资产大类维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaAssetcategoryService.deleteFinanceAaAssetcategoryByIds(ids));
    }

    /**
     * 查询资产大类(下拉选单用)- 远程调用暴露接口
     */
    @InnerAuth
    @GetMapping("/getAssetGroupFeign")
    public List<Map<String,String>> getAssetGroupFeign()
    {
        return financeAaAssetcategoryService.selectAssetGroupList();
    }
    /**
     * 查询资产大类(下拉选单用)
     */
    @GetMapping("/assetGroupList")
    public List<Map<String,String>> selectAssetGroupList()
    {
        return financeAaAssetcategoryService.selectAssetGroupList();
    }
}
