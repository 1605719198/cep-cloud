package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtDictData;
import com.jlkj.finance.ft.service.IFinanceFtDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 选单内容设定Controller
 *
 * @author SunXuTong
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/dictData")
public class FinanceFtDictDataController extends BaseController
{
    @Autowired
    private IFinanceFtDictDataService financeFtDictDataService;

    /**
     * 查询选单内容设定列表
     */
    @RequiresPermissions("finance:dictData:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtDictData financeFtDictData)
    {
        startPage();
        List<FinanceFtDictData> list = financeFtDictDataService.selectFinanceFtDictDataList(financeFtDictData);
        return getDataTable(list);
    }

    /**
     * 导出选单内容设定列表
     */
    @RequiresPermissions("finance:dictData:export")
    @Log(title = "选单内容设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtDictData financeFtDictData)
    {
        List<FinanceFtDictData> list = financeFtDictDataService.selectFinanceFtDictDataList(financeFtDictData);
        ExcelUtil<FinanceFtDictData> util = new ExcelUtil<FinanceFtDictData>(FinanceFtDictData.class);
        util.exportExcel(response, list, "选单内容设定数据");
    }

    /**
     * 获取选单内容设定详细信息
     */
    @RequiresPermissions("finance:dictData:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtDictDataService.selectFinanceFtDictDataByUuid(uuid));
    }

    /**
     * 新增选单内容设定
     */
    @RequiresPermissions("finance:dictData:add")
    @Log(title = "选单内容设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtDictData financeFtDictData)
    {
        return toAjax(financeFtDictDataService.insertFinanceFtDictData(financeFtDictData));
    }

    /**
     * 修改选单内容设定
     */
    @RequiresPermissions("finance:dictData:edit")
    @Log(title = "选单内容设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtDictData financeFtDictData)
    {
        return toAjax(financeFtDictDataService.updateFinanceFtDictData(financeFtDictData));
    }

    /**
     * 删除选单内容设定
     */
    @RequiresPermissions("finance:dictData:remove")
    @Log(title = "选单内容设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtDictDataService.deleteFinanceFtDictDataByUuids(uuids));
    }
}
