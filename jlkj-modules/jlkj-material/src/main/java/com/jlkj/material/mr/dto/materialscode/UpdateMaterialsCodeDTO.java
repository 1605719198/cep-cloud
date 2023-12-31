package com.jlkj.material.mr.dto.materialscode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-修改-物料代码维护
 * @author sudeyou
 */
@Data
public class UpdateMaterialsCodeDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @JsonProperty("id")
    private String id;

    /**
     * 类别编号
     */
    @NotNull(message = "类别编号为必填项")
    @NotEmpty(message = "类别编号不能为空")
    @Size(max = 36, message = "类别编号长度不能超过36")
    @JsonProperty("category_id")
    private String categoryId;

    /**
     * 物料名称
     */
    @NotNull(message = "物料名称为必填项")
    @NotEmpty(message = "物料名称不能为空")
    @Size(max = 50, message = "物料名称长度不能超过50")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 物料代码
     */
    @NotNull(message = "物料代码为必填项")
    @NotEmpty(message = "物料代码不能为空")
    @Size(max = 50, message = "物料代码长度不能超过50")
    @JsonProperty("materials_code")
    private String materialsCode;

    /**
     * 计量单位
     */
    @NotNull(message = "计量单位为必填项")
    @NotEmpty(message = "计量单位不能为空")
    @Size(max = 50, message = "计量单位长度不能超过50")
    @JsonProperty("measurement_unit")
    private String measurementUnit;

    /**
     * 修改人id
     */
    @NotNull(message = "修改人id为必填项")
    @NotEmpty(message = "修改人id不能为空")
    @Size(max = 36, message = "修改人id长度不能超过36")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    /**
     * 修改人
     */
    @NotNull(message = "修改人为必填项")
    @NotEmpty(message = "修改人不能为空")
    @Size(max = 50, message = "修改人长度不能超过50")
    @JsonProperty("modify_user_name")
    private String modifyUserName;

}

