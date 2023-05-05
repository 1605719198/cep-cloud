package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
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
import com.jlkj.finance.aa.domain.FinanceAaCashflowCodeCompid;
import com.jlkj.finance.aa.service.IFinanceAaCashflowCodeCompidService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 现金流量代码公司级Controller
 *
 * @author 265799
 * @date 2023-05-04
 */
@RestController
@RequestMapping("/codecompid")
public class FinanceAaCashflowCodeCompidController extends BaseController
{
    @Autowired
    private IFinanceAaCashflowCodeCompidService financeAaCashflowCodeCompidService;

    /**
     * 查询现金流量代码公司级列表
     */
    @RequiresPermissions("aa:codecompid:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid)
    {
        startPage();
        List<FinanceAaCashflowCodeCompid> list = financeAaCashflowCodeCompidService.selectFinanceAaCashflowCodeCompidList(financeAaCashflowCodeCompid);
        return getDataTable(list);
    }

    /**
     * 导出现金流量代码公司级列表
     */
    @RequiresPermissions("aa:codecompid:export")
    @Log(title = "现金流量代码公司级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid)
    {
        List<FinanceAaCashflowCodeCompid> list = financeAaCashflowCodeCompidService.selectFinanceAaCashflowCodeCompidList(financeAaCashflowCodeCompid);
        ExcelUtil<FinanceAaCashflowCodeCompid> util = new ExcelUtil<FinanceAaCashflowCodeCompid>(FinanceAaCashflowCodeCompid.class);
        util.exportExcel(response, list, "现金流量代码公司级数据");
    }

    /**
     * 获取现金流量代码公司级详细信息
     */
    @RequiresPermissions("aa:codecompid:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCashflowCodeCompidService.selectFinanceAaCashflowCodeCompidById(id));
    }

    /**
     * 新增现金流量代码公司级
     */
    @RequiresPermissions("aa:codecompid:add")
    @Log(title = "现金流量代码公司级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCashflowCodeDTO financeAaCashflowCodeDTO)
    {
        List<FinanceAaCashflowCodeDTO> projectIds = financeAaCashflowCodeDTO.getCodeList();
        return toAjax(financeAaCashflowCodeCompidService.insertFinanceAaCashflowCodeCompid(projectIds));
    }

    /**
     * 修改现金流量代码公司级
     */
    @RequiresPermissions("aa:codecompid:edit")
    @Log(title = "现金流量代码公司级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCashflowCodeCompid financeAaCashflowCodeCompid)
    {
        return toAjax(financeAaCashflowCodeCompidService.updateFinanceAaCashflowCodeCompid(financeAaCashflowCodeCompid));
    }

    /**
     * 删除现金流量代码公司级
     */
    @RequiresPermissions("aa:codecompid:remove")
    @Log(title = "现金流量代码公司级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCashflowCodeCompidService.deleteFinanceAaCashflowCodeCompidByIds(ids));
    }
}
