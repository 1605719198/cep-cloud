package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.ServletUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.service.TokenService;
import com.jlkj.human.ex.domain.ExamType;
import com.jlkj.human.ex.service.IExamTypeService;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试分类Controller
 * 
 * @author 111191
 * @date 2023-07-11
 */
@RestController
@RequestMapping("/questions/examtype")
public class ExamTypeController extends BaseController
{
    @Autowired
    private IExamTypeService examTypeService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询考试分类列表
     */
    @RequiresPermissions("questions:examtype:list")
    @GetMapping("/list")
    public AjaxResult list(ExamType examType)
    {
        List<ExamType> list = examTypeService.selectExamTypeList(examType);
        return AjaxResult.success(list);
    }

    /**
     * 导出考试分类列表
     */
    @RequiresPermissions("questions:examtype:export")
    @Log(title = "考试分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamType examType)
    {
        List<ExamType> list = examTypeService.selectExamTypeList(examType);
        ExcelUtil<ExamType> util = new ExcelUtil<ExamType>(ExamType.class);
        util.exportExcel(response,list, "考试分类数据");
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/typetreeselect")
    public AjaxResult treeselect(ExamType examType)
    {
    	List<ExamType> list = examTypeService.selectExamTypeList(examType);    	
        return AjaxResult.success(examTypeService.buildTypeTreeSelect(list));
    }
    /**
     * 获取考试分类详细信息
     */
    @RequiresPermissions("questions:examtype:query")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId)
    {
        return AjaxResult.success(examTypeService.selectExamTypeById(typeId));
    }

    /**
     * 新增考试分类
     */
    @RequiresPermissions("questions:examtype:add")
    @Log(title = "考试分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamType examType)
    {
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	examType.setCreateBy(loginUser.getUserName());
        return toAjax(examTypeService.insertExamType(examType));
    }

    /**
     * 修改考试分类
     */
    @RequiresPermissions("questions:examtype:edit")
    @Log(title = "考试分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamType examType)
    {
        return toAjax(examTypeService.updateExamType(examType));
    }

    /**
     * 删除考试分类
     */
    @RequiresPermissions("questions:examtype:remove")
    @Log(title = "考试分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds)
    {
        return toAjax(examTypeService.deleteExamTypeByIds(typeIds));
    }
}
