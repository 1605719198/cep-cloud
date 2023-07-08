package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工请假记录对象 human_hd_person_holiday
 *
 * @author jiangbingchen
 * @date 2023-04-14
 */
@Data
public class PersonHoliday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

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
    private String postname;

    /** 岗位ID */
    private String postid;

    /** 一级组织机构ID */
    private String orgParentId;

    /** 二级组织机构ID */
    private String orgId;

    /** 请假类别 */
    @Excel(name = "请假类别")
    private String leaTypeId;

    /** 是否包括节假日 */
    private String isContainHoliday;

    /** 辅助说明 */
    private String description;

    /** 剩余可请假天数 */
    @Excel(name = "剩余可请假天数")
    private BigDecimal remainingDays;

    /** 本月累计天数 */
    private BigDecimal monthDays;

    /** 本月累计小时数 */
    private BigDecimal monthHours;

    /** 本年累计天数 */
    private BigDecimal yearDays;

    /** 本年累计小时数 */
    private BigDecimal yearHours;

    /** 请假批示状态 */
    @Excel(name = "请假批示状态")
    private String status;

    /** 销假批示状态 */
    private String statusCancel;

    /** 请假小时数 */
    private BigDecimal leaveHours;

    /** 请假天数 */
    @Excel(name = "请假天数")
    private BigDecimal leaveShifts;

    /** 请假开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 请假结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;
}
