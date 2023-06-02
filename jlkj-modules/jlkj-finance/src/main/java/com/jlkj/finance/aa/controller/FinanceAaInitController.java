package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaInit;
import com.jlkj.finance.aa.service.IFinanceAaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 初始化记录Controller
 *
 * @author 265799
 * @date 2023-05-26
 */
@RestController
@RequestMapping("/init")
public class FinanceAaInitController extends BaseController
{
    @Autowired
    private IFinanceAaInitService financeAaInitService;

    /**
     * 查询初始化记录列表
     */
    @RequiresPermissions("aa:init:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaInit financeAaInit)
    {
        startPage();
        List<FinanceAaInit> list = financeAaInitService.selectFinanceAaInitList(financeAaInit);
        return getDataTable(list);
    }

    /**
     * 导出初始化记录列表
     */
    @RequiresPermissions("aa:init:export")
    @Log(title = "初始化记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaInit financeAaInit)
    {
        List<FinanceAaInit> list = financeAaInitService.selectFinanceAaInitList(financeAaInit);
        ExcelUtil<FinanceAaInit> util = new ExcelUtil<FinanceAaInit>(FinanceAaInit.class);
        util.exportExcel(response, list, "初始化记录数据");
    }

    /**
     * 获取初始化记录详细信息
     */
    @RequiresPermissions("aa:init:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaInitService.selectFinanceAaInitById(id));
    }

    /**
     * 新增初始化记录
     */
    @RequiresPermissions("aa:init:add")
    @Log(title = "初始化记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaInit financeAaInit)
    {
        return toAjax(financeAaInitService.insertFinanceAaInit(financeAaInit));
    }

    /**
     * 修改初始化记录
     */
    @RequiresPermissions("aa:init:edit")
    @Log(title = "初始化记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaInit financeAaInit)
    {
        return toAjax(financeAaInitService.updateFinanceAaInit(financeAaInit));
    }

    /**
     * 删除初始化记录
     */
    @RequiresPermissions("aa:init:remove")
    @Log(title = "初始化记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaInitService.deleteFinanceAaInitByIds(ids));
    }
    /**
     * 导入初始化记录
     */
    @Log(title = "导入初始化记录", businessType = BusinessType.IMPORT)
    @RequiresPermissions("aa:voucher:import")
    @PostMapping("/importData/{acctPeriod}/{companyId}")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, @PathVariable String acctPeriod,@PathVariable String companyId) throws Exception
    {
        ExcelUtil<FinanceAaInit> util = new ExcelUtil<FinanceAaInit>(FinanceAaInit.class);
        List<FinanceAaInit> financeAaInits = util.importExcel(file.getInputStream());
        String operName = getUsername();
        List<FinanceAaInit> financeAaInitList = financeAaInitService.importFinanceAaInits(financeAaInits, updateSupport, operName, acctPeriod, companyId);
        return AjaxResult.success(financeAaInitList);
    }
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<FinanceAaInit> util = new ExcelUtil<FinanceAaInit>(FinanceAaInit.class);
        util.importTemplateExcel(response, "初始化记录");
    }
}
