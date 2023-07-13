package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApConfirmDetail;
import com.jlkj.finance.ap.service.IFinanceApConfirmDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会计确认明细Controller
 * @Description:
 * @author jlkj
 * @date 2023-07-10
 * @version:V1.0
 */
@RestController
@RequestMapping("ap/detail")
public class FinanceApConfirmDetailController extends BaseController
{
    @Autowired
    private IFinanceApConfirmDetailService financeApConfirmDetailService;

    /**
     * 查询会计确认明细列表
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail
     * @return
     */
    @RequiresPermissions("ap:detail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApConfirmDetail financeApConfirmDetail)
    {
        startPage();
        List<FinanceApConfirmDetail> list = financeApConfirmDetailService.selectFinanceApConfirmDetailList(financeApConfirmDetail);
        return getDataTable(list);
    }

    /**
     * 导出会计确认明细列表
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail
     * @return
     */
    @RequiresPermissions("ap:detail:export")
    @Log(title = "会计确认明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApConfirmDetail financeApConfirmDetail)
    {
        List<FinanceApConfirmDetail> list = financeApConfirmDetailService.selectFinanceApConfirmDetailList(financeApConfirmDetail);
        ExcelUtil<FinanceApConfirmDetail> util = new ExcelUtil<FinanceApConfirmDetail>(FinanceApConfirmDetail.class);
        util.exportExcel(response, list, "会计确认明细数据");
    }

    /**
     * 获取会计确认明细详细信息
     * @author jlkj
     * @date 2023-07-10
     * @param billId
     * @return
     */
    @RequiresPermissions("ap:detail:query")
    @GetMapping(value = "/{billId}")
    public AjaxResult getInfo(@PathVariable("billId") String billId)
    {
        return success(financeApConfirmDetailService.selectFinanceApConfirmDetailByBillId(billId));
    }

    /**
     * 新增会计确认明细
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail
     * @return
     */
    @RequiresPermissions("ap:detail:add")
    @Log(title = "会计确认明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApConfirmDetail financeApConfirmDetail)
    {
        return toAjax(financeApConfirmDetailService.insertFinanceApConfirmDetail(financeApConfirmDetail));
    }

    /**
     * 修改会计确认明细
     * @author jlkj
     * @date 2023-07-10
     * @param financeApConfirmDetail
     * @return
     */
    @RequiresPermissions("ap:detail:edit")
    @Log(title = "会计确认明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApConfirmDetail financeApConfirmDetail)
    {
        return toAjax(financeApConfirmDetailService.updateFinanceApConfirmDetail(financeApConfirmDetail));
    }

    /**
     * 删除会计确认明细
     * @author jlkj
     * @date 2023-07-10
     * @param billIds
     * @return
     */
    @RequiresPermissions("ap:detail:remove")
    @Log(title = "会计确认明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{billIds}")
    public AjaxResult remove(@PathVariable String[] billIds)
    {
        return toAjax(financeApConfirmDetailService.deleteFinanceApConfirmDetailByBillIds(billIds));
    }
}
