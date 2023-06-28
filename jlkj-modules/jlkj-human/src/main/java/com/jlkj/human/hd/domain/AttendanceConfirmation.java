package com.jlkj.human.hd.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出勤确认表
 * @TableName human_hd_attendance_confirmation
 * @author 111191
 */
@TableName(value ="human_hd_attendance_confirmation")
@Data
public class AttendanceConfirmation implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 确认状态（确认时，状态给1，取消确认时，状态给0）
     */
    private String isCon;

    /**
     * 排班状态（1-取消排班，0-恢复排班）
     */
    private String shiftStatus;

    /**
     * 薪资确认状态（1-确认，0-未确认）
     */
    private String status;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 组织机构ID
     */
    private String orgId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}