package com.jlkj.product.oi.dto.productionoutputperformanceplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-生产管理-炼焦实绩-推焦计划-数据同步-查询-分页
 *
 * @author sudeyou
 * @since 2023-04-10 10:11:01
 */
@Data
@Schema(description = "DTO类-生产管理-炼焦实绩-推焦计划-数据同步-查询-分页")
public class PageProductionOutputPerformancePlanDTO {
    /**
     * 炉区
     */
    @NotNull(message = "炉区为必填项")
    @Schema(description = "炉区")
    @JsonProperty("furnace_area")
    private Integer furnaceArea;

    /**
     * 班组
     */
    @NotNull(message = "班组为必填项")
    @Size(max = 30, message = "班组长度不能超过30")
    @Schema(description = "班组")
    @JsonProperty("class_name")
    private String className;

    /**
     * 计划推焦时间[开始]
     */
    @NotNull(message = "计划推焦时间[开始]为必填项")
    @Schema(description = "计划推焦时间[开始]")
    @JsonProperty("start_time")
    private String startTime;

    /**
     * 计划推焦时间[结束]
     */
    @NotNull(message = "计划推焦时间[结束]为必填项")
    @Schema(description = "计划推焦时间[结束]")
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
