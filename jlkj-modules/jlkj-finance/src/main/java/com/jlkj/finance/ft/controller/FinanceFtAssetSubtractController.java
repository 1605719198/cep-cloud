package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.dto.FinanceFtAssetAddDTO;
import com.jlkj.finance.ft.service.IFinanceFtAssetSubtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产减少单Controller
 * @Description:
 * @author SunXuTong
 * @date 2023-07-15
 * @version:V1.0
 */
@RestController
@RequestMapping("/assetSubtract")
public class FinanceFtAssetSubtractController extends BaseController
{
    @Autowired
    private IFinanceFtAssetSubtractService financeFtAssetSubtractService;

    /**
     * 查询资产减少单列表
     * @author SunXuTong
     * @date 2023-07-15
     * @param financeFtChange
     * @return
     */
    @RequiresPermissions("finance:assetSubtract:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtChange financeFtChange)
    {
        startPage();
        List<FinanceFtChange> list = financeFtAssetSubtractService.selectFinanceFtChangeList(financeFtChange);
        return getDataTable(list);
    }

    /**
     * 导出资产减少单列表
     * @author SunXuTong
     * @date 2023-07-15
     * @param financeFtChange
     * @return
     */
    @RequiresPermissions("finance:assetSubtract:export")
    @Log(title = "资产减少单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtChange financeFtChange)
    {
        List<FinanceFtChange> list = financeFtAssetSubtractService.selectFinanceFtChangeList(financeFtChange);
        ExcelUtil<FinanceFtChange> util = new ExcelUtil<FinanceFtChange>(FinanceFtChange.class);
        util.exportExcel(response, list, "资产减少单数据");
    }

    /**
     * 获取资产减少单详细信息
     * @author SunXuTong
     * @date 2023-07-15
     * @param
     * @return
     */
    @RequiresPermissions("finance:assetSubtract:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtAssetSubtractService.selectFinanceFtChangeByUuid(uuid));
    }

    /**
     * 新增资产减少单
     * @author SunXuTong
     * @date 2023-07-15
     * @param
     * @return
     */
    @RequiresPermissions("finance:assetSubtract:add")
    @Log(title = "资产减少单", businessType = BusinessType.INSERT)
    @PostMapping
    public Object add(@RequestBody FinanceFtAssetAddDTO financeFtAssetAddDTO)
    {
        return financeFtAssetSubtractService.insertFinanceFtChange(financeFtAssetAddDTO);
    }

    /**
     * 修改资产减少单
     * @author SunXuTong
     * @date 2023-07-15
     * @param
     * @return
     */
    @RequiresPermissions("finance:assetSubtract:edit")
    @Log(title = "资产减少单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtChange financeFtChange)
    {
        return toAjax(financeFtAssetSubtractService.updateFinanceFtChange(financeFtChange));
    }

    /**
     * 删除资产减少单
     * @author SunXuTong
     * @date 2023-07-15
     * @param
     * @return
     */
    @RequiresPermissions("finance:assetSubtract:remove")
    @Log(title = "资产减少单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtAssetSubtractService.deleteFinanceFtChangeByUuids(uuids));
    }
}
