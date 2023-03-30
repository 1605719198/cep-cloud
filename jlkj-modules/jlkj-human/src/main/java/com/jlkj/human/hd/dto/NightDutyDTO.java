package com.jlkj.human.hd.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 夜班查询表
 * @TableName human_hd_night_duty
 */
@TableName(value ="human_hd_night_duty")
@Data
public class NightDutyDTO implements Serializable {
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
    private String postFullname;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 班次ID
     */
    private String shiftId;

    /**
     * 排班日期
     */
    private Date shiftDate;

    /**
     * 大夜数
     */
    private BigDecimal bigNight;

    /**
     * 小夜数
     */
    private BigDecimal smallNight;

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
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}