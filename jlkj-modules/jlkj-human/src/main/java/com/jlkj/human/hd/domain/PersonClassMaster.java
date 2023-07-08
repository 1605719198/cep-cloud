package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 出勤身份对象 human_hd_person_class_master
 *
 * @author 266861
 * @date 2023-04-12
 */
@Data
public class PersonClassMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 工号 */
    @Excel(name = "工号")
    private String empId;

    /** 姓名 */
    private String nickName;

    /** 一级单位名称 */
    private String firstDeptName;

    /** 组织机构主档id */
    private String orgId;

    /** 轮班方式ID */
    @Excel(name = "轮班方式ID")
    private String shiftmodeId;

    /** 轮班方式名称 */
    private String shiftmodeName;

    /** 班别ID */
    @Excel(name = "班别ID")
    private String classId;

    /** 班别名称 */
    private String className;

    /** 排班开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 排班结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 状态 */
    private String status;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
}
