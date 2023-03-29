package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.AttendenceRecord;
import com.jlkj.human.hd.dto.AttendenceRecordDTO;
import com.jlkj.human.hd.service.IAttendenceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-03-29 13:42
 */
@RestController
@RequestMapping("/attendenceRecord")
public class AttendenceRecordController extends BaseController {

    @Autowired
    IAttendenceRecordService iAttendenceRecordService;

    /**
     * 查询日常出勤记录列表
     */
    @RequiresPermissions("human:attendenceRecord:list")
    @Log(title = "查询日常出勤记录列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询日常出勤记录列表")
    @GetMapping("/list")
    public Object listAttendenceRecord(AttendenceRecordDTO attendenceRecordDTO) {
        startPage();
        AttendenceRecord attendenceRecord = new AttendenceRecord();
        BeanUtils.copyProperties(attendenceRecordDTO, attendenceRecord);
        List<AttendenceRecord> list = iAttendenceRecordService.lambdaQuery()
                .eq(StringUtils.isNotBlank(attendenceRecord.getCompId()), AttendenceRecord::getCompId, attendenceRecord.getCompId())
                .eq(AttendenceRecord::getEmpNo, attendenceRecord.getEmpNo())
                .between(AttendenceRecord::getSlotCardDate, attendenceRecordDTO.getStartTime(), attendenceRecordDTO.getEndTime()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }
}
