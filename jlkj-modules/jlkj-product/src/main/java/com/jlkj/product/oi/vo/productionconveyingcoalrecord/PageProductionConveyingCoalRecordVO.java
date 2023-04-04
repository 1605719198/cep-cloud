package com.jlkj.product.oi.vo.productionconveyingcoalrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-生产实绩-上煤记录-查询-分页
 *
 * @author sudeyou
 * @since 2022-11-22 11:02:01
 */
@Data
@Schema(description = "VO类-生产实绩-上煤记录-查询-分页")
public class PageProductionConveyingCoalRecordVO {
    /**
     * 主键标识;UUID
     */
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

    /**
     * 班次代码
     */
    @Schema(description = "班次代码")
    @JsonProperty("shift_name")
    private String shiftName;

    /**
     * 班次代码
     */
    @Schema(description = "班次代码")
    @JsonProperty("class_name")
    private String className;

    /**
     * 配煤计划id
     */
    @Schema(description = "配煤计划id")
    @JsonProperty("config_coke_plan_id")
    private String configCokePlanId;

    /**
     * 配煤计划编号
     */
    @Schema(description = "配煤计划编号")
    @JsonProperty("plan_number")
    private String planNumber;

    /**
     * 焦炭等级料号
     */
    @Schema(description = "焦炭等级料号")
    @JsonProperty("coal_level_materials_code")
    private String coalLevelMaterialsCode;

    /**
     * 焦炭等级名称
     */
    @Schema(description = "焦炭等级名称")
    @JsonProperty("coal_level_materials_name")
    private String coalLevelMaterialsName;

    /**
     * 配煤仓号
     */
    @Schema(description = "配煤仓号")
    @JsonProperty("coke_warehouse_number")
    private Integer cokeWarehouseNumber;

    /**
     * 煤的料号
     */
    @Schema(description = "煤的料号")
    @JsonProperty("coke_materials_code")
    private String cokeMaterialsCode;

    /**
     * 煤的料号名称
     */
    @Schema(description = "煤的料号名称")
    @JsonProperty("coke_materials_name")
    private String cokeMaterialsName;

    /**
     * 上煤开始时间
     */
    @Schema(description = "上煤开始时间")
    @JsonProperty("start_time")
    private String startTime;

    /**
     * 上煤结束时间
     */
    @Schema(description = "上煤结束时间")
    @JsonProperty("end_time")
    private String endTime;

    /**
     * 上煤重量
     */
    @Schema(description = "上煤重量")
    @JsonProperty("coke_weight")
    private Integer cokeWeight;

    /**
     * 上煤前重量
     */
    @Schema(description = "上煤前重量")
    @JsonProperty("coke_weight_befor")
    private Integer cokeWeightBefor;

    /**
     * 上煤后重量
     */
    @Schema(description = "上煤后重量")
    @JsonProperty("coke_weight_after")
    private Integer cokeWeightAfter;

    /**
     * 上煤前料位
     */
    @Schema(description = "上煤前料位")
    @JsonProperty("coke_material_level_befor")
    private Integer cokeMaterialLevelBefor;

    /**
     * 上煤后料位
     */
    @Schema(description = "上煤后料位")
    @JsonProperty("coke_material_level_after")
    private Integer cokeMaterialLevelAfter;

    /**
     * 数据来源(0:手工填报；1:数据采集)
     */
    @Schema(description = "数据来源(0:手工填报；1:数据采集)")
    @JsonProperty("data_sources")
    private Short dataSources;

    /**
     * 创建人id
     */
    @Schema(description = "创建人id")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @JsonProperty("create_user_name")
    private String createUserName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonProperty("create_time")
    private String createTime;

    /**
     * 小煤种编号
     */
    @Schema(description = "小煤种编号")
    @JsonProperty("coke_materials_small_code")
    private String cokeMaterialsSmallCode;

    /**
     * 小煤种名称
     */
    @Schema(description = "小煤种名称")
    @JsonProperty("coke_materials_small_name")
    private String cokeMaterialsSmallName;
}
