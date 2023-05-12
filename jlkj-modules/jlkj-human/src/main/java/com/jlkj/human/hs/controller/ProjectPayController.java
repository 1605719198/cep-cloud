package com.jlkj.human.hs.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
import com.jlkj.human.hs.domain.ProjectPay;
import com.jlkj.human.hs.service.IProjectPayService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

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
     * 获取薪酬项目详细信息
     */
    @RequiresPermissions("human:projectPay:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectPayService.selectProjectPayById(id));
    }

    /**
     * 新增薪酬项目
     */
    @RequiresPermissions("human:projectPay:add")
    @Log(title = "薪酬项目新增", businessType = BusinessType.INSERT)
    @PostMapping
    public int add(@RequestBody List<ProjectPay> projectPayList)
    {
        return projectPayService.insertProjectPay(projectPayList);
    }


}
