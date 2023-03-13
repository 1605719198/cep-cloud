package com.jlkj.human.hp.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.human.hp.domain.SysDeptVersion;
import com.jlkj.human.hp.service.ISysDeptVersionService;
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
@RequestMapping("/deptVersion")
public class SysDeptVersionController extends BaseController
{
    @Autowired
    private ISysDeptVersionService sysDeptVersionService;

    /**
     * 查询部门资料变更版本列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysDeptVersion sysDeptVersion)
    {
        startPage();
        List<SysDeptVersion> list = sysDeptVersionService.selectSysDeptVersionList(sysDeptVersion);
        return getDataTable(list);
    }

    /**
     * 导出部门资料变更版本列表
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDeptVersion sysDeptVersion)
    {
        List<SysDeptVersion> list = sysDeptVersionService.selectSysDeptVersionList(sysDeptVersion);
        ExcelUtil<SysDeptVersion> util = new ExcelUtil<SysDeptVersion>(SysDeptVersion.class);
        util.exportExcel(response, list, "部门资料变更版本数据");
    }

    /**
     * 获取部门资料变更版本详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysDeptVersionService.selectSysDeptVersionById(id));
    }

    /**
     * 新增部门资料变更版本
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDeptVersion sysDeptVersion)
    {
        return toAjax(sysDeptVersionService.insertSysDeptVersion(sysDeptVersion));
    }

    /**
     * 修改部门资料变更版本
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDeptVersion sysDeptVersion)
    {
        return toAjax(sysDeptVersionService.updateSysDeptVersion(sysDeptVersion));
    }

    /**
     * 删除部门资料变更版本
     */
    @Log(title = "部门资料变更版本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysDeptVersionService.deleteSysDeptVersionByIds(ids));
    }
}
