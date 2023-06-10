package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        if (user.equals(cancellationPerson.getType())) {
            if (personnelList.isEmpty()){
                return AjaxResult.error("工号不存在，不能注销");
            } else {
                List<AttendanceAbnormal> attendanceAbnormalList = iAttendanceAbnormalService.lambdaQuery()
                        .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                        .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                        .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                        .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                        .list();

                if (attendanceAbnormalList.isEmpty()) {
                    return AjaxResult.error("查无可注销出勤异常数据");
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
        return null;
    }

    /**
     * 添加人事批量注销信息
     */
    @RequiresPermissions("human:cancellationPerson:add")
    @Operation(summary = "添加人事批量注销信息")
    @PostMapping("/addBatchCancellationPerson")
    @Log(title = "添加人事批量注销信息", businessType = BusinessType.INSERT)
    public Object addBatchCancellationPerson(@RequestBody CancellationPersonDTO cancellationPersonDTO) throws ParseException {
        CancellationPerson cancellationPerson = new CancellationPerson();
        BeanUtils.copyProperties(cancellationPersonDTO, cancellationPerson);
        Personnel personnel = new Personnel();
        BeanUtils.copyProperties(cancellationPersonDTO, personnel);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cancellationPerson.setCheckStartDate(simpleDateFormat.parse(cancellationPersonDTO.getStartTime()));
        cancellationPerson.setCheckEndDate(simpleDateFormat.parse(cancellationPersonDTO.getEndTime()));
        cancellationPerson.setCreator(SecurityUtils.getNickName());
        for (CancellationPerson item : cancellationPersonDTO.getUserInfo()){
            List<Personnel> personnelList = personnelService.lambdaQuery().eq(Personnel::getEmpNo, item.getEmpNo()).list();
            if (personnelList.isEmpty()) {
                return AjaxResult.error("查无可注销出勤异常数据");
            } else {
                List<AttendanceAbnormal> attendanceAbnormalList = iAttendanceAbnormalService.lambdaQuery()
                        .eq(AttendanceAbnormal::getEmpNo, item.getEmpNo())
                        .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                        .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                        .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                        .list();
                if (attendanceAbnormalList.isEmpty()) {
                    return AjaxResult.error("工号:" + item.getEmpNo() + "已处理不能注销");
                } else {
                    boolean update = iAttendanceAbnormalService.lambdaUpdate()
                            .set(AttendanceAbnormal::getStatus, "05")
                            .set(AttendanceAbnormal::getDisposeId, "03")
                            .set(AttendanceAbnormal::getExcReaId, "03")
                            .eq(AttendanceAbnormal::getEmpNo, item.getEmpNo())
                            .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                            .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                            .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                            .update();
                    if (update) {
                        iCancellationPersonService.save(cancellationPerson);
                    }
                }
            }
        }
        return AjaxResult.success("注销成功");
    }


    @Log(title = "基于表格注销", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:cancellationPerson:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<CancellationPerson> util = new ExcelUtil<CancellationPerson>(CancellationPerson.class);
        List<CancellationPerson> cancellationPersonList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = iCancellationPersonService.importUser(cancellationPersonList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载注销模板
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<CancellationPerson> util = new ExcelUtil<CancellationPerson>(CancellationPerson.class);
        util.importTemplateExcel(response, "注销模板");
    }

    /** 出勤异常确认作业确认旷工 */
    @RequiresPermissions("human:attendanceAnomalyConfirmation:confirm")
    @Operation(summary = "出勤异常确认作业确认旷工")
    @PostMapping("/confirm")
    @Log(title = "出勤异常确认作业确认旷工", businessType = BusinessType.INSERT)
    public Object confirmAttendanceAnomaly(@RequestBody CancellationPersonDTO cancellationPersonDTO) throws ParseException {
        CancellationPerson cancellationPerson = new CancellationPerson();
        BeanUtils.copyProperties(cancellationPersonDTO, cancellationPerson);
        Personnel personnel = new Personnel();
        BeanUtils.copyProperties(cancellationPersonDTO, personnel);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cancellationPerson.setCheckStartDate(simpleDateFormat.parse(cancellationPersonDTO.getStartTime()));
        cancellationPerson.setCheckEndDate(simpleDateFormat.parse(cancellationPersonDTO.getEndTime()));
        cancellationPerson.setCreator(SecurityUtils.getNickName());
        List<Personnel> personnelList = personnelService.lambdaQuery().eq(Personnel::getEmpNo, cancellationPerson.getEmpNo()).list();
        if (personnelList.isEmpty()) {
            return AjaxResult.error("工号:" + cancellationPerson.getEmpNo() + "不存在，不能注销");
        } else {
            List<AttendanceAbnormal> attendanceAbnormalList = iAttendanceAbnormalService.lambdaQuery()
                    .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                    .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                    .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                    .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                    .list();
            if (attendanceAbnormalList.isEmpty()) {
                return AjaxResult.error("工号:" + cancellationPerson.getEmpNo() + "已处理不能注销");
            } else {
                boolean update = iAttendanceAbnormalService.lambdaUpdate()
                        .set(AttendanceAbnormal::getStatus, "05")
                        .set(AttendanceAbnormal::getAuditType, "0")
                        .set(AttendanceAbnormal::getDisposeId, "12")
                        .set(AttendanceAbnormal::getExcReaId, "08")
                        .set(AttendanceAbnormal::getUndutyType, "3")
                        .eq(AttendanceAbnormal::getEmpNo, cancellationPerson.getEmpNo())
                        .and(i -> i.eq(AttendanceAbnormal::getDisposeId, "09").or().eq(AttendanceAbnormal::getDisposeId, "08"))
                        .apply("date_format (slot_card_onduty,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getStartTime())
                        .apply("date_format (slot_card_offduty,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", cancellationPersonDTO.getEndTime())
                        .update();
                if (update) {
                    iCancellationPersonService.save(cancellationPerson);
                }
            }
            return AjaxResult.success("已确认旷工");
        }
    }
}
