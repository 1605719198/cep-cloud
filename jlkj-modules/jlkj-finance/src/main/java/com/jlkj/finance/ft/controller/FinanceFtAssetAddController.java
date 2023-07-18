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
import com.jlkj.finance.ft.service.IFinanceFtAssetAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产增加单Controller
 * @Description:
 * @author SunXuTong
 * @date 2023-07-11
 * @version:V1.0
 */
@RestController
@RequestMapping("/assetAdd")
public class FinanceFtAssetAddController extends BaseController
{
    @Autowired
    private IFinanceFtAssetAddService financeFtAssetAddService;

    /**
     * 查询资产增加单列表
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAdd
     * @return
     */
    @RequiresPermissions("finance:assetAdd:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtChange financeFtChange)
    {
        startPage();
        List<FinanceFtChange> list = financeFtAssetAddService.selectFinanceFtChangeList(financeFtChange);
        return getDataTable(list);
    }

    /**
     * 导出资产增加单列表
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtChange
     * @return
     */
    @RequiresPermissions("finance:assetAdd:export")
    @Log(title = "资产增加单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtChange financeFtChange)
    {
        List<FinanceFtChange> list = financeFtAssetAddService.selectFinanceFtChangeList(financeFtChange);
        ExcelUtil<FinanceFtChange> util = new ExcelUtil<FinanceFtChange>(FinanceFtChange.class);
        util.exportExcel(response, list, "资产增加单数据");
    }

    /**
     * 获取资产增加单详细信息
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtChange
     * @return
     */
    @RequiresPermissions("finance:assetAdd:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtAssetAddService.selectFinanceFtChangeByUuid(uuid));
    }

    /**
     * 新增资产增加单
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAddDTO
     * @return
     */
    @RequiresPermissions("finance:assetAdd:add")
    @Log(title = "资产增加单", businessType = BusinessType.INSERT)
    @PostMapping
    public Object add(@RequestBody FinanceFtAssetAddDTO financeFtAssetAddDTO)
    {
        return financeFtAssetAddService.insertFinanceFtChange(financeFtAssetAddDTO);
    }

    /**
     * 修改资产增加单
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAdd
     * @return
     */
    @RequiresPermissions("finance:assetAdd:edit")
    @Log(title = "资产增加单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtChange financeFtChange)
    {
        return toAjax(financeFtAssetAddService.updateFinanceFtChange(financeFtChange));
    }

    /**
     * 删除资产增加单
     * @author SunXuTong
     * @date 2023-07-11
     * @param financeFtAssetAdd
     * @return
     */
    @RequiresPermissions("finance:assetAdd:remove")
    @Log(title = "资产增加单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtAssetAddService.deleteFinanceFtChangeByUuids(uuids));
    }
}
