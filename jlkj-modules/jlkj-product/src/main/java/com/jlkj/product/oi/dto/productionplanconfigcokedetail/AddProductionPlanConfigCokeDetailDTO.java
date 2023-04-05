package com.jlkj.product.oi.dto.productionplanconfigcokedetail;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO类-新增-配煤计划配煤记录
 * @author sudeyou
 */
@Data
@Schema(description = "DTO类-新增-配煤计划配煤记录")
public class AddProductionPlanConfigCokeDetailDTO {
    /**
     * 计划编号
     */
    @NotNull(message = "计划编号为必填项")
    @NotEmpty(message = "计划编号不能为空")
    @Size(max = 36, message = "计划编号长度不能超过36")
    @Schema(description = "计划编号")
    @JsonProperty("plan_id")
    private String planId;

    /**
     * 配煤仓号
     */
    @NotNull(message = "配煤仓号为必填项")
    @Schema(description = "配煤仓号")
    @JsonProperty("warehouse_number")
    private Integer warehouseNumber;

    /**
     * 煤种编号
     */
    @NotNull(message = "煤种编号为必填项")
    @NotEmpty(message = "煤种编号不能为空")
    @Size(max = 36, message = "煤种编号长度不能超过36")
    @Schema(description = "煤种编号")
    @JsonProperty("coal_type_id")
    private String coalTypeId;

    /**
     * 煤种名称
     */
    @NotNull(message = "煤种名称为必填项")
    @NotEmpty(message = "煤种名称不能为空")
    @Size(max = 90, message = "煤种名称长度不能超过90")
    @Schema(description = "煤种名称")
    @JsonProperty("coal_type_name")
    private String coalTypeName;

    /**
     * 小煤种代码
     */
    @NotNull(message = "小煤种代码为必填项")
    @NotEmpty(message = "小煤种代码不能为空")
    @Schema(description = "小煤种代码")
    @JsonProperty("materials_small_code")
    private String materialsSmallCode;

    /**
     * 小煤种简称
     */
    @NotNull(message = "小煤种简称为必填项")
    @NotEmpty(message = "小煤种简称不能为空")
    @Schema(description = "小煤种简称")
    @JsonProperty("materials_small_name")
    private String materialsSmallName;

    /**
     * 配置比例
     */
    @NotNull(message = "配置比例为必填项")
    @Schema(description = "配置比例")
    @JsonProperty("proportion")
    private BigDecimal proportion;

    /**
     * 创建人编号
     */
    @NotNull(message = "创建人编号为必填项")
    @NotEmpty(message = "创建人编号不能为空")
    @Size(max = 36, message = "创建人编号长度不能超过36")
    @Schema(description = "创建人编号")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人
     */
    @NotNull(message = "创建人为必填项")
    @NotEmpty(message = "创建人不能为空")
    @Size(max = 50, message = "创建人长度不能超过50")
    @Schema(description = "创建人")
    @JsonProperty("create_user_name")
    private String createUserName;

}

