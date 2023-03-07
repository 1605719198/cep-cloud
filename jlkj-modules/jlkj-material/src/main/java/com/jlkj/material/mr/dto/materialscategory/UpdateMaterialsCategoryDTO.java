package com.jlkj.material.mr.dto.materialscategory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-修改-物料类别维护
 * @author sudeyou
 */
@Data
public class UpdateMaterialsCategoryDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @JsonProperty("id")
    private String id;

    /**
     * 类别名称
     */
    @NotNull(message = "类别名称为必填项")
    @NotEmpty(message = "类别名称不能为空")
    @Size(max = 36, message = "类别名称长度不能超过36")
    @JsonProperty("category_name")
    private String categoryName;

    /**
     * 类别代码
     */
    @NotNull(message = "类别代码为必填项")
    @NotEmpty(message = "类别代码不能为空")
    @Size(max = 50, message = "类别代码长度不能超过50")
    @JsonProperty("category_code")
    private String categoryCode;

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

