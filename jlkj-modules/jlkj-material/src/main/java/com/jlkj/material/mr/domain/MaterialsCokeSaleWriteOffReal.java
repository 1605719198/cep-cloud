package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类-焦炭外售销账实际
 * @author sudeyou
 * @since 2022-08-19 13:58:42
 */
@TableName(value ="materials_mr_coke_sale_write_off_real")
@Data
public class MaterialsCokeSaleWriteOffReal implements Serializable {
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
     * 申请单位代码
     */
    private String issueDept;

    /**
     * 申请单位名称
     */
    private String issueDeptName;

    /**
     * 申请人工号
     */
    private String issueEmpNo;

    /**
     * 申请人姓名
     */
    private String issueEmpName;

    /**
     * 申请日期
     */
    private String issueDate;

    /**
     * 磅单号
     */
    private String wgtListNo;

    /**
     * 净重
     */
    private String netWgt;

    /**
     * 销账时间
     */
    private String closeTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

