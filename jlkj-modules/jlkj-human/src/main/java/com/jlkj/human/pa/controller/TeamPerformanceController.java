package com.jlkj.human.pa.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.pa.domain.TeamPerformance;
import com.jlkj.human.pa.dto.TeamPerformanceDTO;
import com.jlkj.human.pa.service.ITeamPerformanceDetailService;
import com.jlkj.human.pa.service.ITeamPerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuangBing
 * @Description
 * @create 2023-06-27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/teamPerformance")
public class TeamPerformanceController extends BaseController {

    private final ITeamPerformanceService iTeamPerformanceService;
    private final ITeamPerformanceDetailService iTeamPerformanceDetailService;

    /**
     * 查询团队绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @RequiresPermissions("human:teamPerformance:list")
    @Log(title = "查询团队绩效主档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询团队绩效主档列表")
    @GetMapping("/list")
    public Object listTeamPerformance(TeamPerformanceDTO teamPerformanceDTO) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(iTeamPerformanceService.listTeamPerformance(teamPerformanceDTO)));
    }

    /**
     * 查询团队绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 查询参数集
     * @return list 分页数据
     */
    @Log(title = "查询团队绩效明细档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询团队绩效明细档列表")
    @GetMapping("/listTeamPerformanceDetail")
    public Object listTeamPerformanceDetail(TeamPerformance teamPerformance) {
        startPage();
        return AjaxResult.success("查询成功", getDataTable(iTeamPerformanceService.listTeamPerformanceDetail(teamPerformance)));
    }

    /**
     * 新增团队绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 新增数据
     * @return 新增结果
     */
    @RequiresPermissions("human:teamPerformance:add")
    @Log(title = "新增团队绩效主档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增团队绩效主档列表")
    @PostMapping
    public Object addTeamPerformance(@RequestBody TeamPerformance teamPerformance) {
        return iTeamPerformanceService.addTeamPerformance(teamPerformance);
    }


    /**
     * 修改团队绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformanceDTO 修改数据
     * @return 修改结果
     */
    @RequiresPermissions("human:teamPerformance:edit")
    @Log(title = "修改团队绩效明细档列表", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改团队绩效明细档列表")
    @PutMapping
    public Object updateTeamPerformanceDetail(@RequestBody TeamPerformanceDTO teamPerformanceDTO) {
        return iTeamPerformanceService.updateTeamPerformanceDetail(teamPerformanceDTO);
    }

    /**
     * 删除团队绩效主档同时一起删除明细档
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 删除数据
     * @return 删除结果
     */
    @RequiresPermissions("human:teamPerformance:remove")
    @Operation(summary = "删除人员绩效主档同时一起删除明细档、进度表")
    @PostMapping("/delTeamPerformance")
    @Log(title = "删除单位KPI指标主档同时一起删除明细档", businessType = BusinessType.DELETE)
    public Object delTeamPerformanceAndDetail(@RequestBody TeamPerformance teamPerformance) {
        return iTeamPerformanceService.delTeamPerformanceAndDetail(teamPerformance);
    }

    /**
     * 状态确认
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 确认数据
     * @return 删除结果
     */
    @RequiresPermissions("human:teamPerformance:confirm")
    @Operation(summary = "状态确认")
    @PostMapping("/updateTeamPerformanceMeritStatus")
    @Log(title = "状态确认", businessType = BusinessType.UPDATE)
    public Object updateTeamPerformanceMeritStatus(@RequestBody TeamPerformance teamPerformance) {
        return iTeamPerformanceService.updateTeamPerformanceMeritStatus(teamPerformance);
    }
}
