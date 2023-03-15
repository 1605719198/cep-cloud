package com.jlkj.human.hp.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 部门资料维护Controller
 *
 * @author jlkj
 * @date 2023-03-08
 */
@RestController
@RequestMapping("/deptMaintenance")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询部门资料维护列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysDept sysDept)
    {
        startPage();
        List<SysDept> list = sysDeptService.selectSysDeptList(sysDept);
        return getDataTable(list);
    }

    /**
     * 导出部门资料维护列表
     */
    @Log(title = "部门资料维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDept sysDept)
    {
        List<SysDept> list = sysDeptService.selectSysDeptList(sysDept);
        ExcelUtil<SysDept> util = new ExcelUtil<SysDept>(SysDept.class);
        util.exportExcel(response, list, "部门资料维护数据");
    }

    /**
     * 获取部门资料维护详细信息
     */
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable("deptId") Long deptId)
    {
        return success(sysDeptService.selectSysDeptByDeptId(deptId));
    }

    /**
     * 新增部门资料维护
     */
    @Log(title = "部门资料维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDept sysDept)throws Exception
    {
        return toAjax(sysDeptService.insertSysDept(sysDept));
    }

    /**
     * 修改部门资料维护
     */
    @Log(title = "部门资料维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDept sysDept) throws Exception
    {
        return toAjax(sysDeptService.updateSysDept(sysDept));
    }

    /**
     * 删除部门资料维护
     */
    @Log(title = "部门资料维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptIds}")
    public AjaxResult remove(@PathVariable Long[] deptIds)
    {
        return toAjax(sysDeptService.deleteSysDeptByDeptIds(deptIds));
    }
    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysDept dept)
    {
        List<SysDept> depts = sysDeptService.selectSysDeptList(dept);
        return AjaxResult.success(sysDeptService.buildDeptTreeSelect(depts));
    }
}
