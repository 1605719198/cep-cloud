package com.jlkj.human.hd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 倒班人员法定假日加班资料表
 * @author HuangBing
 * @TableName human_hd_ holiday_overtime
 */
@TableName(value ="human_hd_holiday_overtime")
@Data
public class HolidayOvertime implements Serializable {
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
     * 员工Id
     */
    private String empId;

    /**
     * 工号
     */
    private String empNo;

    /**
     * 姓名
     */
    private String empName;

    /**
     * 岗位全称
     */
    private String postFullName;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 法定假日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date legalHolDate;

    /**
     * 轮班方式ID
     */
    private String turnTypeId;

    /**
     * 轮班方式中文
     */
    private String turnTypeName;

    /**
     * 班别ID
     */
    private String classId;

    /**
     * 班别中文
     */
    private String className;

    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;

    /**
     * 班次开始时间
     */
    private Date extraWorkBegin;

    /**
     * 班次结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date extraWorkEnd;

    /**
     * 加班时数
     */
    private BigDecimal extraWorkHours;

    /**
     * 刷卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date slotCardTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}