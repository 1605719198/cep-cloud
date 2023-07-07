package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtStatusDetail;
import com.jlkj.finance.ft.service.IFinanceFtStatusDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产使用状态明细设定Controller
 * @Description:
 * @author SunXuTong
 * @date 2023-07-06
 * @version:V1.0
 */
@RestController
@RequestMapping("/statusDetail")
public class FinanceFtStatusDetailController extends BaseController
{
    @Autowired
    private IFinanceFtStatusDetailService financeFtStatusDetailService;

    /**
     * 查询资产使用状态明细设定列表
     * @author SunXuTong
     * @date 2023-07-06
     * @param financeFtStatusDetail
     * @return
     */
    @RequiresPermissions("finance:statusDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtStatusDetail financeFtStatusDetail)
    {
        startPage();
        List<FinanceFtStatusDetail> list = financeFtStatusDetailService.selectFinanceFtStatusDetailList(financeFtStatusDetail);
        return getDataTable(list);
    }

    /**
     * 导出资产使用状态明细设定列表
     * @author SunXuTong
     * @date 2023-07-06
     * @param financeFtStatusDetail
     * @return
     */
    @RequiresPermissions("finance:statusDetail:export")
    @Log(title = "资产使用状态明细设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtStatusDetail financeFtStatusDetail)
    {
        List<FinanceFtStatusDetail> list = financeFtStatusDetailService.selectFinanceFtStatusDetailList(financeFtStatusDetail);
        ExcelUtil<FinanceFtStatusDetail> util = new ExcelUtil<FinanceFtStatusDetail>(FinanceFtStatusDetail.class);
        util.exportExcel(response, list, "资产使用状态明细设定数据");
    }

    /**
     * 获取资产使用状态明细设定详细信息
     * @author SunXuTong
     * @date 2023-07-06
     * @return
     */
    @RequiresPermissions("finance:statusDetail:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtStatusDetailService.selectFinanceFtStatusDetailByUuid(uuid));
    }

    /**
     * 新增资产使用状态明细设定
     * @author SunXuTong
     * @date 2023-07-06
     * @param financeFtStatusDetail
     * @return
     */
    @RequiresPermissions("finance:statusDetail:add")
    @Log(title = "资产使用状态明细设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtStatusDetail financeFtStatusDetail)
    {
        return toAjax(financeFtStatusDetailService.insertFinanceFtStatusDetail(financeFtStatusDetail));
    }

    /**
     * 修改资产使用状态明细设定
     * @author SunXuTong
     * @date 2023-07-06
     * @param financeFtStatusDetail
     * @return
     */
    @RequiresPermissions("finance:statusDetail:edit")
    @Log(title = "资产使用状态明细设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtStatusDetail financeFtStatusDetail)
    {
        return toAjax(financeFtStatusDetailService.updateFinanceFtStatusDetail(financeFtStatusDetail));
    }

    /**
     * 删除资产使用状态明细设定
     * @author SunXuTong
     * @date 2023-07-06
     * @return
     */
    @RequiresPermissions("finance:statusDetail:remove")
    @Log(title = "资产使用状态明细设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtStatusDetailService.deleteFinanceFtStatusDetailByUuids(uuids));
    }
}
