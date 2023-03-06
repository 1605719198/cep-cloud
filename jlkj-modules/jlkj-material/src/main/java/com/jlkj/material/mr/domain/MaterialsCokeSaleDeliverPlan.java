package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类-焦炭外售发货计划
 * @author sudeyou
 * @since 2022-08-19 14:00:15
 */
@TableName(value ="materials_mr_coke_sale_deliver_plan")
@Data
public class MaterialsCokeSaleDeliverPlan implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 发货单号
     */
    private String planListNo;

    /**
     * 运输方式
     */
    private String carrierType;

    /**
     * 车号
     */
    private String carrierNo;

    /**
     * 料号
     */
    private String mtrlNo;

    /**
     * 储位代码
     */
    private String fromCode;

    /**
     * 客户编号
     */
    private String toCode;

    /**
     * 客户名称
     */
    private String toDesc;

    /**
     * 合同号
     */
    private String pchrNo;

    /**
     * 是否过磅
     */
    private String isLoad;

    /**
     * 过磅点
     */
    private String loadStation;

    /**
     * 开单人单位代码（二级）
     */
    private String issueDept;

    /**
     * 开单人单位名称
     */
    private String issueDeptName;

    /**
     * 开单人工号
     */
    private String issueEmpNo;

    /**
     * 开单人姓名
     */
    private String issueEmpName;

    /**
     * 申请日期
     */
    private String issueDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

