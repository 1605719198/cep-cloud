package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  generator
 * 堆取料机实绩
 * @TableName product_oi_stacker_reclaimer_performance
 */
@TableName(value ="product_oi_stacker_reclaimer_performance")
@Data
public class ProductionStackerReclaimerPerformance implements Serializable {
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
     * 堆取料机号
     */
    private Integer stackerReclaimerNumber;

    /**
     * 储位编号
     */
    private String slotNumber;

    /**
     * 储位名称
     */
    private String slotName;

    /**
     * 上煤开始
     */
    private Date startTime;

    /**
     * 上煤结束
     */
    private Date endTime;

    /**
     * 上煤时长
     */
    private BigDecimal duration;

    /**
     * 物料编号
     */
    private String materialNumber;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
