package com.jlkj.product.oi.dto.productionoutputperformancemanualbake;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO类-生产管理-炼焦实绩-出炉实绩-手动出炉-新增
 *
 * @author sudeyou
 * @since 2022-12-28 13:23:57
 */
@Data
@Schema(description = "DTO类-生产管理-炼焦实绩-出炉实绩-手动出炉-新增")
public class InsertProductionOutputPerformanceManualBakeDTO {
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
     * 班组开始时间
     */
    @NotNull(message = "班组开始时间为必填项")
    @NotEmpty(message = "班组开始时间不能为空")
    @Schema(description = "班组开始时间")
    @JsonProperty("class_start_time")
    private String classStartTime;

    /**
     * 班组结束时间
     */
    @NotNull(message = "班组结束时间为必填项")
    @NotEmpty(message = "班组结束时间不能为空")
    @Schema(description = "班组结束时间")
    @JsonProperty("class_end_time")
    private String classEndTime;

    /**
     * 炉号
     */
    @NotNull(message = "炉号为必填项")
    @Schema(description = "炉号")
    @JsonProperty("coke_oven_number")
    private Integer cokeOvenNumber;

    /**
     * 炭化室号
     */
    @NotNull(message = "炭化室号为必填项")
    @Schema(description = "炭化室号")
    @JsonProperty("carbonization_chamber_number")
    private Integer carbonizationChamberNumber;

    /**
     * 生产日期
     */
    @NotNull(message = "生产日期为必填项")
    @NotEmpty(message = "生产日期不能为空")
    @Schema(description = "生产日期")
    @JsonProperty("production_date")
    private String productionDate;

    /**
     * 实际推焦时间
     */
    @NotNull(message = "实际推焦时间为必填项")
    @NotEmpty(message = "实际推焦时间不能为空")
    @Schema(description = "实际推焦时间")
    @JsonProperty("actual_production_time")
    private String actualProductionTime;

    /**
     * 1:干熄焦；2:湿熄焦
     */
    @NotNull(message = "1:干熄焦；2:湿熄焦为必填项")
    @Schema(description = "1:干熄焦；2:湿熄焦")
    @JsonProperty("coke_type")
    private Short cokeType;

    /**
     * 储煤塔号
     */
    @NotNull(message = "储煤塔号为必填项")
    @Schema(description = "储煤塔号")
    @JsonProperty("coal_tower_number")
    private Integer coalTowerNumber;

    /**
     * 实际装煤时间
     */
    @NotNull(message = "实际装煤时间为必填项")
    @NotEmpty(message = "实际装煤时间不能为空")
    @Schema(description = "实际装煤时间")
    @JsonProperty("actual_load_coal_time")
    private String actualLoadCoalTime;

    /**
     * 装煤量
     */
    @NotNull(message = "装煤量为必填项")
    @Schema(description = "装煤量")
    @JsonProperty("loading_coal")
    private BigDecimal loadingCoal;

    /**
     * 创建人id(数据采集为空)
     */
    @NotNull(message = "创建人id为必填项")
    @NotEmpty(message = "创建人id不能为空")
    @Size(max = 36, message = "创建人id长度不能超过36")
    @Schema(description = "创建人id(数据采集为空)")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人姓名(数据采集为空)
     */
    @NotNull(message = "创建人姓名为必填项")
    @NotEmpty(message = "创建人姓名不能为空")
    @Size(max = 50, message = "创建人姓名长度不能超过50")
    @Schema(description = "创建人姓名(数据采集为空)")
    @JsonProperty("create_user_name")
    private String createUserName;

}
