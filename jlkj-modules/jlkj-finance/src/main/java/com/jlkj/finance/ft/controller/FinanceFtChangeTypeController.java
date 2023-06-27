package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtChangeType;
import com.jlkj.finance.ft.service.IFinanceFtChangeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 固定资产变动方式Controller
 *
 * @author SunXuTong
 * @date 2023-06-26
 */
@RestController
@RequestMapping("/changeType")
public class FinanceFtChangeTypeController extends BaseController
{
    @Autowired
    private IFinanceFtChangeTypeService financeFtChangeTypeService;

    /**
     * 查询固定资产变动方式列表
     */
    @RequiresPermissions("finance:changeType:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtChangeType financeFtChangeType)
    {
        startPage();
        List<FinanceFtChangeType> list = financeFtChangeTypeService.selectFinanceFtChangeTypeList(financeFtChangeType);
        return getDataTable(list);
    }

    /**
     * 导出固定资产变动方式列表
     */
    @RequiresPermissions("finance:changeType:export")
    @Log(title = "固定资产变动方式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtChangeType financeFtChangeType)
    {
        List<FinanceFtChangeType> list = financeFtChangeTypeService.selectFinanceFtChangeTypeList(financeFtChangeType);
        ExcelUtil<FinanceFtChangeType> util = new ExcelUtil<FinanceFtChangeType>(FinanceFtChangeType.class);
        util.exportExcel(response, list, "固定资产变动方式数据");
    }

    /**
     * 获取固定资产变动方式详细信息
     */
    @RequiresPermissions("finance:changeType:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtChangeTypeService.selectFinanceFtChangeTypeByUuid(uuid));
    }

    /**
     * 新增固定资产变动方式
     */
    @RequiresPermissions("finance:changeType:add")
    @Log(title = "固定资产变动方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtChangeType financeFtChangeType)
    {
        return toAjax(financeFtChangeTypeService.insertFinanceFtChangeType(financeFtChangeType));
    }

    /**
     * 修改固定资产变动方式
     */
    @RequiresPermissions("finance:changeType:edit")
    @Log(title = "固定资产变动方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtChangeType financeFtChangeType)
    {
        return toAjax(financeFtChangeTypeService.updateFinanceFtChangeType(financeFtChangeType));
    }

    /**
     * 删除固定资产变动方式
     */
    @RequiresPermissions("finance:changeType:remove")
    @Log(title = "固定资产变动方式", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtChangeTypeService.deleteFinanceFtChangeTypeByUuids(uuids));
    }
}
