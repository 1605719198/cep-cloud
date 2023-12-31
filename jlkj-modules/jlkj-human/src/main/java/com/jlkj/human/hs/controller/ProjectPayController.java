package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.ProjectPay;
import com.jlkj.human.hs.service.IProjectPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪酬项目Controller
 *
 * @author 266861
 * @date 2023-05-11
 */
@RestController
@RequestMapping("/projectPay")
public class ProjectPayController extends BaseController
{
    @Autowired
    private IProjectPayService projectPayService;

    /**
     * 查询薪酬项目列表
     */
    @RequiresPermissions("human:projectPay:list")
    @GetMapping("/list")
    public TableDataInfo list(ProjectPay projectPay)
    {
        startPage();
        List<ProjectPay> list = projectPayService.selectProjectPayList(projectPay);
        return getDataTable(list);
    }

    /**
     * 查询薪酬项目树结构
     */
    @RequiresPermissions("human:projectPay:list")
    @GetMapping("/listTree")
    public List<ProjectPay> listTree(ProjectPay projectPay)
    {
        List<ProjectPay> list = projectPayService.selectProjectPayList(projectPay);
        return list;
    }


    /**
     * 保存薪酬项目
     */
    @RequiresPermissions("human:projectPay:save")
    @Log(title = "薪酬项目保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult save(@RequestBody List<ProjectPay> projectPayList)
    {
        int result = projectPayService.insertProjectPay(projectPayList);
        if(result==0){
            return error("保存失败，已停用数据无法进行修改");
        }else{
            return success("保存成功");
        }
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("human:projectPay:save")
    @Log(title = "启用禁用", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ProjectPay projectPay)
    {
        return toAjax(projectPayService.updateProjectPay(projectPay));
    }


}
