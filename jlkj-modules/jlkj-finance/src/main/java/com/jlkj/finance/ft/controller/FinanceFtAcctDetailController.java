package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtAcctDetail;
import com.jlkj.finance.ft.service.IFinanceFtAcctDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账务代码明细设定Controller
 * @Description:
 * @author SunXuTong
 * @date 2023-06-30
 * @version:V1.0
 */
@RestController
@RequestMapping("/acctDetail")
public class FinanceFtAcctDetailController extends BaseController
{
    @Autowired
    private IFinanceFtAcctDetailService financeFtAcctDetailService;

    /**
     * 查询账务代码明细设定列表
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail
     * @return
     */
    @RequiresPermissions("finance:acctDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtAcctDetail financeFtAcctDetail)
    {
        startPage();
        List<FinanceFtAcctDetail> list = financeFtAcctDetailService.selectFinanceFtAcctDetailList(financeFtAcctDetail);
        return getDataTable(list);
    }

    /**
     * 导出账务代码明细设定列表
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail
     * @return
     */
    @RequiresPermissions("finance:acctDetail:export")
    @Log(title = "账务代码明细设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtAcctDetail financeFtAcctDetail)
    {
        List<FinanceFtAcctDetail> list = financeFtAcctDetailService.selectFinanceFtAcctDetailList(financeFtAcctDetail);
        ExcelUtil<FinanceFtAcctDetail> util = new ExcelUtil<FinanceFtAcctDetail>(FinanceFtAcctDetail.class);
        util.exportExcel(response, list, "账务代码明细设定数据");
    }

    /**
     * 获取账务代码明细设定详细信息
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail
     * @return
     */
    @RequiresPermissions("finance:acctDetail:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtAcctDetailService.selectFinanceFtAcctDetailByUuid(uuid));
    }

    /**
     * 新增账务代码明细设定
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail
     * @return
     */
    @RequiresPermissions("finance:acctDetail:add")
    @Log(title = "账务代码明细设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtAcctDetail financeFtAcctDetail)
    {
        return toAjax(financeFtAcctDetailService.insertFinanceFtAcctDetail(financeFtAcctDetail));
    }

    /**
     * 修改账务代码明细设定
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail
     * @return
     */
    @RequiresPermissions("finance:acctDetail:edit")
    @Log(title = "账务代码明细设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtAcctDetail financeFtAcctDetail)
    {
        return toAjax(financeFtAcctDetailService.updateFinanceFtAcctDetail(financeFtAcctDetail));
    }

    /**
     * 删除账务代码明细设定
     * @author SunXuTong
     * @date 2023-06-30
     * @param financeFtAcctDetail
     * @return
     */
    @RequiresPermissions("finance:acctDetail:remove")
    @Log(title = "账务代码明细设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtAcctDetailService.deleteFinanceFtAcctDetailByUuids(uuids));
    }
}
