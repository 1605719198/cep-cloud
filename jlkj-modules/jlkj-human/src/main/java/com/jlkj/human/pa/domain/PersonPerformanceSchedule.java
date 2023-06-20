package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人绩效进度表主档
 * @TableName human_pa_person_performance_schedule
 */
@TableName(value ="human_pa_person_performance_schedule")
@Data
public class PersonPerformanceSchedule implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 主档ID
     */
    private String personPerformanceId;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 计划进度项（1-计划输入，2-计划审批，3-自评，4-主管考评，5-最终主管复核）
     */
    private String project;

    /**
     * 序号
     */
    private String num;

    /**
     * 制定者工号
     */
    private String apprEmp;

    /**
     * 制定者名称
     */
    private String apprName;

    /**
     * 制定时间
     */
    private Date apprTime;

    /**
     * 状态（0-未完成，1-已完成，2-计划审批中，3-自评中，4-主管考评中，5-最终复核中）
     */
    private String apprStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}