package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.AttendanceAbnormal;
import com.jlkj.human.hd.dto.AttendanceAbnormalDTO;
import com.jlkj.human.hd.service.IAttendanceAbnormalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-04-7 11:48
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/attendanceAbnormal")
public class AttendanceAbnormalController extends BaseController {

    private final IAttendanceAbnormalService iAttendanceAbnormalService;

    /**
     * 查询员工出勤异常记录列表
     */
    @RequiresPermissions("human:attendanceAbnormal:list")
    @Log(title = "查询员工出勤异常记录列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工出勤异常记录列表")
    @GetMapping("/list")
    public Object listAttendanceAbnormal(AttendanceAbnormalDTO attendanceAbnormalDTO) {
        startPage();
        AttendanceAbnormal attendanceAbnormal = new AttendanceAbnormal();
        BeanUtils.copyProperties(attendanceAbnormalDTO, attendanceAbnormal);
        List<AttendanceAbnormal> list = iAttendanceAbnormalService.lambdaQuery()
                .eq(StringUtils.isNotBlank(attendanceAbnormal.getCompId()), AttendanceAbnormal::getCompId, attendanceAbnormal.getCompId())
                .eq(AttendanceAbnormal::getEmpNo, attendanceAbnormal.getEmpNo())
                .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", attendanceAbnormalDTO.getStartTime())
                .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", attendanceAbnormalDTO.getEndTime()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 查询个人出勤异常
     */
    @RequiresPermissions("human:attendanceAbnormalQuery:list")
    @Log(title = "查询个人出勤异常",businessType = BusinessType.OTHER)
    @Operation(summary = "查询个人出勤异常")
    @GetMapping("/abnormal")
    public Object listAttendanceAbnormalQuery(AttendanceAbnormalDTO attendanceAbnormalDTO) {
        startPage();
        AttendanceAbnormal attendanceAbnormal = new AttendanceAbnormal();
        BeanUtils.copyProperties(attendanceAbnormalDTO, attendanceAbnormal);
        List<AttendanceAbnormal> list = iAttendanceAbnormalService.lambdaQuery()
                .eq(StringUtils.isNotBlank(attendanceAbnormal.getCompId()), AttendanceAbnormal::getCompId, attendanceAbnormal.getCompId())
                .eq(AttendanceAbnormal::getEmpNo, attendanceAbnormal.getEmpNo())
                .eq(AttendanceAbnormal::getDisposeId, attendanceAbnormal.getDisposeId())
                .between(AttendanceAbnormal::getSlotCardOnduty, attendanceAbnormalDTO.getStartTime(), attendanceAbnormalDTO.getEndTime())
                .or()
                .between(AttendanceAbnormal::getSlotCardOffduty, attendanceAbnormalDTO.getStartTime(), attendanceAbnormalDTO.getEndTime()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }



    /**
     * 根据uuid查询出勤异常详细信息
     */
    @RequiresPermissions("human:attendanceAbnormal:query")
    @Operation(summary = "根据uuid查询出勤异常详细信息")
    @GetMapping("/{uuid}")
    @Log(title = "根据uuid查询出勤异常详细信息", businessType = BusinessType.OTHER)
    public Object getAttendanceAbnormal(@PathVariable String uuid) {
        AttendanceAbnormal abnormal = iAttendanceAbnormalService.lambdaQuery().eq(AttendanceAbnormal::getId, uuid).one();
        return AjaxResult.success("查询成功", abnormal);
    }

    /**
     * 修改出勤异常申请信息
     */
    @RequiresPermissions("human:attendanceAbnormal:edit")
    @Operation(summary = "修改出勤异常申请信息")
    @PutMapping
    @Log(title = "修改出勤异常申请信息", businessType = BusinessType.UPDATE)
    public Object updateAttendanceAbnormal(@RequestBody AttendanceAbnormal attendanceAbnormal) {
        iAttendanceAbnormalService.updateById(attendanceAbnormal);
        return AjaxResult.success("修改成功");
    }

    /**
     * 送审
     */
    @RequiresPermissions("human:attendanceAbnormal:edit")
    @Operation(summary = "出勤异常申请信息送审")
    @PostMapping("/send")
    @Log(title = "出勤异常申请信息送审", businessType = BusinessType.UPDATE)
    public Object sendAttendanceAbnormal(@RequestBody AttendanceAbnormal attendanceAbnormal) {
        String status = "01";
        if (status.equals(attendanceAbnormal.getStatus())) {
            attendanceAbnormal.setStatus("03");
            iAttendanceAbnormalService.updateById(attendanceAbnormal);
            return AjaxResult.success("送审成功");
        } else {
            return AjaxResult.success("审批状态为未送审时，才允许送审！");
        }

    }

    /**
     * 撤回
     */
    @RequiresPermissions("human:attendanceAbnormal:edit")
    @Operation(summary = "出勤异常申请信息撤回")
    @PostMapping("/recall")
    @Log(title = "出勤异常申请信息撤回", businessType = BusinessType.UPDATE)
    public Object recallAttendanceAbnormal(@RequestBody AttendanceAbnormal attendanceAbnormal) {
        String status = "03";
        if (status.equals(attendanceAbnormal.getStatus())) {
            attendanceAbnormal.setStatus("01");
            iAttendanceAbnormalService.updateById(attendanceAbnormal);
            return AjaxResult.success("撤回成功");
        } else {
            return AjaxResult.success("审批状态为审核中时，才允许撤回！");
        }
    }

    /**
     * 查询出勤异常确认列表
     */
    @RequiresPermissions("human:attendanceAnomalyConfirmation:list")
    @Log(title = "查询出勤异常确认列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询出勤异常确认列表")
    @GetMapping("/attendanceAnomalyConfirmationList")
    public Object listAttendanceAnomalyConfirmation(AttendanceAbnormalDTO attendanceAbnormalDTO) {
        startPage();
        AttendanceAbnormal attendanceAbnormal = new AttendanceAbnormal();
        BeanUtils.copyProperties(attendanceAbnormalDTO, attendanceAbnormal);
        List<AttendanceAbnormal> list = iAttendanceAbnormalService.lambdaQuery()
                .eq(StringUtils.isNotBlank(attendanceAbnormal.getCompId()), AttendanceAbnormal::getCompId, attendanceAbnormal.getCompId())
                .eq(AttendanceAbnormal::getEmpNo, attendanceAbnormal.getEmpNo())
                .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "08")).or().eq(AttendanceAbnormal::getDisposeId, "09")
                .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", attendanceAbnormalDTO.getStartTime())
                .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", attendanceAbnormalDTO.getEndTime()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }
}
