package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zyf
 * 焦炭产量记录
 * @TableName product_oi_coke_records
 */
@TableName(value ="product_oi_coke_records")
@Data
public class ProductionCokeRecords implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 班次
     */
    private String shiftName;

    /**
     * 班别
     */
    private String className;

    /**
     * 班组开始时间
     */
    private Date classStartTime;

    /**
     * 班组结束时间
     */
    private Date classEndTime;

    /**
     * 物料代码
     */
    private String materialCode;

    /**
     * 物料名称(产品)
     */
    private String materialName;

    /**
     * 产出量
     */
    private BigDecimal materialWeight;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
