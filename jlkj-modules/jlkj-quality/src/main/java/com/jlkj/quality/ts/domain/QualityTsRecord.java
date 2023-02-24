package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName v_quality_ts_record
 */
@TableName(value ="v_quality_ts_record")
@Data
public class QualityTsRecord implements Serializable {
    /**
     * 班别
     */
    private String shift;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 质量类型
     */
    private String recordtype;

    /**
     * 煤类型
     */
    private String coaltype;

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
     * 水份
     */
    private BigDecimal mt;

    /**
     * 灰分
     */
    private BigDecimal ad;

    /**
     * 挥发份
     */
    private BigDecimal vdaf;

    /**
     * 硫份
     */
    private BigDecimal stad;

    /**
     * M40
     */
    private BigDecimal m40;

    /**
     * M10
     */
    private BigDecimal m10;

    /**
     * CRI
     */
    private BigDecimal cri;

    /**
     * CSR
     */
    private BigDecimal csr;

    /**
     * 细度
     */
    private BigDecimal xd;

    /**
     * 
     */
    private BigDecimal rmax;

    /**
     * 
     */
    private BigDecimal y;

    /**
     * 
     */
    private BigDecimal g;

    /**
     * 
     */
    private BigDecimal x;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}