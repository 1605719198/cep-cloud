package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工销假对象 human_hd_person_holiday_cancel
 *
 * @author 266861
 * @date 2023-04-21
 */
@Data
public class PersonHolidayCancel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 请假记录id */
    private String personHolidayId;

    /** 公司别 */
    private String compId;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 工号ID */
    private String empId;

    /** 姓名 */
    private String empName;

    /** 岗位名称 */
    private String postName;

    /** 岗位ID */
    private String postId;

    /** 一级组织机构ID */
    private String orgParentId;

    /** 二级组织机构id */
    private String orgId;

    /** 实际请假开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际请假开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 实际请假结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际请假结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 实际请假天数 */
    @Excel(name = "实际请假天数")
    private BigDecimal leaveDays;

    /** 原始请假开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date oriStartDate;

    /** 原始请假结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date oriEndDate;

    /** 原始请假天数 */
    private BigDecimal oriLeaveDays;

    /** 请假类别 */
    @Excel(name = "请假类别")
    private String leaTypeId;

    /** 本次请假天数 */
    private BigDecimal leaveShifts;

    /** 本次请假时数 */
    private BigDecimal leaveHours;

    /** 本次请假时数（含节假日） */
    private BigDecimal morLeaveHours;

    /** 本次请假天数（含节假日） */
    private BigDecimal morLeaveShifts;

    /** 销假状态 */
    private String status;

    /** 请假状态 */
    private String leaveStatus;

    /** 是否全销 */
    private String isAll;

    /** 辅助说明 */
    private String description;

    /** 是否包括节假日 */
    private String isContainHoliday;

    /** 剩余可请假天数 */
    private BigDecimal remainingDays;

    /** 本月累计天数 */
    private BigDecimal monthDays;

    /** 本月累计小时数 */
    private BigDecimal monthHours;

    /** 本年累计天数 */
    private BigDecimal yearDays;

    /** 本年累计小时数 */
    private BigDecimal yearHours;

    /** 审核人 */
    private String appName;

    /** 审核日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appDate;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人日期 */

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;


}
