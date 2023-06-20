package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtPara;
import com.jlkj.finance.ft.service.IFinanceFtParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 固定资产参数维护Controller
 *
 * @author SunXuTong
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/ft/para")
public class FinanceFtParaController extends BaseController
{
    @Autowired
    private IFinanceFtParaService financeFtParaService;

    /**
     * 查询固定资产参数维护列表
     */
    @RequiresPermissions("finance:ft:para:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtPara financeFtPara)
    {
        startPage();
        List<FinanceFtPara> list = financeFtParaService.selectFinanceFtParaList(financeFtPara);
        return getDataTable(list);
    }

    /**
     * 导出固定资产参数维护列表
     */
    @RequiresPermissions("finance:ft:para:export")
    @Log(title = "固定资产参数维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtPara financeFtPara)
    {
        List<FinanceFtPara> list = financeFtParaService.selectFinanceFtParaList(financeFtPara);
        ExcelUtil<FinanceFtPara> util = new ExcelUtil<FinanceFtPara>(FinanceFtPara.class);
        util.exportExcel(response, list, "固定资产参数维护数据");
    }

    /**
     * 获取固定资产参数维护详细信息
     */
    @RequiresPermissions("finance:ft:para:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeFtParaService.selectFinanceFtParaById(id));
    }

    /**
     * 新增固定资产参数维护
     */
    @RequiresPermissions("finance:ft:para:add")
    @Log(title = "固定资产参数维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtPara financeFtPara)
    {
        return toAjax(financeFtParaService.insertFinanceFtPara(financeFtPara));
    }

    /**
     * 修改固定资产参数维护
     */
    @RequiresPermissions("finance:ft:para:edit")
    @Log(title = "固定资产参数维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtPara financeFtPara)
    {
        return toAjax(financeFtParaService.updateFinanceFtPara(financeFtPara));
    }

    /**
     * 删除固定资产参数维护
     */
    @RequiresPermissions("finance:ft:para:remove")
    @Log(title = "固定资产参数维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeFtParaService.deleteFinanceFtParaByIds(ids));
    }
}
