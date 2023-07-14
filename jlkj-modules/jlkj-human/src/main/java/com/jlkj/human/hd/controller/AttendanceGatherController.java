package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.AttendanceConfirmation;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;
import com.jlkj.human.hd.service.IAttendanceConfirmationService;
import com.jlkj.human.hd.service.IAttendanceGatherService;
import com.jlkj.human.hp.domain.HumanDept;
import com.jlkj.human.hp.service.IHumanDeptService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private final IAttendanceConfirmationService iAttendanceConfirmationService;
    private final IHumanDeptService iHumanDeptService;

    /**
     * 查询员工出勤汇总列表
     */
    @RequiresPermissions("human:attendanceGather:list")
    @Log(title = "查询员工出勤汇总列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工出勤汇总列表")
    @GetMapping("/list")
    public Object listAttendanceGather(AttendanceGatherDTO attendanceGatherDTO) {
        startPage();
        List<AttendanceGatherDTO> list = iAttendanceGatherService.selectJoinList(attendanceGatherDTO);
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 查询出勤汇总列表
     */
    @RequiresPermissions("human:attendanceGather:list")
    @Log(title = "查询员工出勤汇总列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工出勤汇总列表")
    @GetMapping("/attendanceList")
    public Object listAttendance(AttendanceGatherDTO attendanceGatherDTO) {
        startPage();
        String real = "true";
        if (real.equals(attendanceGatherDTO.getIncludingSubsidiaries())) {
            HumanDept humanDept = iHumanDeptService.selectSysDeptByDeptCode(attendanceGatherDTO.getCompId());
            List<HumanDept> list = iHumanDeptService.selectParentIdByDeptCode(humanDept.getDeptId());
            List<AttendanceGatherDTO> gatherList = iAttendanceGatherService.selectJoinList(attendanceGatherDTO);
            for (HumanDept item : list) {
                attendanceGatherDTO.setCompId(item.getDeptCode());
                List<AttendanceGatherDTO> gatherListA = iAttendanceGatherService.selectCompJoinList(attendanceGatherDTO);
                gatherList.addAll(gatherListA);
            }
            return AjaxResult.success("查询成功", getDataTable(gatherList));
        } else {
            List<AttendanceGatherDTO> list = iAttendanceGatherService.selectCompJoinList(attendanceGatherDTO);
            return AjaxResult.success("查询成功", getDataTable(list));
        }
    }


    /**
     * 导出出勤汇总列表
     */
    @RequiresPermissions("human:attendanceGather:export")
    @Log(title = "导出出勤汇总列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttendanceGatherDTO attendanceGatherDTO)
    {
       iAttendanceGatherService.exportExcel(response, attendanceGatherDTO);
    }

    /**
     * 取消排班操作
     */
    @RequiresPermissions("human:attendanceGather:cancelScheduling")
    @Log(title = "取消排班操作",businessType = BusinessType.OTHER)
    @Operation(summary = "取消排班操作")
    @PostMapping("/cancelScheduling")
    public Object cancelScheduling(@RequestBody AttendanceGatherDTO attendanceGatherDTO) {
        AttendanceConfirmation attendanceConfirmation = new AttendanceConfirmation();
        BeanUtils.copyProperties(attendanceGatherDTO,attendanceConfirmation);
        List<AttendanceConfirmation> list = iAttendanceConfirmationService.lambdaQuery()
                .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
        if (list.isEmpty()) {
            attendanceConfirmation.setIsCon("0");
            attendanceConfirmation.setShiftStatus("0");
            attendanceConfirmation.setStatus("0");
            attendanceConfirmation.setYear(attendanceGatherDTO.getDate().substring(0, 4));
            attendanceConfirmation.setMonth(attendanceGatherDTO.getDate().substring(5, 7));
            attendanceConfirmation.setCreator(SecurityUtils.getNickName());
            iAttendanceConfirmationService.save(attendanceConfirmation);
            return AjaxResult.error("不存在数据，已新增一笔");
        } else {
            for (AttendanceConfirmation item : list) {
                if ("0".equals(item.getShiftStatus())) {
                    iAttendanceConfirmationService.lambdaUpdate()
                            .set(AttendanceConfirmation::getShiftStatus, "1")
                            .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                            .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                            .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).update();
                } else {
                    return AjaxResult.error("取消排班失败，排班状态为取消排班");
                }
            }
            return AjaxResult.success("取消排班成功");
        }
    }

    /**
     * 反（取消排班）操作
     */
    @RequiresPermissions("human:attendanceGather:oppositeCancelScheduling")
    @Log(title = "反（取消排班）操作",businessType = BusinessType.OTHER)
    @Operation(summary = "反（取消排班）操作")
    @PostMapping("/oppositeCancelScheduling")
    public Object oppositeCancelScheduling(@RequestBody AttendanceGatherDTO attendanceGatherDTO) {
        AttendanceConfirmation attendanceConfirmation = new AttendanceConfirmation();
        BeanUtils.copyProperties(attendanceGatherDTO,attendanceConfirmation);
        List<AttendanceConfirmation> list = iAttendanceConfirmationService.lambdaQuery()
                .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
        if (list.isEmpty()) {
            attendanceConfirmation.setIsCon("0");
            attendanceConfirmation.setShiftStatus("0");
            attendanceConfirmation.setStatus("0");
            attendanceConfirmation.setYear(attendanceGatherDTO.getDate().substring(0, 4));
            attendanceConfirmation.setMonth(attendanceGatherDTO.getDate().substring(5, 7));
            attendanceConfirmation.setCreator(SecurityUtils.getNickName());
            iAttendanceConfirmationService.save(attendanceConfirmation);
            return AjaxResult.error("不存在数据，已新增一笔");
        } else {
            for (AttendanceConfirmation item : list) {
                if ("1".equals(item.getShiftStatus())) {
                    iAttendanceConfirmationService.lambdaUpdate()
                            .set(AttendanceConfirmation::getShiftStatus, "0")
                            .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                            .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                            .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).update();
                } else {
                    return AjaxResult.error("反（取消排班）失败，排班状态为恢复排班");
                }
            }
            return AjaxResult.success("反（取消排班）成功");
        }
    }

    /**
     * 确认操作
     */
    @RequiresPermissions("human:attendanceGather:confirm")
    @Log(title = "确认操作",businessType = BusinessType.OTHER)
    @Operation(summary = "确认操作")
    @PostMapping("/confirm")
    public Object confirm(@RequestBody AttendanceGatherDTO attendanceGatherDTO) {
        AttendanceConfirmation attendanceConfirmation = new AttendanceConfirmation();
        BeanUtils.copyProperties(attendanceGatherDTO,attendanceConfirmation);
        List<AttendanceConfirmation> list = iAttendanceConfirmationService.lambdaQuery()
                .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
        if (list.isEmpty()) {
            attendanceConfirmation.setIsCon("0");
            attendanceConfirmation.setShiftStatus("0");
            attendanceConfirmation.setStatus("0");
            attendanceConfirmation.setYear(attendanceGatherDTO.getDate().substring(0, 4));
            attendanceConfirmation.setMonth(attendanceGatherDTO.getDate().substring(5, 7));
            attendanceConfirmation.setCreator(SecurityUtils.getNickName());
            iAttendanceConfirmationService.save(attendanceConfirmation);
            return AjaxResult.error("不存在数据，已新增一笔");
        } else {
            for (AttendanceConfirmation item : list) {
                if ("1".equals(item.getShiftStatus())&&"0".equals(item.getStatus())) {
                    iAttendanceConfirmationService.lambdaUpdate()
                            .set(AttendanceConfirmation::getIsCon, "1")
                            .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                            .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                            .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).update();
                } else {
                    return AjaxResult.error("确认失败，排班状态、薪资确认状态错误");
                }
            }
            return AjaxResult.success("确认成功");
        }
    }

    /**
     * 反确认（考勤）操作
     */
    @RequiresPermissions("human:attendanceGather:counterConfirmation")
    @Log(title = "反确认（考勤）操作",businessType = BusinessType.OTHER)
    @Operation(summary = "反确认（考勤）操作")
    @PostMapping("/counterConfirmation")
    public Object counterConfirmation(@RequestBody AttendanceGatherDTO attendanceGatherDTO) {
        AttendanceConfirmation attendanceConfirmation = new AttendanceConfirmation();
        BeanUtils.copyProperties(attendanceGatherDTO,attendanceConfirmation);
        List<AttendanceConfirmation> list = iAttendanceConfirmationService.lambdaQuery()
                .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
        if (list.isEmpty()) {
            attendanceConfirmation.setIsCon("0");
            attendanceConfirmation.setShiftStatus("0");
            attendanceConfirmation.setStatus("0");
            attendanceConfirmation.setYear(attendanceGatherDTO.getDate().substring(0, 4));
            attendanceConfirmation.setMonth(attendanceGatherDTO.getDate().substring(5, 7));
            attendanceConfirmation.setCreator(SecurityUtils.getNickName());
            iAttendanceConfirmationService.save(attendanceConfirmation);
            return AjaxResult.error("不存在数据，已新增一笔");
        } else {
            for (AttendanceConfirmation item : list) {
                if ("0".equals(item.getStatus())) {
                    iAttendanceConfirmationService.lambdaUpdate()
                            .set(AttendanceConfirmation::getIsCon, "0")
                            .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                            .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                            .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).update();
                } else {
                    return AjaxResult.error("反确认（考勤）失败，薪资确认状态已确认");
                }
            }
            return AjaxResult.success("反确认（考勤）成功");
        }
    }

    /**
     * 确认（薪资可计算）操作
     */
    @RequiresPermissions("human:attendanceGather:confirmSalaryCalculation")
    @Log(title = "确认（薪资可计算）操作",businessType = BusinessType.OTHER)
    @Operation(summary = "确认（薪资可计算）操作")
    @PostMapping("/confirmSalaryCalculation")
    public Object confirmSalaryCalculation(@RequestBody AttendanceGatherDTO attendanceGatherDTO) {
        AttendanceConfirmation attendanceConfirmation = new AttendanceConfirmation();
        BeanUtils.copyProperties(attendanceGatherDTO,attendanceConfirmation);
        List<AttendanceConfirmation> list = iAttendanceConfirmationService.lambdaQuery()
                .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
        if (list.isEmpty()) {
            attendanceConfirmation.setIsCon("0");
            attendanceConfirmation.setShiftStatus("0");
            attendanceConfirmation.setStatus("0");
            attendanceConfirmation.setYear(attendanceGatherDTO.getDate().substring(0, 4));
            attendanceConfirmation.setMonth(attendanceGatherDTO.getDate().substring(5, 7));
            attendanceConfirmation.setCreator(SecurityUtils.getNickName());
            iAttendanceConfirmationService.save(attendanceConfirmation);
            return AjaxResult.error("不存在数据，已新增一笔");
        } else {
            for (AttendanceConfirmation item : list) {
                if ("1".equals(item.getIsCon())&&"1".equals(item.getShiftStatus())) {
                    iAttendanceConfirmationService.lambdaUpdate()
                            .set(AttendanceConfirmation::getStatus, "1")
                            .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                            .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                            .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).update();
                } else {
                    return AjaxResult.error("确认（可薪资计算）失败，确认状态、排班状态错误");
                }
            }
            return AjaxResult.success("确认（可薪资计算）成功");
        }
    }

    /**
     * 反确认（薪资可计算）操作
     */
    @RequiresPermissions("human:attendanceGather:counterConfirmationSalaryCalculation")
    @Log(title = "反确认（薪资可计算）操作",businessType = BusinessType.OTHER)
    @Operation(summary = "反确认（薪资可计算）操作")
    @PostMapping("/counterConfirmationSalaryCalculation")
    public Object counterConfirmationSalaryCalculation(@RequestBody AttendanceGatherDTO attendanceGatherDTO) {
        AttendanceConfirmation attendanceConfirmation = new AttendanceConfirmation();
        BeanUtils.copyProperties(attendanceGatherDTO,attendanceConfirmation);
        List<AttendanceConfirmation> list = iAttendanceConfirmationService.lambdaQuery()
                .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
        if (list.isEmpty()) {
            attendanceConfirmation.setIsCon("0");
            attendanceConfirmation.setShiftStatus("0");
            attendanceConfirmation.setStatus("0");
            attendanceConfirmation.setYear(attendanceGatherDTO.getDate().substring(0, 4));
            attendanceConfirmation.setMonth(attendanceGatherDTO.getDate().substring(5, 7));
            attendanceConfirmation.setCreator(SecurityUtils.getNickName());
            iAttendanceConfirmationService.save(attendanceConfirmation);
            return AjaxResult.error("不存在数据，已新增一笔");
        } else {
            for (AttendanceConfirmation item : list) {
                if ("1".equals(item.getIsCon())) {
                    iAttendanceConfirmationService.lambdaUpdate()
                            .set(AttendanceConfirmation::getStatus, "0")
                            .eq(AttendanceConfirmation::getCompId, attendanceGatherDTO.getCompId())
                            .eq(AttendanceConfirmation::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                            .eq(AttendanceConfirmation::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).update();
                } else {
                    return AjaxResult.error("反确认（可薪资计算）失败，确认状态为取消确认");
                }
            }
            return AjaxResult.success("反确认（可薪资计算）成功");
        }
    }

    @Log(title = "出勤汇总导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:attendanceGather:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<AttendanceGatherDTO> util = new ExcelUtil<AttendanceGatherDTO>(AttendanceGatherDTO.class);
        List<AttendanceGatherDTO> attendanceGatherList = util.importExcel(file.getInputStream(),1);
        String operName = SecurityUtils.getUsername();
        String message = iAttendanceGatherService.importUser(attendanceGatherList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入出勤汇总数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        iAttendanceGatherService.importTemplate(response);
    }

    /**
     * 月初出勤汇总
     *
     * @return 出勤汇总结果
     * @author 266861
     * @Date 2023/7/12 13:34
     **/
    @GetMapping("/summary")
    public AjaxResult attendanceSummary(){
        return success(iAttendanceGatherService.attendanceSummary());
    }
}
