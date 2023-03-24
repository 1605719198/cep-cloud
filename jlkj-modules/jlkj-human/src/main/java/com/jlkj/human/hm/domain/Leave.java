package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员离职信息表
 * @TableName human_hm_leave
 */
@TableName(value ="human_hm_leave")
@Data
public class Leave implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String uuid;

    /**
     * 公司
     */
    private String compId;

    /**
     * 员工ID（人员基础信息表uuid）
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
     * 离职前主岗位
     */
    private String leaveMainPost;

    /**
     * 入企日期
     */
    private Date enterDate;

    /**
     * 离职生效日期
     */
    private Date leaveEffectDate;

    /**
     * 止薪日期
     */
    private Date endPayDate;

    /**
     * 离职类别
     */
    private String leaveTypeId;

    /**
     * 离职原因
     */
    private String leaveReason;

    /**
     * 离职原因细分
     */
    private String leaveReasonDetail;

    /**
     * 更新人员
     */
    private String creator;

    /**
     * 更新人员ID
     */
    private String creatorId;

    /**
     * 更新时间
     */
    private Date createDate;

    /**
     * 备用字段1
     */
    private String alternateField1;

    /**
     * 备用字段2
     */
    private String alternateField2;

    /**
     * 备用字段3
     */
    private String alternateField3;

    /**
     * 备用字段4
     */
    private String alternateField4;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}