package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtStatus;
import com.jlkj.finance.ft.service.IFinanceFtStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产使用状态Controller
 * @Description:
 * @author SunXuTong
 * @date 2023-07-03
 * @version:V1.0
 */
@RestController
@RequestMapping("/status")
public class FinanceFtStatusController extends BaseController
{
    @Autowired
    private IFinanceFtStatusService financeFtStatusService;

    /**
     * 查询资产使用状态列表
     * @author SunXuTong
     * @date 2023-07-03
     * @return
     */
    @RequiresPermissions("finance:status:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtStatus financeFtStatus)
    {
        startPage();
        List<FinanceFtStatus> list = financeFtStatusService.selectFinanceFtStatusList(financeFtStatus);
        return getDataTable(list);
    }

    /**
     * 导出资产使用状态列表
     * @author SunXuTong
     * @date 2023-07-03
     * @return
     */
    @RequiresPermissions("finance:status:export")
    @Log(title = "资产使用状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtStatus financeFtStatus)
    {
        List<FinanceFtStatus> list = financeFtStatusService.selectFinanceFtStatusList(financeFtStatus);
        ExcelUtil<FinanceFtStatus> util = new ExcelUtil<FinanceFtStatus>(FinanceFtStatus.class);
        util.exportExcel(response, list, "资产使用状态数据");
    }

    /**
     * 获取资产使用状态详细信息
     * @author SunXuTong
     * @date 2023-07-03
     * @return
     */
    @RequiresPermissions("finance:status:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtStatusService.selectFinanceFtStatusByUuid(uuid));
    }

    /**
     * 新增资产使用状态
     * @author SunXuTong
     * @date 2023-07-03
     * @return
     */
    @RequiresPermissions("finance:status:add")
    @Log(title = "资产使用状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtStatus financeFtStatus)
    {
        return toAjax(financeFtStatusService.insertFinanceFtStatus(financeFtStatus));
    }

    /**
     * 修改资产使用状态
     * @author SunXuTong
     * @date 2023-07-03
     * @return
     */
    @RequiresPermissions("finance:status:edit")
    @Log(title = "资产使用状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtStatus financeFtStatus)
    {
        return toAjax(financeFtStatusService.updateFinanceFtStatus(financeFtStatus));
    }

    /**
     * 删除资产使用状态
     * @author SunXuTong
     * @date 2023-07-03
     * @return
     */
    @RequiresPermissions("finance:status:remove")
    @Log(title = "资产使用状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtStatusService.deleteFinanceFtStatusByUuids(uuids));
    }
}
