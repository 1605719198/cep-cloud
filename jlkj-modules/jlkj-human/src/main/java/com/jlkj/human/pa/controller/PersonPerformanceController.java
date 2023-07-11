package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.pa.domain.PersonPerformance;
import com.jlkj.human.pa.domain.PersonPerformanceDetail;
import com.jlkj.human.pa.domain.PersonPerformanceFix;
import com.jlkj.human.pa.dto.PersonPerformanceDTO;
import com.jlkj.human.pa.service.IPersonPerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * 查询固定项目启动档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @RequiresPermissions("human:fixedProjectEvaluation:list")
    @Log(title = "查询固定项目启动档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询固定项目启动档列表")
    @GetMapping("/listPersonPerformanceFix")
    public Object listPersonPerformanceFix(PersonPerformanceDTO personPerformanceDTO) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(iPersonPerformanceService.listPersonPerformanceFix(personPerformanceDTO)));
    }

    /**
     * 查询考评清单列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @RequiresPermissions("human:evaluationListQuery:list")
    @Log(title = "查询考评清单列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询考评清单列表")
    @GetMapping("/evaluationList")
    public Object listEvaluation(PersonPerformanceDTO personPerformanceDTO) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(iPersonPerformanceService.listEvaluation(personPerformanceDTO)));
    }

    /**
     * 导出考评清单列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @RequiresPermissions("human:evaluationListQuery:export")
    @Log(title = "导出考评清单列表",businessType = BusinessType.EXPORT)
    @Operation(summary = "导出考评清单列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonPerformanceDTO personPerformanceDTO)
    {
        List<PersonPerformance> list = iPersonPerformanceService.listEvaluation(personPerformanceDTO);
        ExcelUtil<PersonPerformance> util = new ExcelUtil<PersonPerformance>(PersonPerformance.class);
        util.exportExcel(response, list, "考评清单数据");
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
     * 添加人员绩效明细档自评和实际完成情况
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 添加数据
     * @return 添加结果
     */
    @RequiresPermissions("human:personPerformanceEvaluation:edit")
    @Log(title = "添加人员绩效明细档自评和实际完成情况", businessType = BusinessType.UPDATE)
    @Operation(summary = "添加人员绩效明细档自评和实际完成情况")
    @PostMapping("/updateSelfApprDetail")
    public Object updateSelfApprDetail(@RequestBody PersonPerformanceDetail personPerformanceDetail) {
        return iPersonPerformanceService.updateSelfApprDetail(personPerformanceDetail);
    }

    /**
     * 添加人员绩效主档自评总结
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 添加数据
     * @return 添加结果
     */
    @RequiresPermissions("human:personPerformanceEvaluation:edit")
    @Log(title = "添加人员绩效主档自评总结", businessType = BusinessType.UPDATE)
    @Operation(summary = "添加人员绩效主档自评总结")
    @PostMapping("/updateSelfAppr")
    public Object updateSelfAppr(@RequestBody PersonPerformance personPerformance) {
        return iPersonPerformanceService.updateSelfAppr(personPerformance);
    }

    /**
     * 添加人员绩效主档主管考评和明细档主管考评分数及备注
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 添加数据
     * @return 添加结果
     */
    @RequiresPermissions("human:personPerformanceEvaluation:edit")
    @Log(title = "添加人员绩效主档主管考评和明细档主管考评分数及备注", businessType = BusinessType.UPDATE)
    @Operation(summary = "添加人员绩效主档主管考评和明细档主管考评分数及备注")
    @PostMapping("/updateAdmAppr")
    public Object updateAdmAppr(@RequestBody PersonPerformanceDTO personPerformanceDTO) {
        return iPersonPerformanceService.updateAdmAppr(personPerformanceDTO);
    }

    /**
     * 添加考评意见/复核意见、更新明细表主管考评及备注
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 添加数据
     * @return 添加结果
     */
    @RequiresPermissions("human:fixedProjectEvaluation:edit")
    @Log(title = "添加考评意见/复核意见、更新明细表主管考评及备注", businessType = BusinessType.UPDATE)
    @Operation(summary = "添加考评意见/复核意见、更新明细表主管考评及备注")
    @PostMapping("/updateAdmEvaluation")
    public Object updateAdmEvaluation(@RequestBody PersonPerformanceDTO personPerformanceDTO) {
        return iPersonPerformanceService.updateAdmEvaluation(personPerformanceDTO);
    }

    /**
     * 删除人员绩效主档同时一起删除明细档、进度表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 删除数据
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

    /**
     * 呈送固定项目考评
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @RequiresPermissions("human:fixedProjectEvaluation:send")
    @Operation(summary = "呈送固定项目考评")
    @PostMapping("/sendPersonPerformanceFix")
    @Log(title = "呈送计划", businessType = BusinessType.OTHER)
    public Object sendPersonPerformanceFix(@RequestBody PersonPerformanceDTO personPerformanceDTO) {
        return iPersonPerformanceService.sendPersonPerformanceFix(personPerformanceDTO);
    }

    /**
     * 呈送按钮
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @RequiresPermissions("human:personPerformanceEvaluation:send")
    @Operation(summary = "呈送按钮")
    @PostMapping("/sendSelfAppr")
    @Log(title = "呈送按钮", businessType = BusinessType.OTHER)
    public Object sendSelfAppr(@RequestBody PersonPerformanceDTO personPerformanceDTO) {
        return iPersonPerformanceService.sendSelfAppr(personPerformanceDTO);
    }

    /**
     * 最终主管确认
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformance 表格数据
     * @return 确认结果
     */
    @RequiresPermissions("human:personPerformanceEvaluation:confirm")
    @Operation(summary = "最终主管确认")
    @PostMapping("/confirmPersonalPerformance")
    @Log(title = "最终主管确认", businessType = BusinessType.OTHER)
    public Object confirmPersonalPerformance(@RequestBody PersonPerformance personPerformance) {
        return iPersonPerformanceService.confirmPersonalPerformance(personPerformance);
    }

    /**
     * 固定项目考评最终主管确认
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformance 表格数据
     * @return 确认结果
     */
    @RequiresPermissions("human:fixedProjectEvaluation:confirm")
    @Operation(summary = "固定项目考评最终主管确认")
    @PostMapping("/confirmPersonalPerformanceFix")
    @Log(title = "固定项目考评最终主管确认", businessType = BusinessType.OTHER)
    public Object confirmPersonalPerformanceFix(@RequestBody PersonPerformance personPerformance) {
        return iPersonPerformanceService.confirmPersonalPerformanceFix(personPerformance);
    }

    /**
     * 启动固定项目考评
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceFix 表格数据
     * @return 确认结果
     */
    @RequiresPermissions("human:fixedProjectEvaluation:start")
    @Operation(summary = "启动固定项目考评")
    @PostMapping("/startPersonPerformanceFix")
    @Log(title = "启动固定项目考评", businessType = BusinessType.OTHER)
    public Object startPersonPerformanceFix(@RequestBody PersonPerformanceFix personPerformanceFix) {
        return iPersonPerformanceService.startPersonPerformanceFix(personPerformanceFix);
    }
}
