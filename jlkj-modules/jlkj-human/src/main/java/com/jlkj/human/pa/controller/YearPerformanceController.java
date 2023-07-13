package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.pa.domain.YearPerformance;
import com.jlkj.human.pa.dto.YearPerformanceDTO;
import com.jlkj.human.pa.service.IYearPerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 年度绩效主档Controller
 *
 * @author 267383
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/yearPerformance")
public class YearPerformanceController extends BaseController
{
    @Autowired
    private IYearPerformanceService yearPerformanceService;

    /**
     * 查询年度绩效主档列表
     */
    @RequiresPermissions("human:yearPerformance:list")
    @GetMapping("/list")
    public TableDataInfo list(YearPerformance yearPerformance)
    {
        startPage();
        List<YearPerformance> list = yearPerformanceService.selectYearPerformanceList(yearPerformance);
        return getDataTable(list);
    }

    /**
     * 查询年度绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param yearPerformance 查询参数集
     * @return list 分页数据
     */
    @RequiresPermissions("human:yearPerformance:list")
    @Log(title = "查询年度绩效主档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询年度绩效主档列表")
    @GetMapping("/listYearPerformance")
    public Object listYearPerformance(YearPerformance yearPerformance) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(yearPerformanceService.listYearPerformance(yearPerformance)));
    }

    /**
     * 导出年度绩效主档列表
     */
    @RequiresPermissions("human:yearPerformance:export")
    @Log(title = "年度绩效主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YearPerformance yearPerformance)
    {
        List<YearPerformance> list = yearPerformanceService.selectYearPerformanceList(yearPerformance);
        ExcelUtil<YearPerformance> util = new ExcelUtil<YearPerformance>(YearPerformance.class);
        util.exportExcel(response, list, "年度绩效主档数据");
    }

    /**
     * 获取年度绩效主档详细信息
     */
    @RequiresPermissions("human:yearPerformance:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(yearPerformanceService.selectYearPerformanceById(id));
    }

    /**
     * 新增年度绩效主档
     */
    @RequiresPermissions("human:yearPerformance:add")
    @Log(title = "年度绩效主档", businessType = BusinessType.INSERT)
    @PostMapping
    public Object add(@RequestBody YearPerformance yearPerformance)
    {
        return yearPerformanceService.insertYearPerformance(yearPerformance);
    }

    /**
     * 修改年度绩效主档
     */
    @RequiresPermissions("human:yearPerformance:edit")
    @Log(title = "年度绩效主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YearPerformance yearPerformance)
    {
        return toAjax(yearPerformanceService.updateYearPerformance(yearPerformance));
    }

    /**
     * 删除年度绩效主档
     */
    @RequiresPermissions("human:yearPerformance:remove")
    @Log(title = "年度绩效主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(yearPerformanceService.deleteYearPerformanceByIds(ids));
    }

    /**
     * 呈送计划
     * @author HuangBing
     * @date 2023-6-20
     * @param yearPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @RequiresPermissions("human:yearPerformance:send")
    @Operation(summary = "呈送计划")
    @PostMapping("/sendYearPerformance")
    @Log(title = "呈送计划", businessType = BusinessType.OTHER)
    public Object sendYearPerformance(@RequestBody YearPerformanceDTO yearPerformanceDTO) {
        return yearPerformanceService.sendYearPerformance(yearPerformanceDTO);
    }

    /**
     * 最终复核
     * @author HuangBing
     * @date 2023-6-20
     * @param yearPerformanceDTO 表格数据
     * @return 最终复核结果
     */
    @RequiresPermissions("human:yearPerformance:check")
    @Operation(summary = "最终复核")
    @PostMapping("/finalCheckYearPerformance")
    @Log(title = "最终复核", businessType = BusinessType.OTHER)
    public Object finalCheckYearPerformance(@RequestBody YearPerformanceDTO yearPerformanceDTO) {
        return yearPerformanceService.finalCheckYearPerformance(yearPerformanceDTO);
    }

    /**
     * 年度绩效管理导入
     * @author HuangBing
     * @date 2023-6-20
     * @param file 表格数据
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 年度绩效管理导入结果
     */
    @RequiresPermissions("human:yearPerformance:import")
    @Operation(summary = "年度绩效管理导入")
    @PostMapping("/importData")
    @Log(title = "年度绩效管理导入", businessType = BusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<YearPerformance> util = new ExcelUtil<YearPerformance>(YearPerformance.class);
        List<YearPerformance> yearPerformanceList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = yearPerformanceService.importYearPerformance(yearPerformanceList, updateSupport, operName);
        return success(message);
    }

    /**
     * 年度绩效管理下载模板
     * @author HuangBing
     * @date 2023-6-20
     * @param response 返回数据
     */
    @Operation(summary = "年度绩效管理下载模板")
    @PostMapping("/importTemplate")
    @Log(title = "年度绩效管理下载模板", businessType = BusinessType.OTHER)
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<YearPerformance> util = new ExcelUtil<YearPerformance>(YearPerformance.class);
        util.importTemplateExcel(response, "年度绩效数据");
    }
}
