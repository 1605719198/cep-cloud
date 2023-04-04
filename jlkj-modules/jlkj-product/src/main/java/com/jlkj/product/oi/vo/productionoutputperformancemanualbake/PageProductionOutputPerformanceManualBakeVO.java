package com.jlkj.product.oi.vo.productionoutputperformancemanualbake;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-炼焦实绩-出炉实绩-手动出炉-查询-分页
 *
 * @author sudeyou
 * @since 2022-12-28 13:35:48
 */
@Data
@Schema(description = "VO类-生产管理-炼焦实绩-出炉实绩-手动出炉-查询-分页")
public class PageProductionOutputPerformanceManualBakeVO {
    /**
     * 主键标识;UUID
     */
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

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
     * 炭化室号
     */
    @Schema(description = "炭化室号")
    @JsonProperty("carbonization_chamber_number")
    private Integer carbonizationChamberNumber;

    /**
     * 生产日期
     */
    @Schema(description = "生产日期")
    @JsonProperty("production_date")
    private String productionDate;

    /**
     * 计划推焦时间
     */
    @Schema(description = "计划推焦时间")
    @JsonProperty("planning_production_time")
    private String planningProductionTime;

    /**
     * 实际推焦时间
     */
    @Schema(description = "实际推焦时间")
    @JsonProperty("actual_production_time")
    private String actualProductionTime;

    /**
     * 推焦电流
     */
    @Schema(description = "推焦电流")
    @JsonProperty("coke_current")
    private BigDecimal cokeCurrent;

    /**
     * 1:干熄焦；2:湿熄焦
     */
    @Schema(description = "1:干熄焦；2:湿熄焦")
    @JsonProperty("coke_type")
    private Short cokeType;

    /**
     * 计划运转周期
     */
    @Schema(description = "计划运转周期")
    @JsonProperty("planning_run_cycle")
    private BigDecimal planningRunCycle;

    /**
     * 实际运转周期
     */
    @Schema(description = "实际运转周期")
    @JsonProperty("actual_run_cycle")
    private BigDecimal actualRunCycle;

    /**
     * 单孔产量
     */
    @Schema(description = "单孔产量")
    @JsonProperty("single_hole_production")
    private BigDecimal singleHoleProduction;

    /**
     * 计划装煤时间
     */
    @Schema(description = "计划装煤时间")
    @JsonProperty("planning_load_coal_time")
    private String planningLoadCoalTime;

    /**
     * 实际装煤时间
     */
    @Schema(description = "实际装煤时间")
    @JsonProperty("actual_load_coal_time")
    private String actualLoadCoalTime;

    /**
     * 装煤电流
     */
    @Schema(description = "装煤电流")
    @JsonProperty("coal_current")
    private BigDecimal coalCurrent;

    /**
     * 装煤量
     */
    @Schema(description = "装煤量")
    @JsonProperty("loading_coal")
    private BigDecimal loadingCoal;

    /**
     * 焦炭等级名称
     */
    @Schema(description = "焦炭等级名称")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 储煤塔号
     */
    @Schema(description = "储煤塔号")
    @JsonProperty("coal_tower_number")
    private String coalTowerNumber;


}
