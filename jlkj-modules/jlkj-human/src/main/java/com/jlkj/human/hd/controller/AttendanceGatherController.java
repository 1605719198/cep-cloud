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
import com.jlkj.human.hd.domain.AttendanceConfirmation;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;
import com.jlkj.human.hd.service.IAttendanceConfirmationService;
import com.jlkj.human.hd.service.IAttendanceGatherService;
import com.jlkj.human.hp.domain.SysDept;
import com.jlkj.human.hp.service.ISysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    private final ISysDeptService iSysDeptService;

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

    /**
     * 查询出勤汇总列表
     */
    @RequiresPermissions("human:attendanceGather:list")
    @Log(title = "查询员工出勤汇总列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询员工出勤汇总列表")
    @GetMapping("/attendanceList")
    public Object listAttendance(AttendanceGatherDTO attendanceGatherDTO) {
        startPage();
        AttendanceGather attendanceGather = new AttendanceGather();
        BeanUtils.copyProperties(attendanceGatherDTO, attendanceGather);
        String real = "true";
        if (real.equals(attendanceGatherDTO.getIncludingSubsidiaries())) {
            SysDept sysDept = iSysDeptService.selectSysDeptByDeptCode(attendanceGatherDTO.getCompId());
            List<SysDept> list = iSysDeptService.selectParentIdByDeptCode(sysDept.getDeptId());
            List<AttendanceGather> gatherList = iAttendanceGatherService.lambdaQuery()
                    .eq(AttendanceGather::getCompId, attendanceGatherDTO.getCompId())
                    .eq(AttendanceGather::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                    .eq(AttendanceGather::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
            for (SysDept item : list) {
                List<AttendanceGather> gatherListA = iAttendanceGatherService.lambdaQuery()
                        .eq(AttendanceGather::getCompId, item.getDeptCode())
                        .eq(AttendanceGather::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                        .eq(AttendanceGather::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
                gatherList.addAll(gatherListA);
            }
            return AjaxResult.success("查询成功", getDataTable(gatherList));
        } else {
            List<AttendanceGather> list = iAttendanceGatherService.lambdaQuery()
                    .eq(AttendanceGather::getCompId, attendanceGatherDTO.getCompId())
                    .eq(AttendanceGather::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                    .eq(AttendanceGather::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
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
            List<AttendanceGather> list = iAttendanceGatherService.lambdaQuery()
                    .eq(AttendanceGather::getCompId, attendanceGatherDTO.getCompId())
                    .eq(AttendanceGather::getYear, attendanceGatherDTO.getDate().substring(0, 4))
                    .eq(AttendanceGather::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
            ExcelUtil<AttendanceGather> util = new ExcelUtil<AttendanceGather>(AttendanceGather.class);
            util.exportExcel(response, list, "出勤汇总数据");
//        //1.创建一个workbook,对应一个excel文件
//        HSSFWorkbook wb = new HSSFWorkbook();
//
//        //2.在workbook中添加一个sheet,对应Excel中的sheet
//        HSSFSheet sheet = wb.createSheet("出勤汇总");
//
//        //设置每一列的列宽
//        for (int i = 0; i < 28; i++) {
//            sheet.setColumnWidth(0,256*15);
//        }
//
//        //3.设置样式以及字体样式
//        HSSFCellStyle headerStyle = ExcelUtil.createHeadCellStyle(wb);
//        HSSFCellStyle contentStyle = ExcelUtil.createContentCellStyle(wb);
//
//        //4.创建标题，合并标题单元格
//        //行号
//        int rowNum = 0;
//
//        //第一行
//        HSSFRow row0 = sheet.createRow(rowNum++);
//        row0.setHeight((short)500);
//        String[] row_first = {"工号","姓名","岗位","迟到次数","早退次数","大夜班次数","小夜班次数","请假天数","加班天数","月初未上岗","月末未上岗","存班小时数","旷职天数","应出勤天数","实际出勤天数"};
//        for (int i = 0; i < row_first.length; i++) {
//            HSSFCell tempCell = row0.createCell(i);
//            tempCell.setCellValue(row_first[i]);
//            tempCell.setCellStyle(headerStyle);
//        }
//        //合并单元格
//        //工号
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
//        //姓名
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
//        //岗位
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
//        //迟到次数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
//        //早退次数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
//        //大夜班次数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 5, 5));
//        //小夜班次数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 6, 6));
//        //请假天数
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 17));
//        //加班天数
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 18, 21));
//        //月初未上岗
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 22, 22));
//        //月末未上岗
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 23, 23));
//        //存班小时数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 24, 24));
//        //旷职天数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 25, 25));
//        //应出勤天数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 26, 26));
//        //实际出勤天数
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 27, 27));
//
//        //第二行
//        HSSFRow row1 = sheet.createRow(rowNum++);
//        row1.setHeight((short)500);
//        String[] row_second = {"","","","","","","","事假","病假","婚假","产假","护理假","丧假","年休假","公假","工伤假","外派公假","合计","延时加班","休息日加班","法定假加班","合计","","","","","",""};
//        for (int i = 0; i < row_second.length; i++) {
//            HSSFCell tempCell = row1.createCell(i);
//            tempCell.setCellValue(row_second[i]);
//            tempCell.setCellStyle(headerStyle);
//        }
//
//        //查询出勤汇总列表
//        List<AttendanceGather> list = iAttendanceGatherService.lambdaQuery()
//                .eq(AttendanceGather::getCompId, attendanceGatherDTO.getCompId())
//                .eq(AttendanceGather::getYear, attendanceGatherDTO.getDate().substring(0, 4))
//                .eq(AttendanceGather::getMonth, attendanceGatherDTO.getDate().substring(5, 7)).list();
//
//        for(int i = 0;i<list.size();i++){
//            HSSFRow tempRow = sheet.createRow(rowNum++);
//            tempRow.setHeight((short)500);
//            //循环单元格填入数据
//            for(int j=0;j<27;j++){
//                HSSFCell tempCell = tempRow.createCell(j);
//                tempCell.setCellStyle(contentStyle);
//                String cellValue = "";
//                if(j == 0){
//                    //工号
//                    cellValue = list.get(i).getEmpNo();
//                }else if(j == 1){
//                    cellValue = list.get(i).getEmpName();
//                }else if(j ==2){
//                    cellValue = list.get(i).getPostName();
//                }else if(j == 3){
//                    cellValue = list.get(i).getLateNum().toString();
//                }else if(j ==4){
//                    cellValue = list.get(i).getLeaNum().toString();
//                }else if(j == 5){
//                    cellValue = list.get(i).getBigNig().toString();
//                }else if(j == 6){
//                    cellValue = list.get(i).getSmaNig().toString();
//                }else if(j ==7){//查询加班汇总表(请假)
////                    cellValue = list.get(i)
//                }else if(j == 8){
//                    //                    cellValue = list.get(i)
//                }else if(j == 9){
//                    //                    cellValue = list.get(i)
//                }else if(j == 10){
//                    //                    cellValue = list.get(i)
//                }else if(j == 11){
//                    //                    cellValue = list.get(i)
//                }else if( j == 12){
//                    //                    cellValue = list.get(i)
//                }else if(j == 13){
//                    //                    cellValue = list.get(i)
//                }else if(j ==14){
//                    //                    cellValue = list.get(i)
//                }else if(j == 15){
//                    //                    cellValue = list.get(i)
//                }else if(j == 16){
//                    //                    cellValue = list.get(i)
//                }else if(j == 17){
//                    //                    cellValue = list.get(i)
//                }else if(j == 18){//查询加班表
//                    //                    cellValue = list.get(i)
//                }else if(j == 19){
//                    //                    cellValue = list.get(i)
//                }else if(j == 20){
//                    //                    cellValue = list.get(i)
//                }else if(j == 21){
//                    //                    cellValue = list.get(i)
//                }else if(j == 22){
//                    cellValue = String.valueOf(list.get(i).getBefEntDuty());
//                }else if(j == 23){
//                    cellValue = String.valueOf(list.get(i).getAftEntDuty());
//                }else if(j == 24){
//                    cellValue = list.get(i).getSavHou();
//                }else if(j == 25){
//                    cellValue = String.valueOf(list.get(i).getTruDuty());
//                }else if(j == 26){
//                    cellValue = String.valueOf(list.get(i).getDueAttDuty());
//                }else if(j == 27){
//                    cellValue = String.valueOf(list.get(i).getActAttDuty());
//                }
//                tempCell.setCellValue(cellValue);
//            }
//        }
//
//        //导出excel
//        String fileName = "出勤汇总表.xls";
//        try {
//            fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
//            response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
//            OutputStream stream = response.getOutputStream();
//            if(null != wb && null != stream){
//                wb.write(stream);
//                wb.close();
//                stream.close();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
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

//    @Log(title = "出勤汇总导入", businessType = BusinessType.IMPORT)
//    @RequiresPermissions("human:attendanceGather:import")
//    @PostMapping("/importData")
//    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
//    {
//        ExcelUtil<AttendanceGatherDTO> util = new ExcelUtil<AttendanceGatherDTO>(AttendanceGatherDTO.class);
//        List<AttendanceGatherDTO> attendanceGatherList = util.importExcel(file.getInputStream(),1);
//        String operName = SecurityUtils.getUsername();
//        String message = iAttendanceGatherService.importUser(attendanceGatherList, updateSupport, operName);
//        return success(message);
//    }
}
