package com.jlkj.product.oi.dto.productionstackerreclaimerperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO类-堆取料机实绩-新增
 *
 * @author sudeyou
 * @since 2022-11-09 16:08:57
 */
@Data
@Schema(description = "DTO类-堆取料机实绩-新增")
public class InsertProductionStackerReclaimerPerformanceDTO {
    /**
     * 班次
     */
    @NotNull(message = "班次为必填项")
    @NotEmpty(message = "班次不能为空")
    @Size(max = 50, message = "班次长度不能超过50")
    @Schema(description = "班次")
    @JsonProperty("shift_name")
    private String shiftName;

    /**
     * 班别
     */
    @NotNull(message = "班别为必填项")
    @NotEmpty(message = "班别不能为空")
    @Size(max = 50, message = "班别长度不能超过50")
    @Schema(description = "班别")
    @JsonProperty("class_name")
    private String className;

    /**
     * 堆取料机号
     */
    @NotNull(message = "堆取料机号为必填项")
    @Schema(description = "堆取料机号")
    @JsonProperty("stacker_reclaimer_number")
    private Integer stackerReclaimerNumber;

    /**
     * 储位编号
     */
    @NotNull(message = "储位编号为必填项")
    @NotEmpty(message = "储位编号不能为空")
    @Size(max = 36, message = "储位编号长度不能超过36")
    @Schema(description = "储位编号")
    @JsonProperty("slot_number")
    private String slotNumber;

    /**
     * 储位名称
     */
    @NotNull(message = "储位名称为必填项")
    @NotEmpty(message = "储位名称不能为空")
    @Size(max = 50, message = "储位名称长度不能超过50")
    @Schema(description = "储位名称")
    @JsonProperty("slot_name")
    private String slotName;

    /**
     * 上煤开始
     */
    @NotNull(message = "上煤开始为必填项")
    @NotEmpty(message = "上煤开始不能为空")
    @Schema(description = "上煤开始")
    @JsonProperty("start_time")
    private String startTime;

    /**
     * 上煤结束
     */
    @NotNull(message = "上煤结束为必填项")
    @NotEmpty(message = "上煤结束不能为空")
    @Schema(description = "上煤结束")
    @JsonProperty("end_time")
    private String endTime;

    /**
     * 上煤时长
     */
    @NotNull(message = "上煤时长为必填项")
    @Schema(description = "上煤时长")
    @JsonProperty("duration")
    private BigDecimal duration;

}
