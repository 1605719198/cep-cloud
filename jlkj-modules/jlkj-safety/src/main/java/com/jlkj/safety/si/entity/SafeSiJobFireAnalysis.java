package com.jlkj.safety.si.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 动火安全作业证-动火分析
 * </p>
 *
 * @author su
 * @since 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobFireAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

    /**
     * 作业证ID
     */
    private String jobId;

    /**
     * 分析时间
     */
    private LocalDateTime analysisTime;

    /**
     * 分析点名称
     */
    private String pointName;

    /**
     * 分析数据
     */
    private String data;

    /**
     * 分析人
     */
    private String personName;

    /**
     * 动火分析地点
     */
    private String analysisArea;

    /**
     * 一氧化碳浓度
     */
    private BigDecimal coDensity;

    /**
     * 氧气化碳浓度
     */
    private BigDecimal o2Density;

    /**
     * 可燃气体化碳浓度
     */
    private BigDecimal othreDensity;

    /**
     * 分析人id
     */
    private String personId;



}
