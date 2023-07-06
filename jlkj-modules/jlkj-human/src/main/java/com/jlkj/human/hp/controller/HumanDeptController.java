package com.jlkj.human.hp.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hp.domain.HumanDept;
import com.jlkj.human.hp.domain.HumanDeptVersion;
import com.jlkj.human.hp.dto.CopySysDeptDTO;
import com.jlkj.human.hp.dto.DeptUnionPostDTO;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import com.jlkj.human.hp.service.IHumanDeptService;
import com.jlkj.human.hp.service.IHumanDeptVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 部门资料维护Controller
 *
 * @author 266861
 * @date 2023-03-08
 */
@RestController
@RequestMapping("/deptMaintenance")
public class HumanDeptController extends BaseController
{
    @Autowired
    private IHumanDeptService sysDeptService;

    @Autowired
    private IHumanDeptVersionService sysDeptVersionService;

    /**
    * @Description 查询部门资料维护列表
    * @param humanDept 部门查询参数
    * @return 部门数据列表
    * @author 266861
    * @date 2023/6/21 14:59
    **/
    @GetMapping("/list")
    public TableDataInfo list(HumanDept humanDept)
    {
        startPage();
        List<HumanDept> list = sysDeptService.selectSysDeptList(humanDept);
        return getDataTable(list);
    }

    /**
    * @Description 查询部门资料变更版本列表
    * @param humanDeptVersion 部门历史版本查询参数
    * @return 部门历史版本数据
    * @author 266861
    * @date 2023/6/21 14:59
    **/
    @RequiresPermissions("human:deptMaintenance:query")
    @GetMapping("/deptVersionlist")
    public TableDataInfo list(HumanDeptVersion humanDeptVersion)
    {
        startPage();
        List<HumanDeptVersion> list = sysDeptVersionService.selectSysDeptVersionList(humanDeptVersion);
        return getDataTable(list);
    }



