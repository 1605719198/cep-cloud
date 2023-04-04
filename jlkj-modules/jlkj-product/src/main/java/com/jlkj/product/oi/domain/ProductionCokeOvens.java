package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-每日推焦炉数维护
 *
 * @author sudeyou
 * @since 2022-11-01 10:10:47
 */
@TableName(value ="product_oi_coke_ovens")
@Data
public class ProductionCokeOvens implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 账务日期
     */
    private Date accountDate;

    /**
     * 物料代码
     */
    private String materialsCode;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 日推焦总炉数
     */
    private Integer outNum;

    /**
     * 日理论总产量(t)(全焦量)
     */
    private BigDecimal theoreticalYield;

    /**
     * 日推焦干熄炉数
     */
    private Integer dryOutNum;

    /**
     * 日理论干熄产量(t)(全焦量)
     */
    private BigDecimal dryTheoreticalYield;

    /**
     * 日推焦湿熄炉数
     */
    private Integer wetOutNum;

    /**
     * 日理论湿熄产量(t)(全焦量)
     */
    private BigDecimal wetTheoreticalYield;

    /**
     * 抛送状态(0:未抛送；1:已抛送)
     */
    private String throwState;

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

