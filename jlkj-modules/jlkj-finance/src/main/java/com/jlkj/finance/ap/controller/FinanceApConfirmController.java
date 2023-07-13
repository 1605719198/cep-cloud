package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApConfirm;
import com.jlkj.finance.ap.service.IFinanceApConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会计确认主档Controller
 * @Description:
 * @author 265799
 * @date 2023-07-10
 * @version:V1.0
 */
@RestController
@RequestMapping("/confirm")
public class FinanceApConfirmController extends BaseController
{
    @Autowired
    private IFinanceApConfirmService financeApConfirmService;

    /**
     * 查询会计确认主档列表
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm
     * @return
     */
    @RequiresPermissions("ap:confirm:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApConfirm financeApConfirm)
    {
        startPage();
        List<FinanceApConfirm> list = financeApConfirmService.selectFinanceApConfirmList(financeApConfirm);
        return getDataTable(list);
    }

    /**
     * 导出会计确认主档列表
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm
     * @return
     */
    @RequiresPermissions("ap:confirm:export")
    @Log(title = "会计确认主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApConfirm financeApConfirm)
    {
        List<FinanceApConfirm> list = financeApConfirmService.selectFinanceApConfirmList(financeApConfirm);
        ExcelUtil<FinanceApConfirm> util = new ExcelUtil<FinanceApConfirm>(FinanceApConfirm.class);
        util.exportExcel(response, list, "会计确认主档数据");
    }

    /**
     * 获取会计确认主档详细信息
     * @author 265799
     * @date 2023-07-10
     * @param id
     * @return
     */
    @RequiresPermissions("ap:confirm:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApConfirmService.selectFinanceApConfirmById(id));
    }

    /**
     * 新增会计确认主档
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm
     * @return
     */
    @RequiresPermissions("ap:confirm:add")
    @Log(title = "会计确认主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApConfirm financeApConfirm)
    {
        return toAjax(financeApConfirmService.insertFinanceApConfirm(financeApConfirm));
    }

    /**
     * 修改会计确认主档
     * @author 265799
     * @date 2023-07-10
     * @param financeApConfirm
     * @return
     */
    @RequiresPermissions("ap:confirm:edit")
    @Log(title = "会计确认主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApConfirm financeApConfirm)
    {
        return toAjax(financeApConfirmService.updateFinanceApConfirm(financeApConfirm));
    }

    /**
     * 删除会计确认主档
     * @author 265799
     * @date 2023-07-10
     * @param ids
     * @return
     */
    @RequiresPermissions("ap:confirm:remove")
    @Log(title = "会计确认主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApConfirmService.deleteFinanceApConfirmByIds(ids));
    }
}
