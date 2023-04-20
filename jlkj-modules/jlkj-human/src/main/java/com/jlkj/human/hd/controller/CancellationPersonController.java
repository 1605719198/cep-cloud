package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.config.OrgPersonUtils;
import com.jlkj.human.hd.domain.AttendanceAbnormal;
import com.jlkj.human.hd.domain.CancellationPerson;
import com.jlkj.human.hd.dto.CancellationPersonDTO;
import com.jlkj.human.hd.service.IAttendanceAbnormalService;
import com.jlkj.human.hd.service.ICancellationPersonService;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IPersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author HuangBing
 * @Description
 * @create 2023-04-18 13:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cancellationPerson")
public class CancellationPersonController extends BaseController {

    private final ICancellationPersonService iCancellationPersonService;

    private final IPersonnelService personnelService;

    private final IAttendanceAbnormalService iAttendanceAbnormalService;

    /**
     * 查询人事注销列表
     */
    @RequiresPermissions("human:cancellationPerson:list")
    @Log(title = "查询人事注销列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询人事注销列表")
    @GetMapping("/list")
    public Object listCancellationPerson(CancellationPersonDTO cancellationPersonDTO) {
        startPage();
        CancellationPerson cancellationPerson = new CancellationPerson();
        BeanUtils.copyProperties(cancellationPersonDTO, cancellationPerson);
        List<CancellationPerson> list = iCancellationPersonService.lambdaQuery()
                .eq(StringUtils.isNotBlank(cancellationPerson.getCompanyId()), CancellationPerson::getCompanyId, cancellationPerson.getCompanyId())
                .eq(CancellationPerson::getOrgId, cancellationPerson.getOrgId())
                .or()
                .eq(CancellationPerson::getEmpNo, cancellationPerson.getEmpNo())
                .or()
                .eq(CancellationPerson::getClockWorkId, cancellationPerson.getClockWorkId())
                .apply("date_format (check_start_date,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                .apply("date_format (check_end_date,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                .list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 添加人事注销信息
     */
    @RequiresPermissions("human:cancellationPerson:add")
    @Operation(summary = "添加人事注销信息")
    @PostMapping
    @Log(title = "添加人事注销信息", businessType = BusinessType.INSERT)
    public Object addCancellationPerson(@RequestBody CancellationPersonDTO cancellationPersonDTO) throws ParseException {
        CancellationPerson cancellationPerson = new CancellationPerson();
        BeanUtils.copyProperties(cancellationPersonDTO, cancellationPerson);
        Personnel personnel = new Personnel();
        BeanUtils.copyProperties(cancellationPersonDTO, personnel);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cancellationPerson.setCheckStartDate(simpleDateFormat.parse(cancellationPersonDTO.getStartTime()));
        cancellationPerson.setCheckEndDate(simpleDateFormat.parse(cancellationPersonDTO.getEndTime()));
        cancellationPerson.setCreator(SecurityUtils.getNickName());
        String user = "user";
        String org = "org";
        List<Personnel> personnelList = personnelService.lambdaQuery().eq(Personnel::getEmpNo, cancellationPerson.getEmpNo()).list();
        if (personnelList.isEmpty()) {
            return AjaxResult.error("工号不存在，不能注销");
        } else {
            if (user.equals(cancellationPerson.getType())) {
                List<AttendanceAbnormal> attendanceAbnormalList = iAttendanceAbnormalService.lambdaQuery()
                        .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                        .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                        .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                        .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                        .list();
                if (attendanceAbnormalList.isEmpty()) {
                    return AjaxResult.error("已处理不能注销");
                } else {
                    boolean update = iAttendanceAbnormalService.lambdaUpdate()
                            .set(AttendanceAbnormal::getStatus, "05")
                            .set(AttendanceAbnormal::getDisposeId, "03")
                            .set(AttendanceAbnormal::getExcReaId, "03")
                            .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                            .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                            .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                            .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                            .update();
                    if (update) {
                        iCancellationPersonService.save(cancellationPerson);
//                        iAttendanceAbnormalService.lambdaUpdate()
//                                .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
//                                .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
//                                .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
//                                .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
//                                .remove();
                    }
                }
            } else if (org.equals(cancellationPerson.getType())) {
                List<HumanresourcePersonnelInfoDTO> list = OrgPersonUtils.orgPersonUtils.selectOrgPerson(cancellationPersonDTO.getDepartmentId());
                for (HumanresourcePersonnelInfoDTO item : list) {
                    List<AttendanceAbnormal> attendanceAbnormalList = iAttendanceAbnormalService.lambdaQuery()
                            .eq(AttendanceAbnormal::getEmpNo, item.getEmpNo())
                            .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                            .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                            .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                            .list();
                    if (attendanceAbnormalList.isEmpty()) {
                        boolean update = iAttendanceAbnormalService.lambdaUpdate()
                                .set(AttendanceAbnormal::getStatus, "05")
                                .set(AttendanceAbnormal::getDisposeId, "03")
                                .set(AttendanceAbnormal::getExcReaId, "03")
                                .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                                .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                                .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                                .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                                .update();
                        if (update) {
                            iCancellationPersonService.save(cancellationPerson);
//                            iAttendanceAbnormalService.lambdaUpdate()
//                                    .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
//                                    .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
//                                    .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
//                                    .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
//                                    .remove();
                        }
                    } else {
                        return AjaxResult.error("已处理不能注销");
                    }
                }
            } else {
                // 刷卡钟：查询卡钟配置下的人员的注销日期区间内的出勤异常
                return null;
            }
        }
        return null;
    }
}
