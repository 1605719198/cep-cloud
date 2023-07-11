package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 固定考评启动档
 * @author HuangBing
 * @TableName human_pa_person_performance_fix
 */
@TableName(value ="human_pa_person_performance_fix")
@Data
public class PersonPerformanceFix implements Serializable {
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
     * 员工ID
     */
    private String empId;

    /**
     * 岗位类型（下拉选单）
     */
    private String deptType;

    /**
     * 一级单位
     */
    private String dept;

    /**
     * 岗位
     */
    private String postId;

    /**
     * 考评类别（1-月度，2季度，3-年度）
     */
    private String meritType;

    /**
     * 考评年月
     */
    private String meritMonth;

    /**
     * 考评状态（1-已启动，2-已完成）
     */
    private String meritStatus;

    /**
     * 输入人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}