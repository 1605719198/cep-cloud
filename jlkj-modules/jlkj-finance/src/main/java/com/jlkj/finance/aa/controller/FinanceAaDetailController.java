package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.finance.aa.domain.FinanceAaBase;
import com.jlkj.finance.aa.dto.FinanceAaDetailDTO;
import com.jlkj.finance.aa.service.IFinanceAaBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaDetail;
import com.jlkj.finance.aa.service.IFinanceAaDetailService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 系统选单-明细设定Controller
 *
 * @author 265799
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/detail")
public class FinanceAaDetailController extends BaseController
{
    @Autowired
    private IFinanceAaDetailService financeAaDetailService;
    @Autowired
    private IFinanceAaBaseService financeAaBaseService;

    /**
     * 查询系统选单-明细设定列表
     */
    @RequiresPermissions("aa:detail:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaDetail financeAaDetail)
    {
        startPage();
        List<FinanceAaDetail> list = financeAaDetailService.selectFinanceAaDetailList(financeAaDetail);
        return getDataTable(list);
    }

    /**
     * 导出系统选单-明细设定列表
     */
    @RequiresPermissions("aa:detail:export")
    @Log(title = "系统选单-明细设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaDetail financeAaDetail)
    {
        List<FinanceAaDetail> list = financeAaDetailService.selectFinanceAaDetailList(financeAaDetail);
        ExcelUtil<FinanceAaDetail> util = new ExcelUtil<FinanceAaDetail>(FinanceAaDetail.class);
        util.exportExcel(response, list, "系统选单-明细设定数据");
    }

    /**
     * 获取系统选单-明细设定详细信息
     */
    @RequiresPermissions("aa:detail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaDetailService.selectFinanceAaDetailById(id));
    }

    /**
     * 新增系统选单-明细设定
     */
    @RequiresPermissions("aa:detail:add")
    @Log(title = "系统选单-明细设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaDetailDTO financeAaDetailDTO)
    {
        List<FinanceAaDetailDTO> detailList = financeAaDetailDTO.getFinanceDetailList();
        return toAjax(financeAaDetailService.insertFinanceAaDetail(detailList));
    }

    /**
     * 修改系统选单-明细设定
     */
    @RequiresPermissions("aa:detail:edit")
    @Log(title = "系统选单-明细设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaDetail financeAaDetail)
    {

        return toAjax(financeAaDetailService.updateFinanceAaDetail(financeAaDetail));
    }

    /**
     * 删除系统选单-明细设定
     */
    @RequiresPermissions("aa:detail:remove")
    @Log(title = "系统选单-明细设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String[] id)
    {
        return toAjax(financeAaDetailService.deleteFinanceAaDetailByIds(id));
    }
}
