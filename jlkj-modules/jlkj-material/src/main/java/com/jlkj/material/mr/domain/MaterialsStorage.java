package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 原料入储表
 * @TableName materials_mr_storage
 * @author zyf
 */
@TableName(value ="materials_mr_storage")
@Data
public class MaterialsStorage implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 计划单号
     */
    private String planListNo;

    /**
     * 储位代码
     */
    private String stgNo;

    /**
     * 储位说明
     */
    private String stgName;

    /**
     * 组批号
     */
    private String chkNo;

    /**
     * 传送类型
     */
    private String transType;

    /**
     * 运输方式
     */
    private String carrierType;

    /**
     * 到货日期
     */
    private Date recvDate;

    /**
     * 编组号
     */
    private String lotNo;

    /**
     * 车号
     */
    private String carrierNo;

    /**
     * 序号
     */
    private String seqNo;

    /**
     * 料号
     */
    private String mtrlNo;

    /**
     * 料号说明
     */
    private String mtrlName;

    /**
     * 来源代码
     */
    private String fromCode;

    /**
     * 来源说明
     */
    private String fromDesc;

    /**
     * 去向代码
     */
    private String toCode;

    /**
     * 去向说明
     */
    private String toDesc;

    /**
     * 采购合同号
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
     * 申请单位
     */
    private String issueDept;

    /**
     * 申请人
     */
    private String issueEmpNo;

    /**
     * 申请日期
     */
    private Date issueDate;

    /**
     * 是否回皮
     */
    private String isBack;

    /**
     * 磅单号
     */
    private String wgtListNo;

    /**
     * 毛重
     */
    private BigDecimal grossWgt;

    /**
     * 皮重
     */
    private BigDecimal tartWgt;

    /**
     * 净重
     */
    private BigDecimal netWgt;

    /**
     * 检斤实绩时间
     */
    private Date loadDateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}