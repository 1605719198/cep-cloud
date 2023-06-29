package com.jlkj.human.pa.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.pa.domain.PersonPerformance;
import com.jlkj.human.pa.domain.PersonPerformanceDetail;
import com.jlkj.human.pa.dto.PersonPerformanceDTO;
import com.jlkj.human.pa.service.IPersonPerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuangBing
 * @Description
 * @create 2023-06-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/personPerformance")
public class PersonPerformanceController extends BaseController {

    private final IPersonPerformanceService iPersonPerformanceService;

    /**
     * 查询个人绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @RequiresPermissions("human:personPerformance:list")
    @Log(title = "查询个人绩效主档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询个人绩效主档列表")
    @GetMapping("/list")
    public Object listPersonPerformance(PersonPerformanceDTO personPerformanceDTO) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(iPersonPerformanceService.listPersonPerformance(personPerformanceDTO)));
    }

    /**
     * 查询个人绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 查询参数集
     * @return list 分页数据
     */
    @Log(title = "查询个人绩效明细档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询个人绩效明细档列表")
    @GetMapping("/listPersonPerformanceDetail")
    public Object listPersonPerformanceDetail(PersonPerformance personPerformance) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(iPersonPerformanceService.listPersonPerformanceDetail(personPerformance)));
    }

    /**
     * 查询个人绩效进度档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 查询参数集
     * @return list 分页数据
     */
    @Log(title = "查询个人绩效进度档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询个人绩效进度档列表")
    @GetMapping("/listPersonPerformanceSchedule")
    public Object listPersonPerformanceSchedule(PersonPerformance personPerformance) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(iPersonPerformanceService.listPersonPerformanceSchedule(personPerformance)));
    }

    /**
     * 新增人员绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 新增数据
     * @return 新增结果
     */
    @RequiresPermissions("human:personPerformance:add")
    @Log(title = "新增人员绩效主档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员绩效主档列表")
    @PostMapping
    public Object addPersonPerformance(@RequestBody PersonPerformance personPerformance) {
        return iPersonPerformanceService.addPersonPerformance(personPerformance);
    }

    /**
     * 新增人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 新增数据
     * @return 新增结果
     */
    @RequiresPermissions("human:personPerformance:addDetail")
    @Log(title = "新增人员绩效明细档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员绩效明细档列表")
    @PostMapping("/addDetail")
    public Object addPersonPerformanceDetail(@RequestBody PersonPerformanceDetail personPerformanceDetail) {
        return iPersonPerformanceService.addPersonPerformanceDetail(personPerformanceDetail);
    }

    /**
     * 新增人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 新增数据
     * @return 新增结果
     */
    @RequiresPermissions("human:personPerformance:addDetail")
    @Log(title = "新增人员绩效明细档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员绩效明细档列表")
    @PostMapping("/addKpiDetail")
    public Object addPersonPerformanceKpiDetail(@RequestBody PersonPerformanceDetail personPerformanceDetail) {
        return iPersonPerformanceService.addPersonPerformanceKpiDetail(personPerformanceDetail);
    }

    /**
     * 修改人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 修改数据
     * @return 修改结果
     */
    @RequiresPermissions("human:personPerformance:edit")
    @Log(title = "修改人员绩效明细档列表", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改人员绩效明细档列表")
    @PutMapping
    public Object updatePersonPerformanceDetail(@RequestBody PersonPerformanceDetail personPerformanceDetail) {
        return iPersonPerformanceService.updatePersonPerformanceDetail(personPerformanceDetail);
    }

    /**
     * 删除人员绩效主档同时一起删除明细档、进度表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 新增数据
     * @return 删除结果
     */
    @RequiresPermissions("human:personPerformance:remove")
    @Operation(summary = "删除人员绩效主档同时一起删除明细档、进度表")
    @PostMapping("/delPersonPerformance")
    @Log(title = "删除单位KPI指标主档同时一起删除明细档", businessType = BusinessType.DELETE)
    public Object delPersonPerformanceAndDetail(@RequestBody PersonPerformance personPerformance) {
        return iPersonPerformanceService.delPersonPerformanceAndDetail(personPerformance);
    }

    /**
     * 删除人员绩效明细档
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 删除数据
     * @return 删除结果
     */
    @RequiresPermissions("human:personPerformance:remove")
    @Operation(summary = "删除人员绩效明细档")
    @PostMapping("/delPersonPerformanceDetail")
    @Log(title = "删除人员绩效明细档", businessType = BusinessType.DELETE)
    public Object delPersonPerformanceDetail(@RequestBody PersonPerformanceDetail personPerformanceDetail) {
        return iPersonPerformanceService.delPersonPerformanceDetail(personPerformanceDetail);
    }

    /**
     * 呈送计划
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @RequiresPermissions("human:personPerformance:send")
    @Operation(summary = "呈送计划")
    @PostMapping("/sendPersonPerformance")
    @Log(title = "呈送计划", businessType = BusinessType.OTHER)
    public Object sendPersonPerformance(@RequestBody PersonPerformanceDTO personPerformanceDTO) {
        return iPersonPerformanceService.sendPersonPerformance(personPerformanceDTO);
    }
}
