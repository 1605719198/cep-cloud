package com.jlkj.product.oi.dto.productionplanconfigcoke;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.UpdateProductionPlanConfigCokeDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * DTO类-修改-配煤计划主记录
 * @author sudeyou
 */
@Data
@Schema(description = "DTO类-修改-配煤计划主记录")
public class UpdateProductionPlanConfigCokeDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

    /**
     * 计划编号
     */
    @NotNull(message = "计划编号为必填项")
    @NotEmpty(message = "计划编号不能为空")
    @Size(max = 90, message = "计划编号长度不能超过90")
    @Schema(description = "计划编号")
    @JsonProperty("plan_number")
    private String planNumber;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间为必填项")
    @NotEmpty(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    @JsonProperty("plan_start_time")
    private String planStartTime;

    /**
     * 修改人编号
     */
    @NotNull(message = "修改人编号为必填项")
    @NotEmpty(message = "修改人编号不能为空")
    @Size(max = 36, message = "修改人编号长度不能超过36")
    @Schema(description = "修改人编号")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    /**
     * 修改人
     */
    @NotNull(message = "修改人为必填项")
    @NotEmpty(message = "修改人不能为空")
    @Size(max = 50, message = "修改人长度不能超过50")
    @Schema(description = "修改人")
    @JsonProperty("modify_user_name")
    private String modifyUserName;

    /**
     * 物料类型id
     */
    @NotNull(message = "物料类型id为必填项")
    @NotEmpty(message = "物料类型id不能为空")
    @Schema(description = "物料类型id")
    @JsonProperty("material_id")
    private String materialId;

    /**
     * 物料类型
     */
    @NotNull(message = "物料类型为必填项")
    @NotEmpty(message = "物料类型不能为空")
    @Schema(description = "物料类型")
    @JsonProperty("material_name")
    private String materialName;

    /**
     * 配煤列表
     */
    @NotNull(message = "配煤列表为必填项")
    @NotEmpty(message = "配煤列表不能为空")
    @Schema(description = "配煤列表")
    @JsonProperty("detail_list")
    private List<UpdateProductionPlanConfigCokeDetailDTO> detailList;
//
//    /**
//     * 储煤塔号
//     */
//    @NotNull(message = "储煤塔号为必填项")
//    @Schema(description = "储煤塔号")
//    @JsonProperty("tower_number")
//    private Integer towerNumber;
}

