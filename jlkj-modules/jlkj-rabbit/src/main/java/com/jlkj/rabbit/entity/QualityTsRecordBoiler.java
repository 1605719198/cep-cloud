package com.jlkj.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @TableName v_quality_ts_record_boiler
 */
@TableName(value ="v_quality_ts_record_boiler")
@Data
public class QualityTsRecordBoiler implements Serializable {
    /**
     * 班别
     */
    private String shift;

    /**
     * 来样时间
     */
    private String receiveTime;

    /**
     * 报出时间
     */
    private String sendTime;

    /**
     *
     */
    private String recordtype;

    /**
     *
     */
    private String coaltype;

    /**
     *
     */
    private String coaltype2;

    /**
     * 样品分类代号
     */
    private String code;

    /**
     * 传输格式
     */
    private String formatId;

    /**
     * 样品编号
     */
    private String sampleId;

    /**
     * 样品编号主表ID
     */
    private String parentId;

    /**
     *
     */
    private BigDecimal ph;

    /**
     *
     */
    private BigDecimal 电导率;

    /**
     *
     */
    private BigDecimal 二氧化硅;

    /**
     *
     */
    private BigDecimal 总硬度;

    /**
     *
     */
    private BigDecimal 正磷酸盐;

    /**
     *
     */
    private BigDecimal 氯离子;

    /**
     *
     */
    private BigDecimal 钠离子;

    /**
     *
     */
    private BigDecimal 二氧化碳;

    /**
     *
     */
    private BigDecimal 一氧化碳;

    /**
     *
     */
    private BigDecimal 氧气;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
