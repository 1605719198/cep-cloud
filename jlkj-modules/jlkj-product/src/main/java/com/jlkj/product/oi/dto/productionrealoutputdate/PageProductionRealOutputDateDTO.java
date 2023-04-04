package com.jlkj.product.oi.dto.productionrealoutputdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-分页-生产管理-统计分析-指标跟踪
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
@Data
public class PageProductionRealOutputDateDTO {
    /**
     * 年份
     */
    @Schema(description = "年份")
    @NotNull(message = "年份为必填项")
    @JsonProperty("year")
    private Integer year;

    /**
     * 月份
     */
    @Schema(description = "月份")
    @NotNull(message = "月份为必填项")
    @JsonProperty("month")
    private Integer month;

    /**
     * 指标项名称
     */
    @Schema(description = "指标项名称")
    @NotNull(message = "指标项名称为必填项")
    @Size(max = 50, message = "指标项名称长度不能超过50")
    @JsonProperty("target_item_name")
    private String targetItemName;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    @NotNull(message = "当前页为必填项")
    @Min(value = 1, message = "当前页不能小于1")
    private Long current;

    /**
     * 每页行数
     */
    @Schema(description = "每页行数")
    @NotNull(message = "每页行数为必填项")
    @Min(value = 1, message = "每页行数不能小于1")
    private Long size;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    @NotNull(message = "排序字段为必填项")
    @NotEmpty(message = "排序字段不能为空")
    private String order;

    /**
     * 排序规则
     */
    @Schema(description = "排序规则")
    @NotNull(message = "排序规则为必填项")
    private String orderby;
}

