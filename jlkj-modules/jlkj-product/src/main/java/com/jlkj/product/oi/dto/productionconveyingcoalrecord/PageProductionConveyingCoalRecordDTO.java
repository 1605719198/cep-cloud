package com.jlkj.product.oi.dto.productionconveyingcoalrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-生产实绩-上煤记录-查询-分页
 *
 * @author sudeyou
 * @since 2022-11-22 11:02:01
 */
@Data
@Schema(description = "DTO类-生产实绩-上煤记录-查询-分页")
public class PageProductionConveyingCoalRecordDTO {
    /**
     * 班次代码
     */
    @NotNull(message = "班次代码为必填项")
    @Size(max = 50, message = "班次代码长度不能超过50")
    @Schema(description = "班次代码")
    @JsonProperty("shift_name")
    private String shiftName;

    /**
     * 班次代码
     */
    @NotNull(message = "班次代码为必填项")
    @Size(max = 50, message = "班次代码长度不能超过50")
    @Schema(description = "班次代码")
    @JsonProperty("class_name")
    private String className;

    /**
     * 煤的料号
     */
    @NotNull(message = "煤的料号为必填项")
    @Size(max = 50, message = "煤的料号长度不能超过50")
    @Schema(description = "煤的料号")
    @JsonProperty("coke_materials_code")
    private String cokeMaterialsCode;

    /**
     * 上煤开始时间[开始]
     */
    @NotNull(message = "上煤开始时间[开始]为必填项")
    @Schema(description = "上煤开始时间[开始]")
    @JsonProperty("start_time")
    private String startTime;

    /**
     * 上煤开始时间[结束]
     */
    @NotNull(message = "上煤开始时间[结束]为必填项")
    @Schema(description = "上煤开始时间[结束]")
    @JsonProperty("end_time")
    private String endTime;

    /**
     * 当前页
     */
    @NotNull(message = "当前页current为必填项")
    @Min(value = 1, message = "当前页current不能小于1")
    @Schema(description = "当前页")
    private Long current;

    /**
     * 每页行数
     */
    @NotNull(message = "每页行数size为必填项")
    @Min(value = 1, message = "每页行数size不能小于1")
    @Schema(description = "第页行数")
    private Long size;

    /**
     * 排序字段
     */
    @NotNull(message = "排序字段order为必填项")
    @NotEmpty(message = "排序字段order不能为空")
    @Schema(description = "排序字段")
    private String order;

    /**
     * 排序规则
     */
    @NotNull(message = "排序规则orderby为必填项")
    @Schema(description = "排序规则")
    private String orderby;
}

