package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
import com.jlkj.finance.aa.domain.FinanceAaPara;
import com.jlkj.finance.aa.service.IFinanceAaParaService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 会计系统参数维护Controller
 *
 * @author 265799
 * @date 2023-04-13
 */
@RestController
@RequestMapping("/para")
public class FinanceAaParaController extends BaseController
{
    @Autowired
    private IFinanceAaParaService financeAaParaService;

    /**
     * 查询会计系统参数维护列表
     */
    @RequiresPermissions("aa:para:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaPara financeAaPara)
    {
        startPage();
        List<FinanceAaPara> list = financeAaParaService.selectFinanceAaParaList(financeAaPara);
        return getDataTable(list);
    }

    /**
     * 导出会计系统参数维护列表
     */
    @RequiresPermissions("aa:para:export")
    @Log(title = "会计系统参数维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaPara financeAaPara)
    {
        List<FinanceAaPara> list = financeAaParaService.selectFinanceAaParaList(financeAaPara);
        ExcelUtil<FinanceAaPara> util = new ExcelUtil<FinanceAaPara>(FinanceAaPara.class);
        util.exportExcel(response, list, "会计系统参数维护数据");
    }

    /**
     * 获取会计系统参数维护详细信息
     */
    @RequiresPermissions("aa:para:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaParaService.selectFinanceAaParaById(id));
    }

    /**
     * 新增会计系统参数维护
     */
    @RequiresPermissions("aa:para:add")
    @Log(title = "会计系统参数维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaPara financeAaPara)
    {
        financeAaPara.setCreateBy(getUsername());
        return toAjax(financeAaParaService.insertFinanceAaPara(financeAaPara));
    }

    /**
     * 修改会计系统参数维护
     */
    @RequiresPermissions("aa:para:edit")
    @Log(title = "会计系统参数维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaPara financeAaPara)
    {
        financeAaPara.setUpdateBy(getUsername());
        return toAjax(financeAaParaService.updateFinanceAaPara(financeAaPara));
    }

    /**
     * 删除会计系统参数维护
     */
    @RequiresPermissions("aa:para:remove")
    @Log(title = "会计系统参数维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaParaService.deleteFinanceAaParaByIds(ids));
    }
}
