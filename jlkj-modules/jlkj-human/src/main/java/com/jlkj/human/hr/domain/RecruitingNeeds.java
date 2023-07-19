package com.jlkj.human.hr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 招聘需求对象 human_hr_recruiting_needs
 *
 * @author 266861
 * @date 2023-07-18
 */
@Data
public class RecruitingNeeds extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String compId;

    /** 需求单号 */
    @Excel(name = "需求单号")
    private String hiringId;

    /** 隶属机构 */
    @Excel(name = "隶属机构")
    private String hireDeptNo;

    /** 隶属机构名称 */
    @TableField(exist = false)
    private String hireDeptName;

    /** 需求职位Id */
    private Long upperposiId;

    /** 需求职位编码 */
    private String upperposiNo;

    /** 需求职位名称 */
    @Excel(name = "需求职位名称")
    private String upperposiName;

    /** 需求理由 */
    private String hireReason;

    /** 需求人数 */
    private String hireCount;

    /** 工作地点 */
    private String workplace;

    /** 倒班方式 */
    private String attendid;

    /** 招聘方式 */
    @Excel(name = "招聘方式")
    private String hiretype;

    /** 期望到岗日期 */
    private Date wishdate;

    /** 申请日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyDate;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyNo;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    /** 申请人 */
    private String updateNo;

    /** 最小年龄 */
    private Long minage;

    /** 最大年龄 */
    private Long maxage;

    /** 最低学历 */
    private String mingradute;

    /** 专业类别 */
    private String miliduty;

    /** 具体专业2 */
    private String miliduty2;

    /** 第一学历 */
    private String firstDegree;

    /** 工作经验 */
    private String workexperience;

    /** 需求单状态 */
    private String status;

    /** 年度绩效要求 */
    private String mertyear;

}
