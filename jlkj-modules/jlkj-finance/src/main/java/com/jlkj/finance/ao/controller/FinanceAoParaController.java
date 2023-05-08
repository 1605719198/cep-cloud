package com.jlkj.finance.ao.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ao.domain.FinanceAoPara;
import com.jlkj.finance.ao.service.IFinanceAoParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报支参数维护Controller
 *
 * @author SunXuTong
 * @date 2023-05-06
 */
@RestController
@RequestMapping("/ao/para")
public class FinanceAoParaController extends BaseController
{
    @Autowired
    private IFinanceAoParaService financeAoParaService;

    /**
     * 查询报支参数维护列表
     */
    @RequiresPermissions("finance:ao:para:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAoPara financeAoPara)
    {
        startPage();
        List<FinanceAoPara> list = financeAoParaService.selectFinanceAoParaList(financeAoPara);
        return getDataTable(list);
    }

    /**
     * 导出报支参数维护列表
     */
    @RequiresPermissions("finance:ao:para:export")
    @Log(title = "报支参数维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAoPara financeAoPara)
    {
        List<FinanceAoPara> list = financeAoParaService.selectFinanceAoParaList(financeAoPara);
        ExcelUtil<FinanceAoPara> util = new ExcelUtil<FinanceAoPara>(FinanceAoPara.class);
        util.exportExcel(response, list, "报支参数维护数据");
    }

    /**
     * 获取报支参数维护详细信息
     */
    @RequiresPermissions("finance:ao:para:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAoParaService.selectFinanceAoParaById(id));
    }

    /**
     * 新增报支参数维护
     */
    @RequiresPermissions("finance:ao:para:add")
    @Log(title = "报支参数维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAoPara financeAoPara)
    {
        return toAjax(financeAoParaService.insertFinanceAoPara(financeAoPara));
    }

    /**
     * 修改报支参数维护
     */
    @RequiresPermissions("finance:ao:para:edit")
    @Log(title = "报支参数维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAoPara financeAoPara)
    {
        return toAjax(financeAoParaService.updateFinanceAoPara(financeAoPara));
    }

    /**
     * 删除报支参数维护
     */
    @RequiresPermissions("finance:ao:para:remove")
    @Log(title = "报支参数维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAoParaService.deleteFinanceAoParaByIds(ids));
    }

//    /**
//     * 刷新参数缓存
//     */
//    @RequiresPermissions("finance:ao:para:remove")
//    @Log(title = "报支参数维护", businessType = BusinessType.CLEAN)
//    @DeleteMapping("/refreshCache")
//    public AjaxResult refreshCache()
//    {
//        return success();
//    }

}
