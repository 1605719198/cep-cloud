package com.jlkj.human.hp.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.domain.SysDeptVersion;
import com.jlkj.human.hp.dto.CopySysDeptDTO;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import com.jlkj.human.hp.service.ISysDeptService;
import com.jlkj.human.hp.service.ISysDeptVersionService;
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

    @Autowired
    private ISysDeptVersionService sysDeptVersionService;

    /**
     * 查询部门资料维护列表
     */
    @RequiresPermissions("human:deptMaintenance:list")
    @GetMapping("/list")
    public TableDataInfo list(SysDept sysDept)
    {
        startPage();
        List<SysDept> list = sysDeptService.selectSysDeptList(sysDept);
        return getDataTable(list);
    }
    /**
     * 查询部门资料变更版本列表
     */
    @GetMapping("/deptVersionlist")
    public TableDataInfo list(SysDeptVersion sysDeptVersion)
    {
        startPage();
        List<SysDeptVersion> list = sysDeptVersionService.selectSysDeptVersionList(sysDeptVersion);
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
    @RequiresPermissions("human:deptMaintenance:add")
    @Log(title = "部门资料维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDept sysDept)throws Exception
    {
        return toAjax(sysDeptService.insertSysDept(sysDept));
    }

    /**
     * 复制组织机构数据
     */
    @RequiresPermissions("human:deptMaintenance:copy")
    @Log(title = "组织机构复制", businessType = BusinessType.INSERT)
    @PostMapping("/copySysDept")
    public AjaxResult copy(@RequestBody CopySysDeptDTO copySysDeptDTO) throws Exception
    {
        return toAjax(sysDeptService.copySysDept(copySysDeptDTO));
    }

    /**
     * 修改部门资料维护
     */
    @RequiresPermissions("human:deptMaintenance:edit")
    @Log(title = "部门资料维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDept sysDept) throws Exception
    {
        return toAjax(sysDeptService.updateSysDept(sysDept));
    }

    /**
     * 删除部门资料维护
     */
    @RequiresPermissions("human:deptMaintenance:remove")
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

    /**
     * 获取部门岗位下拉树列表
     */
    @GetMapping("/deptpostTree")
    public AjaxResult deptpostTree(DeptUnionPostDTO deptpost)
    {
        List<DeptUnionPostDTO> deptPostList = sysDeptService.selectDeptPostList(deptpost);
        return AjaxResult.success(sysDeptService.buildDeptPostTree(deptPostList));
    }

    /**
     * 获取公司资料列表
     */
    @GetMapping("/selectCompany")
    public AjaxResult selectCompany()
    {
        List<SysDept> companyList = sysDeptService.selectCompanyList();
        return AjaxResult.success(companyList);
    }
    /**
     * 通过员工工号查询一级机构
     */
    @GetMapping("/queryFirstDeptByPerson/{empId}")
    public AjaxResult queryFirstDeptByPerson(@PathVariable("empId") String empId)
    {
        FirstDeptDTO firstDept = sysDeptService.getFirstDeptByPerson(empId);
        return AjaxResult.success(firstDept);
    }

    /**
     * 通过部门ID查询一级机构
     */
    @GetMapping("/queryFirstDeptByDept/{deptId}")
    public AjaxResult queryFirstDeptByDept(@PathVariable("deptId") String deptId)
    {
        FirstDeptDTO firstDept = sysDeptService.getFirstDeptByDept(deptId);
        return AjaxResult.success(firstDept);
    }
    
}
