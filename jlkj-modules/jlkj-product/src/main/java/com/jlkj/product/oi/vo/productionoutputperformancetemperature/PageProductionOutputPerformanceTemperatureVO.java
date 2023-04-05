package com.jlkj.product.oi.vo.productionoutputperformancetemperature;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-出炉实绩测温记录-查询-分页
 * @author sudeyou
 * @since 2022-08-11 14:06:15
 */
@Data
@Schema(description = "VO类-出炉实绩测温记录-查询-分页")
public class PageProductionOutputPerformanceTemperatureVO {
    /**
     * 测温日期
     */
    @Schema(description = "测温日期")
    @JsonProperty("measure_time")
    private String measureTime;

    /**
     * 班次
     */
    @Schema(description = "班次")
    @JsonProperty("shift_name")
    private String shiftName;

    /**
     * 班别
     */
    @Schema(description = "班别")
    @JsonProperty("class_name")
    private String className;

    /**
     * 炉号
     */
    @Schema(description = "炉号")
    @JsonProperty("coke_oven_number")
    private Integer cokeOvenNumber;

    /**
     * 测温类型
     */
    @Schema(description = "测温类型")
    @JsonProperty("measure_type")
    private Short measureType;

    /**
     * 标准温度
     */
    @Schema(description = "标准温度")
    @JsonProperty("standard_temp")
    private BigDecimal standardTemp;

    /**
     * 高
     */
    @Schema(description = "高")
    @JsonProperty("high")
    private BigDecimal high;

    /**
     * 低
     */
    @Schema(description = "低")
    @JsonProperty("low")
    private BigDecimal low;

    /**
     * 最高温度
     */
    @Schema(description = "最高温度")
    @JsonProperty("maximum_temp")
    private BigDecimal maximumTemp;

    /**
     * 最低温度
     */
    @Schema(description = "最低温度")
    @JsonProperty("minimum_temp")
    private BigDecimal minimumTemp;

    /**
     * 平均温度
     */
    @Schema(description = "平均温度")
    @JsonProperty("average_temp")
    private BigDecimal averageTemp;

}

