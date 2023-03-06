package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-焦炭出厂实绩
 *
 * @author sudeyou
 * @since 2022-11-04 15:42:01
 */
@TableName(value ="production_xx_coke_factory_performance")
@Data
public class ProductionCokeFactoryPerformance implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 发货单号
     */
    private String planListNo;

    /**
     * 班次
     */
    private String shiftName;

    /**
     * 班别
     */
    private String className;

    /**
     * 成员
     */
    private String person;

    /**
     * 客户id
     */
    private String clientId;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 物料代码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 车牌号
     */
    private String vehicleNumber;

    /**
     * 装车时间
     */
    private Date loadingTime;

    /**
     * 净重
     */
    private BigDecimal netWgt;

    /**
     * 申请人工号
     */
    private String issueEmpNo;

    /**
     * 申请人姓名
     */
    private String issueEmpName;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

