package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;
import com.jlkj.human.hd.service.IAttendanceGatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-04-6 09:16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/attendanceGather")
public class AttendanceGatherController extends BaseController {

    private final IAttendanceGatherService iAttendanceGatherService;

    /**
     * 查询员工出勤汇总列表
     */
    @RequiresPermissions("human:attendanceGather:list")
    @Log(title = "查询员工出勤汇总列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工出勤汇总列表")
    @GetMapping("/list")
    public Object listAttendanceGather(AttendanceGatherDTO attendanceGatherDTO) {
        startPage();
        AttendanceGather attendanceGather = new AttendanceGather();
        BeanUtils.copyProperties(attendanceGatherDTO, attendanceGather);
        List<AttendanceGather> list = iAttendanceGatherService.lambdaQuery()
                .eq(StringUtils.isNotBlank(attendanceGather.getCompId()), AttendanceGather::getCompId, attendanceGather.getCompId())
                .eq(AttendanceGather::getEmpNo, attendanceGather.getEmpNo()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }
}
