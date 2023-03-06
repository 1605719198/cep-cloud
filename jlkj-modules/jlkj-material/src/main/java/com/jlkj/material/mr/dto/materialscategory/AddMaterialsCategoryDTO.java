package com.jlkj.material.mr.dto.materialscategory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-新增-物料类别维护
 * @author sudeyou
 */
@Data
public class AddMaterialsCategoryDTO {
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

