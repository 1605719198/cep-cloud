package com.jlkj.finance.ap.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ap.domain.FinanceApPara;
import com.jlkj.finance.ap.service.IFinanceApParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 应付参数维护Controller
 *
 * @author SunXuTong
 * @date 2023-05-15
 */
@RestController
@RequestMapping("/ap/para")
public class FinanceApParaController extends BaseController
{
    @Autowired
    private IFinanceApParaService financeApParaService;

    /**
     * 查询应付参数维护列表
     */
    @RequiresPermissions("finance:ap:para:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceApPara financeApPara)
    {
        startPage();
        List<FinanceApPara> list = financeApParaService.selectFinanceApParaList(financeApPara);
        return getDataTable(list);
    }

    /**
     * 导出应付参数维护列表
     */
    @RequiresPermissions("finance:ap:para:export")
    @Log(title = "应付参数维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceApPara financeApPara)
    {
        List<FinanceApPara> list = financeApParaService.selectFinanceApParaList(financeApPara);
        ExcelUtil<FinanceApPara> util = new ExcelUtil<FinanceApPara>(FinanceApPara.class);
        util.exportExcel(response, list, "应付参数维护数据");
    }

    /**
     * 获取应付参数维护详细信息
     */
    @RequiresPermissions("finance:ap:para:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeApParaService.selectFinanceApParaById(id));
    }

    /**
     * 新增应付参数维护
     */
    @RequiresPermissions("finance:ap:para:add")
    @Log(title = "应付参数维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceApPara financeApPara)
    {
        return toAjax(financeApParaService.insertFinanceApPara(financeApPara));
    }

    /**
     * 修改应付参数维护
     */
    @RequiresPermissions("finance:ap:para:edit")
    @Log(title = "应付参数维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceApPara financeApPara)
    {
        return toAjax(financeApParaService.updateFinanceApPara(financeApPara));
    }

    /**
     * 删除应付参数维护
     */
    @RequiresPermissions("finance:ap:para:remove")
    @Log(title = "应付参数维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeApParaService.deleteFinanceApParaByIds(ids));
    }
}
