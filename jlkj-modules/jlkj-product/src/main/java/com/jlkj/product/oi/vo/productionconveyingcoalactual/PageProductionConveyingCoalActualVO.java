package com.jlkj.product.oi.vo.productionconveyingcoalactual;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-生产实绩-上煤实绩-查询-分页
 *
 * @author sudeyou
 * @since 2022-11-22 11:20:46
 */
@Data
@Schema(description = "VO类-生产实绩-上煤实绩-查询-分页")
public class PageProductionConveyingCoalActualVO {
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
     * 班别名称
     */
    @Schema(description = "班别名称")
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
     * 上煤重量
     */
    @Schema(description = "上煤重量")
    @JsonProperty("coke_weight")
    private Integer cokeWeight;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
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
     * 修改人id
     */
    @Schema(description = "修改人id")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @JsonProperty("modify_user_name")
    private String modifyUserName;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonProperty("modify_time")
    private String modifyTime;

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
