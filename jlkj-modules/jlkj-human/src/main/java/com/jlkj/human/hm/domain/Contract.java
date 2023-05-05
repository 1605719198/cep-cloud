package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员劳动合同表
 * @TableName human_hm_contract
 * @author HuangBing
 */
@TableName(value ="human_hm_contract")
@Data
public class Contract implements Serializable {
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
     * 工号
     */
    private String empNo;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 劳动关系状态
     */
    private String relStatus;

    /**
     * 姓名
     */
    private String empName;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 签订合同日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signDate;

    /**
     * 合同起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    /**
     * 合同期限
     */
    private String conPeriId;

    /**
     * 合同到期日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 长期工作地
     */
    private String workAddrId;

    /**
     * 用工性质
     */
    private String empFeaId;

    /**
     * 职工身份
     */
    private String empIdeId;

    /**
     * 劳动关系类型
     */
    private String relTypeId;

    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveEffectDate;

    /**
     * 试用期开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date probStartDate;

    /**
     * 试用期限
     */
    private String probPeriId;

    /**
     * 试用期结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date probEndDate;

    /**
     * 是否试用期
     */
    private String ifProb;

    /**
     * 变更原因
     */
    private String chaReaId;

    /**
     * 变更原因补充说明
     */
    private String chaReaDesc;

    /**
     * 协议类型
     */
    private String protType1Id;

    /**
     * 协议补充说明
     */
    private String protDesc1;

    /**
     * 协议类型
     */
    private String protType2Id;

    /**
     * 协议补充说明
     */
    private String protDesc2;

    /**
     * 协议类型
     */
    private String protType3Id;

    /**
     * 协议补充说明
     */
    private String protDesc3;

    /**
     * 解除合同的有关说明
     */
    private String releseDesc;

    /**
     * 版本号
     */
    private Integer versionNo;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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