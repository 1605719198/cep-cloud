package com.jlkj.human.hd.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 月出勤统计汇总表
 * @TableName human_hd_attendance_gather
 */
@TableName(value ="human_hd_attendance_gather")
@Data
public class AttendanceGatherDTO implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String uuid;

    /**
     * 考勤年
     */
    private String year;

    /**
     * 考勤月
     */
    private String month;

    /**
     * 应出勤天数
     */
    private BigDecimal dueAttDuty;

    /**
     * 应出勤小时数
     */
    private BigDecimal dueAttHou;

    /**
     * 缺勤天数
     */
    private BigDecimal shoAttDuty;

    /**
     * 缺勤小时数
     */
    private BigDecimal shoAttHou;

    /**
     * 实际出勤天数
     */
    private BigDecimal actAttDuty;

    /**
     * 实际出勤小时数
     */
    private BigDecimal actAttHou;

    /**
     * 迟到次数
     */
    private Integer lateNum;

    /**
     * 早退次数
     */
    private Integer leaNum;

    /**
     * 请假天数汇总
     */
    private BigDecimal holDuty;

    /**
     * 请假小时数汇总
     */
    private BigDecimal holHou;

    /**
     * 旷职天数
     */
    private BigDecimal truDuty;

    /**
     * 旷职小时数
     */
    private BigDecimal truHou;

    /**
     * 月初未上岗天数
     */
    private BigDecimal befEntDuty;

    /**
     * 月初未上岗小时数
     */
    private BigDecimal befEntHou;

    /**
     * 大夜班次数
     */
    private Integer bigNig;

    /**
     * 小夜班次数
     */
    private Integer smaNig;

    /**
     * 月末未上岗天数
     */
    private BigDecimal aftEntDuty;

    /**
     * 月末未上岗小时数
     */
    private BigDecimal aftEntHou;

    /**
     * 存班小时数
     */
    private String savHou;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 公司ID
     */
    private String compId;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    private Date createDate;

    /**
     * 加班小时数累计
     */
    private BigDecimal overTimeHou;

    /**
     * 补休小时数累计
     */
    private BigDecimal restHou;

    /**
     * 大小夜班次数
     */
    private Integer bigSmaNig;

    /**
     * 补休小时数
     */
    private BigDecimal resHou;

    /**
     * 工号
     */
    private String empNo;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 姓名
     */
    private String empName;

    /**
     * 调整后应出勤天数
     */
    private BigDecimal updDueAttDuty;

    /**
     * 调整后应出勤时数
     */
    private BigDecimal updDueAttHou;

    /**
     * 调整后缺勤天数
     */
    private BigDecimal updShoAttDuty;

    /**
     * 调整后缺勤小时数
     */
    private BigDecimal updShoAttHou;

    /**
     * 调整后实出勤天数
     */
    private BigDecimal updActAttDuty;

    /**
     * 调整后实出勤小时数
     */
    private BigDecimal updActAttHou;

    /**
     * 日期类别
     */
    private String type;

    /**
     * 是否包含子公司
     */
    private String includingSubsidiaries;

    /**
     * 日期
     */
    private String date;

    /**
     * 假别ID/加班类别ID（假别编码）
     */
    private String holOveType;

    /**
     * 请假类的为1，加班类的为0
     */
    private String isHolType;

    /**
     * 事假
     */
    private String leaveOfAbsence;

    /**
     * 病假
     */
    private String sickLeave;

    /**
     * 工伤假
     */
    private String workInjuryLeave;

    /**
     * 婚假
     */
    private String marriageLeave;

    /**
     * 产假
     */
    private String maternityLeave;

    /**
     * 丧假
     */
    private String bereavementLeave;

    /**
     * 探亲假
     */
    private String homeLeave;

    /**
     * 公假
     */
    private String commonLeave;

    /**
     * 年休假
     */
    private String annualLeave;

    /**
     * 护理假
     */
    private String nursingLeave;

    /**
     * 延时加班
     */
    private String delayedOvertime;

    /**
     * 休息日加班
     */
    private String overtimeOnRestDays;

    /**
     * 法定假日加班
     */
    private String overtimeOnStatutoryHolidays;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}