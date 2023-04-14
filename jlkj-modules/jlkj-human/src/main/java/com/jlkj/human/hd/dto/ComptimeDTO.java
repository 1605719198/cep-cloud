package com.jlkj.human.hd.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 补休记录表
 * @author HuangBing
 * @TableName human_hd_comptime
 */
@TableName(value ="human_hd_comptime")
@Data
public class ComptimeDTO implements Serializable {
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
     * 员工id
     */
    private String empId;

    /**
     * 姓名
     */
    private String empName;

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
     * 补休开始日期
     */
    private List startDate;

    /**
     * 补休结束日期
     */
    private List endDate;

    /**
     * 存班小时数
     */
    private String saveHours;

    /**
     * 补休小时数
     */
    private String compHours;

    /**
     * 辅助说明
     */
    private String description;

    /**
     * 审核状态
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 结束时间
     */
    private List<String> workOvertimeDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}