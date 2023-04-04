package com.jlkj.product.oi.dto.productionrealoutputmonth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DTO类-生产管理-指标跟踪-图表-日期(同环比)
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
@Data
public class ListProductionRealOutputMonthRangeDTO {
    /**
     * 年份[开始]
     */
    @Schema(description = "年份[开始]")
    @NotNull(message = "年份[开始]为必填项")
    @JsonProperty("start_year")
    private Integer startYear;

    /**
     * 年份[结束]
     */
    @Schema(description = "年份[结束]")
    @NotNull(message = "年份[结束]为必填项")
    @JsonProperty("end_year")
    private Integer endYear;

    /**
     * 月份[开始]
     */
    @Schema(description = "月份[开始]")
    @NotNull(message = "月份[开始]为必填项")
    @JsonProperty("start_month")
    private Integer startMonth;

    /**
     * 月份[结束]
     */
    @Schema(description = "月份[结束]")
    @NotNull(message = "月份[结束]为必填项")
    @JsonProperty("end_month")
    private Integer endMonth;

}

