package com.jlkj.human.pa.controller;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.pa.domain.*;
import com.jlkj.human.pa.dto.PersonPerformanceDTO;
import com.jlkj.human.pa.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    private final IPersonPerformanceDetailService iPersonPerformanceDetailService;
    private final IPersonPerformanceScheduleService iPersonPerformanceScheduleService;
    private final IDeptTypeService iDeptTypeService;
    private final IDeptTypeDetailService iDeptTypeDetailService;

    /**
     * 查询个人绩效主档列表
     */
    @RequiresPermissions("human:personPerformance:list")
    @Log(title = "查询个人绩效主档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询个人绩效主档列表")
    @GetMapping("/list")
    public Object listPersonPerformance(PersonPerformanceDTO personPerformanceDTO) {
        startPage();
        List<PersonPerformance> list = iPersonPerformanceService.lambdaQuery()
                .eq(PersonPerformance::getCompId, personPerformanceDTO.getCompId())
                .eq(PersonPerformance::getMeritType, personPerformanceDTO.getMeritType())
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getEmpId()), PersonPerformance::getEmpId, personPerformanceDTO.getEmpId())
                .between(PersonPerformance::getMeritMonth, personPerformanceDTO.getStartTime(), personPerformanceDTO.getEndTime()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 查询个人绩效明细档列表
     */
    @Log(title = "查询个人绩效明细档列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询个人绩效明细档列表")
    @GetMapping("/listPersonPerformanceDetail")
    public Object listPersonPerformanceDetail(PersonPerformance personPerformance) {
        startPage();
        List<PersonPerformanceDetail> list = iPersonPerformanceDetailService.lambdaQuery()
                .eq(PersonPerformanceDetail::getPersonPerformanceId, personPerformance.getId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 新增人员绩效主档列表
     */
    @RequiresPermissions("human:personPerformance:add")
    @Log(title = "新增人员绩效主档列表", businessType = BusinessType.INSERT)
    @Operation(summary = "新增人员绩效主档列表")
    @PostMapping
    public Object addPersonPerformance(@RequestBody PersonPerformance personPerformance) {
        DeptType deptType = iDeptTypeService.lambdaQuery()
                .eq(DeptType::getCompId, personPerformance.getCompId())
                .eq(DeptType::getDeptType, personPerformance.getDeptType())
                .eq(DeptType::getEffcMonth, personPerformance.getMeritMonth()).one();
        personPerformance.setIsTeam(deptType.getIsTeam());
        personPerformance.setIsPoint(deptType.getIsPoint());
        personPerformance.setIsFix(deptType.getIsFix());
        personPerformance.setIsKpi(deptType.getIsKpi());
        personPerformance.setTeamRatio(deptType.getTeamRatio());
        personPerformance.setPointRatio(deptType.getPointRatio());
        personPerformance.setFixRatio(deptType.getFixRatio());
        personPerformance.setKpiRatio(deptType.getKpiRatio());
        personPerformance.setCreator(SecurityUtils.getNickName());
        personPerformance.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        iPersonPerformanceService.save(personPerformance);
        if (("0").equals(deptType.getIsFix())) {
            PersonPerformanceDetail personPerformanceDetail = new PersonPerformanceDetail();
            PersonPerformanceSchedule personPerformanceSchedule = new PersonPerformanceSchedule();
            String[] project = {"计划输入","计划审批","自评","主管考评","最终主管复核"};
            List<DeptTypeDetail> list = iDeptTypeDetailService.lambdaQuery()
                    .eq(DeptTypeDetail::getDeptTypeId, deptType.getId()).list();
            PersonPerformance performance = iPersonPerformanceService.lambdaQuery()
                    .eq(PersonPerformance::getCompId, personPerformance.getCompId())
                    .eq(PersonPerformance::getMeritMonth, personPerformance.getMeritMonth())
                    .eq(PersonPerformance::getEmpId, personPerformance.getEmpId())
                    .eq(PersonPerformance::getDeptType, personPerformance.getDeptType()).one();
            for (DeptTypeDetail item : list){
                personPerformanceDetail.setPersonPerformanceId(performance.getId());
                personPerformanceDetail.setItem(item.getItem());
                personPerformanceDetail.setItemGoal(item.getDataDept());
                personPerformanceDetail.setWeight(item.getWeight());
                personPerformanceDetail.setCompleteTime(DateUtils.getMaxMonthDate1(personPerformance.getMeritMonth()));
                personPerformanceDetail.setType("3");
                iPersonPerformanceDetailService.save(personPerformanceDetail);
            }
            for (int i = 0; i < project.length; i++) {
                personPerformanceSchedule.setPersonPerformanceId(performance.getId());
                personPerformanceSchedule.setCompId(performance.getCompId());
                personPerformanceSchedule.setProject(project[i]);
                personPerformanceSchedule.setNum(String.valueOf(i+1));
                personPerformanceSchedule.setApprStatus("0");
                iPersonPerformanceScheduleService.save(personPerformanceSchedule);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 删除人员绩效主档同时一起删除明细档、进度表
     */
    @RequiresPermissions("human:personPerformance:remove")
    @Operation(summary = "删除人员绩效主档同时一起删除明细档、进度表")
    @PostMapping("/delPersonPerformance")
    @Log(title = "删除单位KPI指标主档同时一起删除明细档", businessType = BusinessType.DELETE)
    public Object delPersonPerformanceAndDetail(@RequestBody PersonPerformance personPerformance) {
        String incomplete = "0";
        String completed = "1";
        PersonPerformanceSchedule performanceSchedule = iPersonPerformanceScheduleService.lambdaQuery()
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId())
                .eq(PersonPerformanceSchedule::getNum, "1").one();
        if (incomplete.equals(performanceSchedule.getApprStatus())) {
            iPersonPerformanceService.lambdaUpdate()
                    .eq(PersonPerformance::getId, personPerformance.getId()).remove();
            iPersonPerformanceDetailService.lambdaUpdate()
                    .eq(PersonPerformanceDetail::getPersonPerformanceId, personPerformance.getId()).remove();
            iPersonPerformanceScheduleService.lambdaUpdate()
                    .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId()).remove();
            return AjaxResult.success("删除成功");
        } else if (completed.equals(performanceSchedule.getApprStatus())) {
            return AjaxResult.error("计划输入状态已完成，不可以进行计划删除");
        } else {
            return AjaxResult.error("删除失败");
        }
    }
}
