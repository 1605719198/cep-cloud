package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.SalaryBasis;
import com.jlkj.human.hs.service.ISalaryBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪资资料Controller
 *
 * @author 266861
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/salaryBasis")
public class SalaryBasisController extends BaseController
{
    @Autowired
    private ISalaryBasisService salaryBasisService;

    /**
     * 查询薪资资料列表
     */
    @RequiresPermissions("human:salaryBasis:list")
    @GetMapping("/list")
    public TableDataInfo list(SalaryBasis salaryBasis)
    {
        startPage();
        List<SalaryBasis> list = salaryBasisService.selectSalaryBasisList(salaryBasis);
        return getDataTable(list);
    }
    /**
     * 查询薪资资料树结构
     */
    @RequiresPermissions("human:salaryBasis:lists")
    @GetMapping("/listTree")
    public List<SalaryBasis> listTree(SalaryBasis salaryBasis)
    {
        List<SalaryBasis> list = salaryBasisService.selectSalaryBasisList(salaryBasis);
        return list;
    }


    /**
     * 获取薪资资料详细信息
     */
    @RequiresPermissions("human:salaryBasis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(salaryBasisService.selectSalaryBasisById(id));
    }

    /**
     * 新增薪资资料
     */
    @RequiresPermissions("human:salaryBasis:add")
    @Log(title = "薪资资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalaryBasis salaryBasis)throws Exception
    {
        return toAjax(salaryBasisService.insertSalaryBasis(salaryBasis));
    }

    /**
     * 修改薪资资料
     */
    @RequiresPermissions("human:salaryBasis:edit")
    @Log(title = "薪资资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalaryBasis salaryBasis)throws Exception
    {
        return toAjax(salaryBasisService.updateSalaryBasis(salaryBasis));
    }

    /**
     * 删除薪资资料
     */
    @RequiresPermissions("human:salaryBasis:remove")
    @Log(title = "薪资资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)throws Exception
    {
        return toAjax(salaryBasisService.deleteSalaryBasisById(id));
    }
}
