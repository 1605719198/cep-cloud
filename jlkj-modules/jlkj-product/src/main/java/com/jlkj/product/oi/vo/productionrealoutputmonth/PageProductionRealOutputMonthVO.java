package com.jlkj.product.oi.vo.productionrealoutputmonth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-查询-分页-生产管理-统计分析-指标跟踪
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
@Data
public class PageProductionRealOutputMonthVO {
    /**
     * 年份
     */
    @Schema(description = "年份")
    @JsonProperty("year")
    private Integer year;

    /**
     * 月份
     */
    @Schema(description = "月份")
    @JsonProperty("month")
    private Integer month;

    /**
     * 日
     */
    @Schema(description = "日")
    @JsonProperty("date")
    private Integer date;

    /**
     * 指标项名称
     */
    @Schema(description = "指标项名称")
    @JsonProperty("target_item_name")
    private String targetItemName;

    /**
     * 计划指标项产量
     */
    @Schema(description = "计划指标项产量")
    @JsonProperty("target_item_output")
    private BigDecimal targetItemOutput;

    /**
     * 实绩指标项产量
     */
    @Schema(description = "实绩指标项产量")
    @JsonProperty("target_item_output_real")
    private BigDecimal targetItemOutputReal;

    /**
     * 去年平均
     */
    @Schema(description = "去年平均")
    @JsonProperty("average_last_year")
    private BigDecimal averageLastYear;

    /**
     * 去年同期
     */
    @Schema(description = "去年同期")
    @JsonProperty("same_time_last_year")
    private BigDecimal sameTimeLastYear;

    /**
     * 历史最大值
     */
    @Schema(description = "历史最大值")
    @JsonProperty("historical_maximum")
    private BigDecimal historicalMaximum;

    /**
     * 历史最小值
     */
    @Schema(description = "历史最小值")
    @JsonProperty("historical_minimum")
    private BigDecimal historicalMinimum;

}

