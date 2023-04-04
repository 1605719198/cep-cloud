package com.jlkj.product.oi.dto.productionplanoutputdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-生产管理-指标跟踪-图表-指标项(日实际)
 * @author sudeyou
 * @since 2022-07-13 14:48:56
 */
@Data
@Schema(description = "DTO类-生产管理-指标跟踪-图表-指标项(日计划)")
public class ListProductionPlanOutputDateTargetItemDTO {
    /**
     * 年份
     */
    @NotNull(message = "年份为必填项")
    @Schema(description = "年份")
    @JsonProperty("year")
    private Integer year;

    /**
     * 月份
     */
    @NotNull(message = "月份为必填项")
    @Schema(description = "月份")
    @JsonProperty("month")
    private Integer month;

    /**
     * 指标项名称
     */
    @Schema(description = "指标项名称")
    @NotNull(message = "指标项名称为必填项")
    @NotEmpty(message = "指标项名称不能为空")
    @Size(max = 50, message = "指标项名称长度不能超过50")
    @JsonProperty("target_item_name")
    private String targetItemName;
}

