package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 月出勤统计汇总对象 human_hd_attendance_gather
 *
 * @author 266861
 * @date 2023-07-13
 */
@Data
public class AttendanceGather implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String uuid;

    /** 考勤年 */
    @Excel(name = "考勤年")
    private Long year;

    /** 考勤月 */
    @Excel(name = "考勤月")
    private Long month;

    /** 应出勤班次数 */
    @Excel(name = "应出勤班次数")
    private BigDecimal dueAttDuty;

    /** 应出勤天数 */
    @Excel(name = "应出勤天数")
    private BigDecimal dueAttDay;

    /** 应出勤小时数 */
    @Excel(name = "应出勤小时数")
    private BigDecimal dueAttHou;

    /** 缺勤班次数 */
    @Excel(name = "缺勤班次数")
    private BigDecimal shoAttDuty;

    /** 缺勤天数 */
    @Excel(name = "缺勤天数")
    private BigDecimal shoAttDay;

    /** 缺勤小时数 */
    @Excel(name = "缺勤小时数")
    private BigDecimal shoAttHou;

    /** 实际出勤班次数 */
    @Excel(name = "实际出勤班次数")
    private BigDecimal actAttDuty;

    /** 实际出勤天数 */
    @Excel(name = "实际出勤天数")
    private BigDecimal actAttDay;

    /** 实际出勤小时数 */
    @Excel(name = "实际出勤小时数")
    private BigDecimal actAttHou;

    /** 调整后应出勤班次数 */
    @Excel(name = "调整后应出勤班次数")
    private BigDecimal updDueAttDuty;

    /** 调整后应出勤天数 */
    @Excel(name = "调整后应出勤天数")
    private BigDecimal updDueAttDay;

    /** 调整后应出勤小时数 */
    @Excel(name = "调整后应出勤小时数")
    private BigDecimal updDueAttHou;

    /** 调整后缺勤班次数 */
    @Excel(name = "调整后缺勤班次数")
    private BigDecimal updShoAttDuty;

    /** 调整后缺勤天数 */
    @Excel(name = "调整后缺勤天数")
    private BigDecimal updShoAttDay;

    /** 调整后缺勤小时数 */
    @Excel(name = "调整后缺勤小时数")
    private BigDecimal updShoAttHou;

    /** 调整后实出勤天数 */
    @Excel(name = "调整后实出勤天数")
    private BigDecimal updActAttDuty;

    /** 调整后实出勤天数 */
    @Excel(name = "调整后实出勤天数")
    private BigDecimal updActAttDay;

    /** 调整后实出勤小时数 */
    @Excel(name = "调整后实出勤小时数")
    private BigDecimal updActAttHou;

    /** 迟到次数 */
    @Excel(name = "迟到次数")
    private BigDecimal lateNum;

    /** 早退次数 */
    @Excel(name = "早退次数")
    private BigDecimal leaNum;

    /** 旷职班次数 */
    @Excel(name = "旷职班次数")
    private BigDecimal truDuty;

    /** 旷职天数 */
    @Excel(name = "旷职天数")
    private BigDecimal truDay;

    /** 旷职小时数 */
    @Excel(name = "旷职小时数")
    private BigDecimal truHou;

    /** 月内入企前班次数 */
    @Excel(name = "月内入企前班次数")
    private BigDecimal befEntDuty;

    /** 月内入企前天数 */
    @Excel(name = "月内入企前天数")
    private BigDecimal befEntDay;

    /** 月内入企前小时数 */
    @Excel(name = "月内入企前小时数")
    private BigDecimal befEntHou;

    /** 月内离职后班次数 */
    @Excel(name = "月内离职后班次数")
    private BigDecimal aftEntDuty;

    /** 月内离职后天数 */
    @Excel(name = "月内离职后天数")
    private BigDecimal aftEntDay;

    /** 月内离职后小时数 */
    @Excel(name = "月内离职后小时数")
    private BigDecimal aftEntHou;

    /** 大夜班次数 */
    @Excel(name = "大夜班次数")
    private Long bigNig;

    /** 小夜班次数 */
    @Excel(name = "小夜班次数")
    private Long smaNig;

    /** 存班小时数 */
    @Excel(name = "存班小时数")
    private BigDecimal savHou;

    /** 待补休小时数(含实际补休小时数) */
    @Excel(name = "待补休小时数(含实际补休小时数)")
    private BigDecimal dueResHou;

    /** 实际补休小时数 */
    @Excel(name = "实际补休小时数")
    private BigDecimal resHou;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 岗位ID */
    @Excel(name = "岗位ID")
    private String postId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 公司ID */
    @Excel(name = "公司ID")
    private String compId;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String resvAttr1a;

    /** 是否离职 */
    @Excel(name = "是否离职")
    private String resvAttr2a;

    /** 数据来源 */
    @Excel(name = "数据来源")
    private String datafrom;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String resvAttr3a;

    /** 备用字段4 */
    @Excel(name = "备用字段4")
    private String resvAttr4a;

    /** 备用字段5 */
    @Excel(name = "备用字段5")
    private String resvAttr5a;

    /** 备用字段6 */
    @Excel(name = "备用字段6")
    private String resvAttr6a;

    /** 备用字段7 */
    @Excel(name = "备用字段7")
    private String resvAttr7a;

    /** 备用字段8 */
    @Excel(name = "备用字段8")
    private String resvAttr8a;

    /** 备用字段9 */
    @Excel(name = "备用字段9")
    private String resvAttr9a;

    /** 备用字段10 */
    @Excel(name = "备用字段10")
    private String resvAttr10a;

    /** 加班小时数累计 */
    @Excel(name = "加班小时数累计")
    private BigDecimal overTimeHou;

    /** 补休小时数累计 */
    @Excel(name = "补休小时数累计")
    private BigDecimal restHou;

    /** 请假班次数 */
    @Excel(name = "请假班次数")
    private BigDecimal holDuty;

    /** 请假天数 */
    @Excel(name = "请假天数")
    private BigDecimal holDay;

    /** 请假小时数 */
    @Excel(name = "请假小时数")
    private BigDecimal holHou;

    /** 大小夜班次数 */
    @Excel(name = "大小夜班次数")
    private Long bigSmaNig;

}
