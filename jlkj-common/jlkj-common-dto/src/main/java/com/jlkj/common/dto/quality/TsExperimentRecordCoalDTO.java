package com.jlkj.common.dto.quality;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 智能研发室 黄兵
 * @date 2022/8/31 - 08:37
 */
@Data
public class TsExperimentRecordCoalDTO {
    /**
     * 班别
     */
    private String className;

    /**
     * 样品编号
     */
    private String sampleNo;

    /**
     * 水份
     */
    private BigDecimal water;

    /**
     * 灰份
     */
    private BigDecimal crozzle;

    /**
     * 挥发份
     */
    private BigDecimal volatileFlux;

    /**
     * 硫份
     */
    private BigDecimal sulfuration;

    /**
     * 细度
     */
    private BigDecimal fineness;

    /**
     * G
     */
    private BigDecimal gHem;

    /**
     * X
     */
    private BigDecimal xHem;

    /**
     * Y
     */
    private BigDecimal yHem;
}
