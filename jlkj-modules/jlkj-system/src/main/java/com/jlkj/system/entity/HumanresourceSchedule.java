package com.jlkj.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-07 09:44:19.097537
 */
@TableName(value ="human_hm_schedule")
@Data
public class HumanresourceSchedule implements Serializable{

    /** 班别代码(ABCD) */
	private String shiftNo;

    /** 班别名称(甲乙丙丁) */
	private String shiftName;

    /** 班次代码(00、 2201 、 2202) */
	private String classNo;

    /** 班次名称(休息、白班、夜班) */
	private String className;

    /** 排班日期 */
	private String scheduleDate;

    /** 本班开始时间 */
	private Date startTime;

    /** 本班结束时间 */
	private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

