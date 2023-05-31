package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.SalaryProjectBasis;
import com.jlkj.human.hs.service.ISalaryProjectBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 集团级薪资项目输入维护Controller
 *
 * @author jiangbingchen
 * @date 2023-05-08
 */
@RestController
@RequestMapping("/salaryProjectBasis")
public class SalaryProjectBasisController extends BaseController {
    @Autowired
    private ISalaryProjectBasisService salaryProjectBasisService;

    /**
     * 查询集团级薪资项目输入维护列表
     */
    @RequiresPermissions("human:salaryProjectBasis:list")
    @GetMapping("/list")
    public Object list(SalaryProjectBasis salaryProjectBasis) {
        try {
            startPage();
            List<SalaryProjectBasis> list = salaryProjectBasisService.lambdaQuery()
                    .eq(SalaryProjectBasis::getParentid, salaryProjectBasis.getId()).list();
            if (list.isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", getDataTable(list));
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 导出集团级薪资项目输入维护列表
     */
    @RequiresPermissions("human:salaryProjectBasis:export")
    @Log(title = "集团级薪资项目输入维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalaryProjectBasis salaryProjectBasis) {
        List<SalaryProjectBasis> list = salaryProjectBasisService.selectSalaryProjectBasisList(salaryProjectBasis);
        ExcelUtil<SalaryProjectBasis> util = new ExcelUtil<SalaryProjectBasis>(SalaryProjectBasis.class);
        util.exportExcel(response, list, "集团级薪资项目输入维护数据");
    }

    /**
     * 获取集团级薪资项目输入维护详细信息
     */
    @RequiresPermissions("human:salaryProjectBasis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(salaryProjectBasisService.selectSalaryProjectBasisById(id));
    }

    /**
     * 新增集团级薪资项目输入维护
     */
    @RequiresPermissions("human:salaryProjectBasis:add")
    @Log(title = "集团级薪资项目输入维护", businessType = BusinessType.INSERT)
    @PostMapping
    public Object add(@RequestBody List<SalaryProjectBasis> salaryProjectBasisList) {
        return salaryProjectBasisService.insertSalaryProjectBasis(salaryProjectBasisList);
    }

    /**
     * 修改集团级薪资项目输入维护
     */
    @RequiresPermissions("human:salaryProjectBasis:edit")
    @Log(title = "集团级薪资项目输入维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalaryProjectBasis salaryProjectBasis) {
        return toAjax(salaryProjectBasisService.updateSalaryProjectBasis(salaryProjectBasis));
    }

    /**
     * 删除集团级薪资项目输入维护
     */
    @RequiresPermissions("human:salaryProjectBasis:remove")
    @Log(title = "集团级薪资项目输入维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)throws Exception
    {
        return toAjax(salaryProjectBasisService.deleteSalaryProjectBasisById(id));
    }

    /**
     * 获取集团级薪资项目下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SalaryProjectBasis salaryProjectBasis) {
        List<SalaryProjectBasis> salaryProjectBasiss = salaryProjectBasisService.selectSalaryProjectBasisList(salaryProjectBasis);
        return AjaxResult.success(salaryProjectBasisService.buildSalaryProjectTreeSelect(salaryProjectBasiss));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("human:salaryProjectBasis:edit")
    @Log(title = "启用禁用", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SalaryProjectBasis salaryProjectBasis)
    {
        return toAjax(salaryProjectBasisService.updateSalaryProjectBasis(salaryProjectBasis));
    }

}
