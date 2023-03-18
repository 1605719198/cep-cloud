package com.jlkj.human.hp.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hp.domain.SysPostVersion;
import com.jlkj.human.hp.service.ISysPostVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 部门资料变更版本Controller
 *
 * @author 266861
 * @date 2023-03-09
 */
@RestController
@RequestMapping("/postVersion")
public class SysPostVersionController extends BaseController
{
    @Autowired
    private ISysPostVersionService SysPostVersionService;

    /**
     * 查询部门资料变更版本列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysPostVersion SysPostVersion)
    {
        startPage();
        List<SysPostVersion> list = SysPostVersionService.selectSysPostVersionList(SysPostVersion);
        return getDataTable(list);
    }

    /**
     * 导出部门资料变更版本列表
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPostVersion SysPostVersion)
    {
        List<SysPostVersion> list = SysPostVersionService.selectSysPostVersionList(SysPostVersion);
        ExcelUtil<SysPostVersion> util = new ExcelUtil<SysPostVersion>(SysPostVersion.class);
        util.exportExcel(response, list, "部门资料变更版本数据");
    }

    /**
     * 获取部门资料变更版本详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(SysPostVersionService.selectSysPostVersionById(id));
    }

    /**
     * 新增部门资料变更版本
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPostVersion SysPostVersion)
    {
        return toAjax(SysPostVersionService.insertSysPostVersion(SysPostVersion));
    }

    /**
     * 修改部门资料变更版本
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPostVersion SysPostVersion)
    {
        return toAjax(SysPostVersionService.updateSysPostVersion(SysPostVersion));
    }

    /**
     * 删除部门资料变更版本
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(SysPostVersionService.deleteSysPostVersionByIds(ids));
    }
}
