package com.jlkj.safety.si.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 煤质检记录
 * </p>
 *
 * @author zyf
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_CoalInspection")
public class Coalinspection implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 样品编码
     */
    @TableField("SpecimenNumber")
    private String specimenNumber;

    /**
     * 发布日期(审批通过日期)
     */
    @TableField("TestDate")
    private LocalDate testDate;

    /**
     * 班次
     */
    @TableField("Shifts")
    private Integer shifts;

    /**
     * 班别
     */
    private String classes;

    /**
     * 工号（不要了）
     */
    @TableField("JobNumber")
    private String jobNumber;

    /**
     * 化验员（录入人）
     */
    @TableField("LaboratoryTechnician")
    private String laboratoryTechnician;

    /**
     * 检验时间
     */
    @TableField("TestTime")
    private LocalDateTime testTime;

    /**
     * 全水Mt%
     */
    @TableField("QuanShui")
    private BigDecimal quanShui;

    /**
     * 全硫St.d%
     */
    @TableField("QuanLiu")
    private BigDecimal quanLiu;

    /**
     * 灰分Ad%
     */
    @TableField("HuiFen")
    private BigDecimal huiFen;

    /**
     * 挥发分Vdaf%
     */
    @TableField("HuiFaFen")
    private BigDecimal huiFaFen;

    /**
     * 固定碳FCad%
     */
    @TableField("GuDingTan")
    private BigDecimal guDingTan;

    /**
     * 粘结指数G
     */
    @TableField("NianJieZhiShu")
    private BigDecimal nianJieZhiShu;

    /**
     * 胶质层Ymm
     */
    @TableField("JiaoZhiCen")
    private BigDecimal jiaoZhiCen;

    /**
     * 粒度
     */
    @TableField("LiDu")
    private BigDecimal liDu;

    /**
     * 送样时间
     */
    @TableField("SendTime")
    private LocalDateTime sendTime;

    /**
     * 二次检验（是/否）
     */
    @TableField("IsSecondaryTest")
    private Integer isSecondaryTest;

    @TableField("MaterialID")
    private Long materialId;

    /**
     * 供应商
     */
    @TableField("SupplierID")
    private Long supplierId;

    /**
     * 物资品名ID
     */
    @TableField("ProductID")
    private String productId;

    /**
     * 物资品名
     */
    @TableField("ProductName")
    private String productName;

    /**
     * 供应商名称
     */
    @TableField("SupplierName")
    private String supplierName;

    /**
     * 质检流程编号
     */
    private Long processNumber;

    /**
     * 审核人ID
     */
    @TableField("AuditUserID")
    private Long auditUserId;

    /**
     * 审核人
     */
    @TableField("AuditUserName")
    private String auditUserName;

    /**
     * 制表人ID
     */
    @TableField("CreateUserID")
    private Long createUserId;

    /**
     * 制表人
     */
    @TableField("CreateUserName")
    private String createUserName;

    /**
     * 状态（0、未审核。1、审核通过。2、审核驳回。）
     */
    @TableField("Status")
    private Integer status;

    /**
     * 驳回原因
     */
    @TableField("DenialReason")
    private String denialReason;

    /**
     * 批准人ID
     */
    @TableField("ApproveUserID")
    private Long approveUserId;

    /**
     * 批准人
     */
    @TableField("ApproveUserName")
    private String approveUserName;

    /**
     * 审核时间
     */
    @TableField("AuditTime")
    private LocalDateTime auditTime;

    /**
     * 质检时间
     */
    @TableField("CreateTime")
    private LocalDateTime createTime;

    @TableField("ID")
    private Long id;

    @TableField("TeamID")
    private Long teamId;


}
