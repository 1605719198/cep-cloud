package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.SalaryEmployedMonth;
import com.jlkj.human.hs.service.ISalaryEmployedMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 员工受雇月维护Controller
 *
 * @author 267383
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/salaryEmployedMonth")
public class SalaryEmployedMonthController extends BaseController
{
    @Autowired
    private ISalaryEmployedMonthService salaryEmployedMonthService;

    /**
     * 查询员工受雇月维护列表
     */
    @RequiresPermissions("human:salaryEmployedMonth:list")
    @GetMapping("/list")
    public TableDataInfo list(SalaryEmployedMonth salaryEmployedMonth)
    {
        startPage();
        List<SalaryEmployedMonth> list = salaryEmployedMonthService.selectSalaryEmployedMonthList(salaryEmployedMonth);
        return getDataTable(list);
    }

    /**
     * 导出员工受雇月维护列表
     */
    @RequiresPermissions("human:salaryEmployedMonth:export")
    @Log(title = "员工受雇月维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalaryEmployedMonth salaryEmployedMonth)
    {
        List<SalaryEmployedMonth> list = salaryEmployedMonthService.selectSalaryEmployedMonthList(salaryEmployedMonth);
        ExcelUtil<SalaryEmployedMonth> util = new ExcelUtil<SalaryEmployedMonth>(SalaryEmployedMonth.class);
        util.exportExcel(response, list, "员工受雇月维护数据");
    }

    /**
     * 获取员工受雇月维护详细信息
     */
    @RequiresPermissions("human:salaryEmployedMonth:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(salaryEmployedMonthService.selectSalaryEmployedMonthById(id));
    }

    /**
     * 保存员工受雇月维护
     */
    @RequiresPermissions("human:salaryEmployedMonth:add")
    @Log(title = "员工受雇月维护保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public int save(@RequestBody List<SalaryEmployedMonth> salaryEmployedMonthList)
    {
        return salaryEmployedMonthService.insertSalaryEmployedMonth(salaryEmployedMonthList);
    }

    /**
     * 修改员工受雇月维护
     */
    @RequiresPermissions("human:salaryEmployedMonth:edit")
    @Log(title = "员工受雇月维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalaryEmployedMonth salaryEmployedMonth)
    {
        return toAjax(salaryEmployedMonthService.updateSalaryEmployedMonth(salaryEmployedMonth));
    }

    /**
     * 删除员工受雇月维护
     */
    @RequiresPermissions("human:salaryEmployedMonth:remove")
    @Log(title = "员工受雇月维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(salaryEmployedMonthService.deleteSalaryEmployedMonthByIds(ids));
    }

    /**
     * 导入员工受雇月维护数据
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "员工受雇月维护资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:salaryEmployedMonth:export")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SalaryEmployedMonth> util = new ExcelUtil<SalaryEmployedMonth>(SalaryEmployedMonth.class);
        List<SalaryEmployedMonth> salaryEmployedMonthList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = salaryEmployedMonthService.importUser(salaryEmployedMonthList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入员工受雇月维护数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SalaryEmployedMonth> util = new ExcelUtil<SalaryEmployedMonth>(SalaryEmployedMonth.class);
        util.importTemplateExcel(response, "员工受雇月维护数据");
    }
}
