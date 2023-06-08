package com.jlkj.human.hd.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 加班汇总表（薪酬计算用）
 * @TableName human_hd_over_time
 */
@TableName(value ="human_hd_over_time")
@Data
public class OverTime implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 年
     */
    private Integer year;

    /**
     * 月
     */
    private Integer month;

    /**
     * 假别ID/加班类别ID（假别编码）
     */
    private String holOveType;

    /**
     * 请假类的为1，加班类的为0
     */
    private String isHolType;

    /**
     * 已批班次数(导入表格的（请假天数）)
     */
    private BigDecimal dutyNum;

    /**
     * 已批小时数（请假天数*8转成小时数）
     */
    private BigDecimal hourNum;

    /**
     * 含节假日已批班次数（导入时等duty_num）
     */
    private BigDecimal morDutyNum;

    /**
     * 含节假日已批小时数（导入时等hour_num）
     */
    private BigDecimal morHourNum;

    /**
     * 员工ID（human_hd_personnel 人员数据）
     */
    private String empId;

    /**
     * 员工编码（human_hd_personnel 人员数据）
     */
    private String empNo;

    /**
     * 岗位ID（human_hd_personnel 人员数据）
     */
    private String postId;

    /**
     * 岗位名称（human_hd_personnel 人员数据）
     */
    private String postName;

    /**
     * 员工姓名（human_hd_personnel 人员数据）
     */
    private String empName;

    /**
     * 公司别
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
     * 
     */
    private Date createDate;

    /**
     * 
     */
    private String resvAttr1a;

    /**
     * 数据来源（1-表示导入，0-系统自动）
     */
    private String dataFrom;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}