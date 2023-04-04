package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  generator
 * 计划管理--维修计划
 * @TableName product_oi_plan_repair
 */
@TableName(value ="product_oi_plan_repair")
@Data
public class ProductionPlanRepair implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 计划编号
     */
    private String planCode;

    /**
     * 部门编号
     */
    private String departmentId;

    /**
     * 维修类型编号
     */
    private Integer repairTypeId;

    /**
     * 主体责任人编号
     */
    private String personMainLiableId;

    /**
     * 主体责任人
     */
    private String personMainLiableName;

    /**
     * 专业责任人编号
     */
    private String personProfessionLiableId;

    /**
     * 专业责任人
     */
    private String personProfessionLiableName;

    /**
     * 设备编号
     */
    private String equipmentNumber;

    /**
     * 设备名称
     */
    private String equipmentName;

    /**
     * 计划开始
     */
    private Date planStartDate;

    /**
     * 计划结束
     */
    private Date planEndDate;

    /**
     * 实际开始时间
     */
    private Date realStartDate;

    /**
     * 实际完成时间
     */
    private Date realEndDate;

    /**
     * 计划完成状态
     */
    private Integer planState;

    /**
     * 维修内容
     */
    private String repairContent;

    /**
     * 是否影响产量
     */
    private Integer isAffectOutput;

    /**
     * 完成操作人id
     */
    private String completeUserId;

    /**
     * 完成操作人姓名
     */
    private String completeUserName;

    /**
     * 计划完成说明
     */
    private String remark;

    /**
     * 预提醒天数
     */
    private Integer remindDays;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人编号
     */
    private String modifyUserId;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
