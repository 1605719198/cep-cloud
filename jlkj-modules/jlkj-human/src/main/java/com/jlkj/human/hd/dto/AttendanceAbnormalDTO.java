package com.jlkj.human.hd.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出勤异常表
 * @author HuangBing
 * @TableName human_hd_attendance_abnormal
 */
@TableName(value ="human_hd_attendance_abnormal")
@Data
public class AttendanceAbnormalDTO implements Serializable {
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
     * 轮班方式ID
     */
    private String turnTypeId;

    /**
     * 轮班方式名称
     */
    private String turnTypeName;

    /**
     * 班别ID
     */
    private String classId;

    /**
     * 班别名称
     */
    private String className;

    /**
     * 异常原因ID
     */
    private String excReaId;

    /**
     * 出勤证明原因ID
     */
    private String proveReaId;

    /**
     * 出勤证明原因
     */
    private String proveReason;

    /**
     * 审批状态
     */
    private String status;

    /**
     * 正常出勤时段开始
     */
    private Date norOndutyBegin;

    /**
     * 正常出勤时段结束
     */
    private Date norOndutyEnd;

    /**
     * 实际出勤时段开始
     */
    private Date slotCardOnduty;

    /**
     * 实际出勤时段结束
     */
    private Date slotCardOffduty;

    /**
     * 辅助说明
     */
    private String description;

    /**
     * 处理情况
     */
    private String disposeId;

    /**
     * 审核类型
     */
    private String auditType;

    /**
     * 审核类型
     */
    private String undutyType;

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