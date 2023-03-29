package com.jlkj.human.hd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工出勤有效记录资料表
 * @TableName human_hd_attendence_record
 * @author HuangBing
 */
@TableName(value ="human_hd_attendence_record")
@Data
public class AttendenceRecord implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String uuid;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 员工ID
     */
    private String empId;

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
     * 班次日期（年月日）
     */
    private Date shiftDate;

    /**
     * 刷卡日期（年月日）
     */
    private Date slotCardDate;

    /**
     * 刷卡时间（年月日 HH:MM）
     */
    private Date slotCardTime;

    /**
     * 星期
     */
    private String week;

    /**
     * 上/下班（1-上班，2-下班）
     */
    private String onOffDuty;

    /**
     * 刷卡钟ID
     */
    private String clockId;

    /**
     * 刷卡钟编码
     */
    private String clockCode;

    /**
     * 刷卡钟名称
     */
    private String clockName;

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
     * 预留属性1
     */
    private String resvAttr1;

    /**
     * 预留属性2
     */
    private String resvAttr2;

    /**
     * 预留属性3
     */
    private String resvAttr3;

    /**
     * 预留属性4
     */
    private String resvAttr4;

    /**
     * 预留属性5
     */
    private String resvAttr5;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}