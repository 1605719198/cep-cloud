package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  generator
 * 生产异常台账
 * @TableName product_oi_abnormal_ledger
 */
@TableName(value ="product_oi_abnormal_ledger")
@Data
public class ProductionAbnormalLedger implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 主体单位id
     */
    private String departmentId;

    /**
     * 主体单位
     */
    private String departmentName;

    /**
     * 发生时间
     */
    private Date happenTime;

    /**
     * 事故名称
     */
    private String accidentName;

    /**
     * 防范措施
     */
    private String preventiveMeasures;

    /**
     * 计划完成时间
     */
    private Date measuresCompletionTime;

    /**
     * 措施落实情况
     */
    private String measuresImplementation;

    /**
     * 专业责任人
     */
    private String professionalResponsiblePerson;

    /**
     * 跟踪责任人
     */
    private String trackResponsiblePerson;

    /**
     * 事故报告
     */
    private String accidentReportFile;

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
     * 修改人id
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
