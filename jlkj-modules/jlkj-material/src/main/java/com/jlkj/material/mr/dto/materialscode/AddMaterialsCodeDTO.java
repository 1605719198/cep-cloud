package com.jlkj.material.mr.dto.materialscode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-新增-物料代码维护
 * @author sudeyou
 */
@Data
public class AddMaterialsCodeDTO {
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
     * 创建人
     */
    @NotNull(message = "创建人为必填项")
    @NotEmpty(message = "创建人不能为空")
    @Size(max = 36, message = "创建人长度不能超过36")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人
     */
    @NotNull(message = "创建人为必填项")
    @NotEmpty(message = "创建人不能为空")
    @Size(max = 50, message = "创建人长度不能超过50")
    @JsonProperty("create_user_name")
    private String createUserName;

}