    /**
    * @Description 获取部门资料维护详细信息
    * @param deptId 部门id
    * @return 该部门详细信息
    * @author 266861
    * @date 2023/6/21 14:59
    **/
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable("deptId") Long deptId)
    {
        return success(sysDeptService.selectSysDeptByDeptId(deptId));
    }

    /**
     * @Description 获取部门历史版本资料信息
     * @param id 历史版本资料id
     * @return 该id历史版本资料信息
     * @author 266861
     * @date 2023/6/21 14:59
     **/
    @GetMapping(value = "/getVersion/{id}")
    public AjaxResult getVersion(@PathVariable("id") Long id)
    {
        return success(sysDeptVersionService.selectSysDeptVersionById(id));
    }

    /**
    * @Description 新增部门资料维护
    * @param humanDept 部门数据
    * @return 新增结果
    * @author 266861
    * @date 2023/6/21 15:01
    **/
    @RequiresPermissions("human:deptMaintenance:add")
    @Log(title = "部门资料维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HumanDept humanDept)throws Exception
    {
        return toAjax(sysDeptService.insertSysDept(humanDept));
    }


    /**
    * @Description 复制组织机构数据
    * @param copySysDeptDTO 组织机构复制参数
    * @return 复制结果
    * @author 266861
    * @date 2023/6/21 15:01
    **/
    @RequiresPermissions("human:deptMaintenance:copy")
    @Log(title = "组织机构复制", businessType = BusinessType.INSERT)
    @PostMapping("/copySysDept")
    public AjaxResult copy(@RequestBody CopySysDeptDTO copySysDeptDTO) throws Exception
    {
        return toAjax(sysDeptService.copySysDept(copySysDeptDTO));
    }


    /**
    * @Description 修改部门资料维护
    * @param humanDept 所修改部门资料
    * @return 修改结果
    * @author 266861
    * @date 2023/6/21 15:02
    **/
    @RequiresPermissions("human:deptMaintenance:edit")
    @Log(title = "部门资料维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HumanDept humanDept) throws Exception
    {
        return toAjax(sysDeptService.updateSysDept(humanDept));
    }

    /**
    * @Description 删除部门资料维护
    * @param deptIds 删除部门id数组
    * @return 删除结果
    * @author 266861
    * @date 2023/6/21 15:02
    **/
    @RequiresPermissions("human:deptMaintenance:remove")
    @Log(title = "部门资料维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptIds}")
    public AjaxResult remove(@PathVariable Long[] deptIds) throws Exception
    {
        return toAjax(sysDeptService.deleteSysDeptByDeptIds(deptIds));
    }

    /**
    * @Description 获取部门下拉树
    * @param dept 下拉树查询条件
    * @return 下拉树
    * @author 266861
    * @date 2023/6/21 15:02
    **/
    @GetMapping("/treeselect")
    public AjaxResult treeselect(HumanDept dept)
    {
        List<HumanDept> depts = sysDeptService.selectSysDeptList(dept);
        return AjaxResult.success(sysDeptService.buildDeptTreeSelect(depts));
    }


    /**
    * @Description 获取部门岗位下拉树列表
    * @param deptpost 部门岗位查询参数
    * @return 部门岗位下拉树
    * @author 266861
    * @date 2023/6/21 15:03
    **/
    @RequiresPermissions("human:deptPost:list")
    @GetMapping("/deptPostTree")
    public AjaxResult deptPostTree(DeptUnionPostDTO deptpost)
    {
        List<DeptUnionPostDTO> deptPostList = sysDeptService.selectDeptPostList(deptpost);
        return AjaxResult.success(sysDeptService.buildDeptPostTree(deptPostList));
    }


    /**
    * @Description 获取公司资料列表（下拉选单用）
    * @return 集团下公司数据
    * @author 266861
    * @date 2023/6/21 14:57
    **/
    @GetMapping("/selectCompany")
    public AjaxResult selectCompany()
    {
        List<HumanDept> companyList = sysDeptService.selectCompanyList();
        return AjaxResult.success(companyList);
    }

    /**
    * @Description 通过员工工号查询一级机构
    * @param empId 员工工号
    * @return 一级机构数据
    * @author 266861
    * @date 2023/6/21 14:57
    **/
    @GetMapping("/queryFirstDeptByPerson/{empId}")
    public AjaxResult queryFirstDeptByPerson(@PathVariable("empId") String empId)
    {
        FirstDeptDTO firstDept = sysDeptService.getFirstDeptByPerson(empId);
        return AjaxResult.success(firstDept);
    }



    /**
    * @Description 通过部门ID查询一级机构
    * @param deptId 部门ID
    * @return 一级机构数据
    * @author 266861
    * @date 2023/6/21 14:56
    **/
    @GetMapping("/queryFirstDeptByDept/{deptId}")
    public AjaxResult queryFirstDeptByDept(@PathVariable("deptId") String deptId)
    {
        FirstDeptDTO firstDept = sysDeptService.getFirstDeptByDept(deptId);
        return AjaxResult.success(firstDept);
    }


    /**
    * @Description 导入部门数据
    * @param file 模板文件
    * @param updateSupport 是否更新
    * @return 导入结果
    * @author 266861
    * @date 2023/6/21 14:54
    **/
    @Log(title = "部门资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:deptMaintenance:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<HumanDept> util = new ExcelUtil<HumanDept>(HumanDept.class);
        List<HumanDept> humanDeptList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = sysDeptService.importUser(humanDeptList, updateSupport, operName);
        return success(message);
    }


    /**
    * @Description 获得部门资料导入模板
    * @param response 导入参数
     *@throws IOException 导入异常处理
    * @author 266861
    * @date 2023/6/21 14:52
    **/
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<HumanDept> util = new ExcelUtil<HumanDept>(HumanDept.class);
        util.importTemplateExcel(response, "部门资料数据");
    }

    /**
    * @Description 查询部门名称（id/编码 转名称）远程调用暴露接口
    * @param compId 公司别
    * @return 某公司部门id编码名称数据
    * @author 266861
    * @date 2023/6/21 14:51
    **/
    @GetMapping("/selectDeptName/{compId}")
    public List<Map<String,Object>> selectDeptName(@PathVariable("compId") String compId)
    {
        return sysDeptService.selectDeptName(compId);
    }
}
