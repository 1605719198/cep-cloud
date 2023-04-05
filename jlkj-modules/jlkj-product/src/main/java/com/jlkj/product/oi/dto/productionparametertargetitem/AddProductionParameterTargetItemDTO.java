package com.jlkj.product.oi.dto.productionparametertargetitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class AddProductionParameterTargetItemDTO {

    @NotNull(message = "指标类型id必须项")
    @JsonProperty("target_item_type_id")
    private Integer targetItemTypeId;

    @NotNull(message = "指标名称必须项")
    @NotEmpty(message = "指标名称不能为空")
    @Size(max = 90, message = "指标名称长度不能超过90")
    @JsonProperty("target_item_name")
    private String targetItemName;

    @NotNull(message = "计算单位id必须项")
    @JsonProperty("calc_unit_id")
    private Integer calcUnitId;

    @NotNull(message = "物料代码必须项")
    @JsonProperty("material_id")
    private String materialId;

    @NotNull(message = "物料代码必须项")
    @JsonProperty("material_name")
    private String materialName;

    @NotNull(message = "计量单位必须项")
    @NotEmpty(message = "计量单位不能为空")
    @Size(max = 90, message = "计量单位长度不能超过90")
    @JsonProperty("measuring_unit")
    private String measuringUnit;

    @NotNull(message = "创建人编号为必须项")
    @NotEmpty(message = "创建人编号不能为空")
    @JsonProperty("create_user_id")
    private String createUserId;

    @NotNull(message = "创建人为必须项")
    @NotEmpty(message = "创建人不能为空")
    @JsonProperty("create_user_name")
    private String createUserName;
}
