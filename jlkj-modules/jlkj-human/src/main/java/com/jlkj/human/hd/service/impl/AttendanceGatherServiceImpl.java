package com.jlkj.human.hd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.BeanCopyUtils;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hd.domain.AttendanceAbnormal;
import com.jlkj.human.hd.domain.AttendanceGather;
import com.jlkj.human.hd.domain.SaveTime;
import com.jlkj.human.hd.domain.ShiftCode;
import com.jlkj.human.hd.dto.AttendanceAbnormalDTO;
import com.jlkj.human.hd.dto.AttendanceGatherDTO;
import com.jlkj.human.hd.dto.PersonShiftCodeDTO;
import com.jlkj.human.hd.mapper.AttendanceGatherMapper;
import com.jlkj.human.hd.service.IAttendanceAbnormalService;
import com.jlkj.human.hd.service.IAttendanceGatherService;
import com.jlkj.human.hd.service.ISaveTimeService;
import com.jlkj.human.hd.service.IShiftCodeService;
import com.jlkj.human.hm.domain.Leave;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IPersonnelService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HuangBing
 * @description 针对表【human_hd_attendance_gather(月出勤统计汇总表)】的数据库操作Service实现
 * @createDate 2023-04-06 09:15:10
 */
@Service
public class AttendanceGatherServiceImpl extends ServiceImpl<AttendanceGatherMapper, AttendanceGather>
        implements IAttendanceGatherService {

    @Resource
    AttendanceGatherMapper attendanceGatherMapper;

    @Resource
    protected Validator validator;

    @Resource
    private IPersonnelService iPersonnelService;

    @Resource
    private IShiftCodeService shiftCodeService;

    @Resource
    private ISaveTimeService saveTimeService;

    @Resource
    private IAttendanceAbnormalService attendanceAbnormalService;

//    @Resource
//    OverTimeServiceImpl overTimeService;

    /**
     * 连表查询加班/请假出勤汇总记录
     *
     * @return 结果
     */
    @Override
    public List<AttendanceGatherDTO> selectJoinList(AttendanceGatherDTO attendanceGatherDTO) {
        return attendanceGatherMapper.selectJoinList(attendanceGatherDTO);
    }

    /**
     * 连表查询加班/请假出勤汇总记录
     *
     * @return 结果
     */
    @Override
    public List<AttendanceGatherDTO> selectCompJoinList(AttendanceGatherDTO attendanceGatherDTO) {
        return attendanceGatherMapper.selectCompJoinList(attendanceGatherDTO);
    }

    /**
     * 导出出勤汇总表数据
     */
    @Override
    public void exportExcel(HttpServletResponse response, AttendanceGatherDTO attendanceGatherDTO) {
        List<AttendanceGatherDTO> list = attendanceGatherMapper.selectCompJoinList(attendanceGatherDTO);
        //1.创建一个workbook,对应一个excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        //2.在workbook中添加一个sheet,对应Excel中的sheet
        HSSFSheet sheet = wb.createSheet("出勤汇总");

        //设置每一列的列宽
        for (int i = 0; i < 28; i++) {
            sheet.setColumnWidth(0, 256 * 15);
        }

        //3.设置样式以及字体样式
        HSSFCellStyle headerStyle = ExcelUtil.createHeadCellStyle(wb);
        HSSFCellStyle contentStyle = ExcelUtil.createContentCellStyle(wb);

        //4.创建标题，合并标题单元格
        //行号
        int rowNum = 0;

        //第一行
        HSSFRow row0 = sheet.createRow(rowNum++);
        row0.setHeight((short) 500);
        String[] row_first = {"工号", "姓名", "岗位", "迟到次数", "早退次数", "大夜班次数", "小夜班次数", "请假天数", "", "", "", "", "", "", "", "", "", "", "加班小时数", "", "", "", "月初未上岗", "月末未上岗", "存班小时数", "旷职天数", "应出勤天数", "实际出勤天数"};
        for (int i = 0; i < row_first.length; i++) {
            HSSFCell tempCell = row0.createCell(i);
            tempCell.setCellValue(row_first[i]);
            tempCell.setCellStyle(headerStyle);
        }
        //合并单元格
        //工号
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        //姓名
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        //岗位
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        //迟到次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
        //早退次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
        //大夜班次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
        //小夜班次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
        //请假天数
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 17));
        //加班天数
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 21));
        //月初未上岗
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 22, 22));
        //月末未上岗
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 23, 23));
        //存班小时数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 24, 24));
        //旷职天数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 25, 25));
        //应出勤天数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 26, 26));
        //实际出勤天数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 27, 27));

        //第二行
        HSSFRow row1 = sheet.createRow(rowNum++);
        row1.setHeight((short) 500);
        String[] row_second = {"", "", "", "", "", "", "", "病假", "工伤假", "事假", "婚假", "产假", "丧假", "探亲假", "公假", "年休假", "护理假", "合计", "延时加班", "休息日加班", "法定假加班", "合计", "", "", "", "", "", ""};
        for (int i = 0; i < row_second.length; i++) {
            HSSFCell tempCell = row1.createCell(i);
            tempCell.setCellValue(row_second[i]);
            tempCell.setCellStyle(headerStyle);
        }

        for (int i = 0; i < list.size(); i++) {
            HSSFRow tempRow = sheet.createRow(rowNum++);
            tempRow.setHeight((short) 500);
            //循环单元格填入数据
            for (int j = 0; j < 28; j++) {
                HSSFCell tempCell = tempRow.createCell(j);
                tempCell.setCellStyle(contentStyle);
                String cellValue = "";
                if (j == 0) {
                    //工号
                    cellValue = list.get(i).getEmpNo();
                } else if (j == 1) {
                    cellValue = list.get(i).getEmpName();
                } else if (j == 2) {
                    cellValue = list.get(i).getPostName();
                } else if (j == 3) {
                    cellValue = list.get(i).getLateNum().toString();
                } else if (j == 4) {
                    cellValue = list.get(i).getLeaNum().toString();
                } else if (j == 5) {
                    cellValue = list.get(i).getBigNig().toString();
                } else if (j == 6) {
                    cellValue = list.get(i).getSmaNig().toString();
                } else if (j == 7) {
                    cellValue = list.get(i).getSickLeave();
                } else if (j == 8) {
                    cellValue = list.get(i).getWorkInjuryLeave();
                } else if (j == 9) {
                    cellValue = list.get(i).getLeaveOfAbsence();
                } else if (j == 10) {
                    cellValue = list.get(i).getMarriageLeave();
                } else if (j == 11) {
                    cellValue = list.get(i).getMaternityLeave();
                } else if (j == 12) {
                    cellValue = list.get(i).getBereavementLeave();
                } else if (j == 13) {
                    cellValue = list.get(i).getHomeLeave();
                } else if (j == 14) {
                    cellValue = list.get(i).getCommonLeave();
                } else if (j == 15) {
                    cellValue = list.get(i).getAnnualLeave();
                } else if (j == 16) {
                    cellValue = list.get(i).getNursingLeave();
                } else if (j == 17) {
//                    cellValue = list.get(i).get
                } else if (j == 18) {
                    cellValue = list.get(i).getDelayedOvertime();
                } else if (j == 19) {
                    cellValue = list.get(i).getOvertimeOnRestDays();
                } else if (j == 20) {
                    cellValue = list.get(i).getOvertimeOnStatutoryHolidays();
                } else if (j == 21) {
                    //                    cellValue = list.get(i)
                } else if (j == 22) {
                    cellValue = String.valueOf(list.get(i).getBefEntDuty());
                } else if (j == 23) {
                    cellValue = String.valueOf(list.get(i).getAftEntDuty());
                } else if (j == 24) {
                    cellValue = list.get(i).getSavHou();
                } else if (j == 25) {
                    cellValue = String.valueOf(list.get(i).getTruDuty());
                } else if (j == 26) {
                    cellValue = String.valueOf(list.get(i).getDueAttDuty());
                } else if (j == 27) {
                    cellValue = String.valueOf(list.get(i).getActAttDuty());
                }
                tempCell.setCellValue(cellValue);
            }
        }

        //导出excel
        String fileName = "出勤汇总表.xls";
        try {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
            OutputStream stream = response.getOutputStream();
            if (null != wb && null != stream) {
                wb.write(stream);
                wb.close();
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出出勤汇总表数据
     */
    @Override
    public void importTemplate(HttpServletResponse response) {
        //1.创建一个workbook,对应一个excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        //2.在workbook中添加一个sheet,对应Excel中的sheet
        HSSFSheet sheet = wb.createSheet("出勤汇总");

        //设置每一列的列宽
        for (int i = 0; i < 28; i++) {
            sheet.setColumnWidth(0, 256 * 15);
        }

        //3.设置样式以及字体样式
        HSSFCellStyle headerStyle = ExcelUtil.createHeadCellStyle(wb);
        HSSFCellStyle contentStyle = ExcelUtil.createContentCellStyle(wb);

        //4.创建标题，合并标题单元格
        //行号
        int rowNum = 0;

        //第一行
        HSSFRow row0 = sheet.createRow(rowNum++);
        row0.setHeight((short) 500);
        String[] row_first = {"工号", "姓名", "岗位", "迟到次数", "早退次数", "大夜班次数", "小夜班次数", "请假天数", "", "", "", "", "", "", "", "", "", "", "加班小时数", "", "", "", "月初未上岗", "月末未上岗", "存班小时数", "旷职天数", "应出勤天数", "实际出勤天数"};
        for (int i = 0; i < row_first.length; i++) {
            HSSFCell tempCell = row0.createCell(i);
            tempCell.setCellValue(row_first[i]);
            tempCell.setCellStyle(headerStyle);
        }
        //合并单元格
        //工号
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        //姓名
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        //岗位
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        //迟到次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
        //早退次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
        //大夜班次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
        //小夜班次数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
        //请假天数
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 17));
        //加班天数
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 18, 21));
        //月初未上岗
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 22, 22));
        //月末未上岗
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 23, 23));
        //存班小时数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 24, 24));
        //旷职天数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 25, 25));
        //应出勤天数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 26, 26));
        //实际出勤天数
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 27, 27));

        //第二行
        HSSFRow row1 = sheet.createRow(rowNum++);
        row1.setHeight((short) 500);
        String[] row_second = {"", "", "", "", "", "", "", "病假", "工伤假", "事假", "婚假", "产假", "丧假", "探亲假", "公假", "年休假", "护理假", "合计", "延时加班", "休息日加班", "法定假加班", "合计", "", "", "", "", "", ""};
        for (int i = 0; i < row_second.length; i++) {
            HSSFCell tempCell = row1.createCell(i);
            tempCell.setCellValue(row_second[i]);
            tempCell.setCellStyle(headerStyle);
        }

        //导出excel
        String fileName = "出勤汇总表.xls";
        try {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
            OutputStream stream = response.getOutputStream();
            if (null != wb && null != stream) {
                wb.write(stream);
                wb.close();
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入出勤汇总数据
     *
     * @param attendanceGatherList 用户出勤汇总列表
     * @param isUpdateSupport      是否更新支持，如果已存在，则进行更新数据
     * @param operName             操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<AttendanceGatherDTO> attendanceGatherList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(attendanceGatherList) || attendanceGatherList.size() == 0) {
            throw new ServiceException("出勤汇总数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        AttendanceGather attendanceGather = new AttendanceGather();
        for (AttendanceGatherDTO attendanceGatherDTO : attendanceGatherList) {
            try {
                // 验证是否存在这笔数据
                AttendanceGather u = lambdaQuery().eq(AttendanceGather::getEmpNo, attendanceGatherDTO.getEmpNo()).one();
                if (StringUtils.isNull(u)) {
                    BeanUtils.copyProperties(attendanceGatherDTO, attendanceGather);
                    BeanValidators.validateWithException(validator, attendanceGatherDTO);
//                    overTimeService.lambdaUpdate()
//                            .eq(OverTime::getCompId, attendanceGatherDTO.getCompId())
//                            .eq(OverTime::getYear, attendanceGatherDTO.getYear())
//                            .eq(OverTime::getMonth, attendanceGatherDTO.getMonth())
//                            .eq(OverTime::getEmpNo, attendanceGatherDTO.getEmpNo()).remove();
                    lambdaUpdate()
                            .eq(AttendanceGather::getCompId, attendanceGatherDTO.getCompId())
                            .eq(AttendanceGather::getYear, attendanceGatherDTO.getYear())
                            .eq(AttendanceGather::getMonth, attendanceGatherDTO.getMonth())
                            .eq(AttendanceGather::getEmpNo, attendanceGatherDTO.getEmpNo()).remove();
                    attendanceGather.setCreator(operName);
                    save(attendanceGather);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + attendanceGatherDTO.getEmpNo() + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, attendanceGatherDTO);
                    attendanceGatherDTO.setCreator(operName);
                    lambdaUpdate().eq(AttendanceGather::getEmpNo, attendanceGatherDTO.getEmpNo()).update();
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、工号 " + attendanceGatherDTO.getEmpNo() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、工号 " + attendanceGatherDTO.getEmpNo() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工号 " + attendanceGatherDTO.getEmpNo() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 月初出勤汇总
     *
     * @return 出勤汇总结果
     * @author 266861
     * @Date 2023/7/12 13:34
     **/
    @Override
    public int attendanceSummary() {
        long[] quarter = {1,4,7,10};
        long lastMonth = 1;
        Date nowDate = new Date();
        long nowYear = Long.parseLong(String.format("%tY", nowDate));
        long nowMonth = Long.parseLong(String.format("%tm", nowDate));
        boolean twelveMonth = (nowMonth - 1) == 0;
        long year = twelveMonth ? nowYear - 1 : nowYear;
        long month = twelveMonth ? 12 : nowMonth - 1;
        AttendanceGather empParam = new AttendanceGather();
        empParam.setYear(year);
        empParam.setMonth(month);
        List<AttendanceGather> empList = attendanceGatherMapper.selectSummaryEmp(empParam);
        List<AttendanceGather> newDataList = new ArrayList<>();
        for (AttendanceGather emp : empList) {
            //执行月度结转
            AttendanceGather newMonthData = new AttendanceGather();
            BeanCopyUtils.copy(emp, newMonthData);
            newMonthData.setYear(year);
            newMonthData.setMonth(month);
            newMonthData = summaryData(newMonthData, year, month);
            newDataList.add(newMonthData);

            // 季度数据处理
            long quaMonth;
            if (month < 4) {
                quaMonth = 13;
            } else if (month < 7) {
                quaMonth = 14;
            } else if (month < 10) {
                quaMonth = 15;
            } else {
                quaMonth = 16;
            }
            long yearMonth = 17;
            if (Arrays.binarySearch(quarter, month) > 0) {
                //执行季度新增
                AttendanceGather newQuarterData = new AttendanceGather();
                BeanCopyUtils.copy(newMonthData, newQuarterData);
                newQuarterData.setYear(year);
                newQuarterData.setMonth(quaMonth);
                newQuarterData.setUuid(IdUtils.simpleUUID());
                newDataList.add(newQuarterData);
                if (month == lastMonth) {
                    //执行年度新增
                    AttendanceGather newYearData = new AttendanceGather();
                    BeanCopyUtils.copy(newMonthData, newYearData);
                    newYearData.setYear(year);
                    newYearData.setMonth(yearMonth);
                    newYearData.setUuid(IdUtils.simpleUUID());
                    newDataList.add(newYearData);
                } else {
                    //执行年度累加
                    AttendanceGather newYearData = new AttendanceGather();
                    BeanCopyUtils.copy(newMonthData, newQuarterData);
                    newQuarterData.setYear(year);
                    newQuarterData.setMonth(yearMonth);
                    attendanceGatherMapper.addAttendanceGather(newQuarterData);
                }
            } else {
                //执行季度累加
                AttendanceGather newQuarterData = new AttendanceGather();
                BeanCopyUtils.copy(newMonthData, newQuarterData);
                newQuarterData.setYear(year);
                newQuarterData.setMonth(quaMonth);
                attendanceGatherMapper.addAttendanceGather(newQuarterData);
                //执行年度累加
                AttendanceGather newYearData = new AttendanceGather();
                BeanCopyUtils.copy(newMonthData, newQuarterData);
                newQuarterData.setYear(year);
                newQuarterData.setMonth(yearMonth);
                attendanceGatherMapper.addAttendanceGather(newQuarterData);
            }
        }
        if (newDataList.size() == 0) {
            return 0;
        } else {
            System.out.println(newDataList);
            int result = 0;
            for (AttendanceGather gather : newDataList) {
                result += attendanceGatherMapper.insertAttendanceGather(gather);
            }
            return result;
        }
    }

    /**
     * 员工月度出勤汇总数据设定
     *
     * @param newData 数据
     * @param year    新数据年
     * @param month   新数据月
     * @return 员工出勤汇总结果
     * @author 266861
     * @Date 2023/7/12 9:35
     **/
    public AttendanceGather summaryData(AttendanceGather newData, long year, long month) {
        //数据来源：系统0，导入1
        String fromSystem = "0";
        newData.setDatafrom(fromSystem);
        newData = setData(newData);
        newData.setCreator("定时出勤汇总");
        newData.setCreateDate(new Date());
        newData.setUuid(IdUtils.simpleUUID());
        newData.setYear(year);
        newData.setMonth(month);
        return newData;
    }

    /**
     * 出勤汇总数据处理
     *
     * @param attendanceGather 出勤汇总数据
     * @return 出勤汇总数据
     */
    public AttendanceGather setData(AttendanceGather attendanceGather) {
        HumanresourcePersonnelInfoDTO personnelInfoDTO = iPersonnelService.selectPersonnelInfo(attendanceGather.getEmpNo());
        List<Personnel> personnelList = personnelInfoDTO.getPersonnelList();
        List<Leave> leaveList = personnelInfoDTO.getLeaveList();
        Date befWorkDay = null, aftWorkDay = null;
        // 人员基本信息数据
        if (personnelList.size() != 0) {
            Personnel personnel = personnelList.get(0);
            // 是否在职
            String isWork = "1";
            String afterWork = "0";
            if (!isWork.equals(personnel.getStatus())) {
                attendanceGather.setResvAttr2a(isWork);
            } else {
                attendanceGather.setResvAttr2a(afterWork);
            }
            attendanceGather.setCompId(personnel.getCompId());
            attendanceGather.setEmpId(personnel.getId());
            attendanceGather.setEmpName(personnel.getFullName());
            if (personnel.getPostId() != null) {
                attendanceGather.setPostId(personnel.getPostId().toString());
            }
            attendanceGather.setPostName(personnel.getPostName());
            //入企时间
            befWorkDay = personnel.getEntryDate();
        }
        if (leaveList.size() != 0) {
            //离职日期
            aftWorkDay = leaveList.get(0).getLeaveEffectDate();
        }
        // 应出勤数据
        Date date = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        // 设置为上一个月
        calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) - 1);
        date = calendar1.getTime();
        PersonShiftCodeDTO shiftCodeDTO = new PersonShiftCodeDTO();
        shiftCodeDTO.setStartDate(DateUtils.getFirstOfMonth(date));
        shiftCodeDTO.setEndDate(DateUtils.getLastOfMonth(date));
        shiftCodeDTO.setEmpId(attendanceGather.getEmpNo());
        shiftCodeDTO.setCompId(attendanceGather.getCompId());
        List<ShiftCode> shiftCodes = shiftCodeService.selectShiftCodeByPerson(shiftCodeDTO);
        //应出勤班次，小时数
        double dueAttDuty = 0, dueAttHou = 0;
        //大夜班小夜班数
        long bigNig = 0, smaNig = 0;
        // 缺勤班次数，缺勤天数，缺勤小时数
        BigDecimal shoAttDuty = new BigDecimal("0.0"), shoAttDay = new BigDecimal("0.0"), shoAttHou = new BigDecimal("0.0");

        // 月内入企前班次数，小时数，月内离职后班次数，小时数
        double befEntDuty = 0, befEntHou = 0, aftEntDuty = 0, aftEntHou = 0;
        String bigNigStr = "1", smaNigStr = "2";
        for (ShiftCode shiftCode : shiftCodes) {
            double hour = Double.parseDouble(shiftCode.getConHour());
            double min = Double.parseDouble(shiftCode.getConMin());
            if (hour != 0 || min != 0) {
                dueAttDuty++;
                dueAttHou += hour + (min / 60);
                if (StringUtils.isNotNull(befWorkDay) && shiftCode.getCreateDate().getTime() < befWorkDay.getTime()) {
                    befEntDuty++;
                    befEntHou += hour + (min / 60);
                }
                if (StringUtils.isNotNull(aftWorkDay) && shiftCode.getCreateDate().getTime() > aftWorkDay.getTime()) {
                    aftEntDuty++;
                    aftEntHou += hour + (min / 60);
                }
            }
            if (bigNigStr.equals(shiftCode.getBigSmaNight())) {
                bigNig++;
            } else if (smaNigStr.equals(shiftCode.getBigSmaNight())) {
                smaNig++;
            }
        }
        shoAttDuty = shoAttDuty.add(BigDecimal.valueOf(befEntDuty)).add(BigDecimal.valueOf(aftEntDuty));
        shoAttHou = shoAttHou.add(BigDecimal.valueOf(befEntHou)).add(BigDecimal.valueOf(aftEntHou));
        // 存班小时数，待补休小时数，实际补休小时数
        double savHou = 0, dueResHou = 0, resHou = 0;
        SaveTime saveTimeParam = new SaveTime();
        BeanCopyUtils.copy(attendanceGather, saveTimeParam);
        try {
            SaveTime saveTime = saveTimeService.selectSaveTimeList(saveTimeParam).get(0);
            savHou = Double.parseDouble(saveTime.getSaveHours());
            dueResHou = Double.parseDouble(saveTime.getOvertimeHours());
            resHou = Double.parseDouble(saveTime.getCompHours());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // 迟到次数，早退次数，旷职班次数，旷职天数，旷职小时数
        BigDecimal lateNum = new BigDecimal("0.0"), leaNum = new BigDecimal("0.0"), truDuty=new BigDecimal("0.0"), truDay = new BigDecimal("0.0"), truHou = new BigDecimal("0.0");
        AttendanceAbnormalDTO attendanceAbnormalDTO = new AttendanceAbnormalDTO();
        BeanCopyUtils.copy(attendanceGather,attendanceAbnormalDTO);
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YYYY_MM);
        String dateYearMonth = sdf.format(date);
        attendanceAbnormalDTO.setIncomingTime(dateYearMonth);
        List<AttendanceAbnormal> abnormalList = attendanceAbnormalService.queryAttendanceAbnormal(attendanceAbnormalDTO);
        System.out.println(abnormalList);
        for(AttendanceAbnormal abnormal : abnormalList){
            // 1-迟到，2-早退，3-旷工
            String type1 = "1", type2 = "2", type3 = "3";
            if(type1.equals(abnormal.getUndutyType())){
                lateNum = lateNum.add(BigDecimal.valueOf(1));
            }else if(type2.equals(abnormal.getUndutyType())){
                leaNum = leaNum.add(BigDecimal.valueOf(1));
            }else if(type3.equals(abnormal.getUndutyType())){
                truDuty = truDuty.add(BigDecimal.valueOf(1));
                truHou = truHou.add(abnormal.getTruHou());
            }
        }
        shoAttDuty = shoAttDuty.add(truDuty);
        shoAttHou = shoAttHou.add(truHou);
        attendanceGather.setDueAttDuty(BigDecimal.valueOf(dueAttDuty));
        attendanceGather.setDueAttHou(BigDecimal.valueOf(dueAttHou));
        attendanceGather.setShoAttDuty(shoAttDuty);
        attendanceGather.setShoAttHou(shoAttHou);
        attendanceGather.setActAttDuty(BigDecimal.valueOf(dueAttDuty).subtract(shoAttDuty));
        attendanceGather.setActAttHou(BigDecimal.valueOf(dueAttHou).subtract(shoAttHou));
        attendanceGather.setLateNum(lateNum);
        attendanceGather.setLeaNum(leaNum);
        attendanceGather.setTruDuty(truDuty);
        attendanceGather.setTruDay(truDay);
        attendanceGather.setTruHou(truHou);
        attendanceGather.setSavHou(BigDecimal.valueOf(savHou));
        attendanceGather.setDueResHou(BigDecimal.valueOf(dueResHou));
        attendanceGather.setResHou(BigDecimal.valueOf(resHou));
        attendanceGather.setBigNig(bigNig);
        attendanceGather.setSmaNig(smaNig);
        attendanceGather.setBefEntDuty(BigDecimal.valueOf(befEntDuty));
        attendanceGather.setBefEntHou(BigDecimal.valueOf(befEntHou));
        attendanceGather.setAftEntDuty(BigDecimal.valueOf(aftEntDuty));
        attendanceGather.setAftEntHou(BigDecimal.valueOf(aftEntHou));
        return attendanceGather;
    }
}




