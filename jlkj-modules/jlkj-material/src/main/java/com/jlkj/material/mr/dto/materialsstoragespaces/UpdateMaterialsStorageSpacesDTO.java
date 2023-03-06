package com.jlkj.material.mr.dto.materialsstoragespaces;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-修改-储位维护
 * @author sudeyou
 */
@Data
public class UpdateMaterialsStorageSpacesDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @JsonProperty("id")
    private String id;

    /**
     * 料仓编号
     */
    @NotNull(message = "料仓编号为必填项")
    @NotEmpty(message = "料仓编号不能为空")
    @Size(max = 36, message = "料仓编号长度不能超过36")
    @JsonProperty("silo_id")
    private String siloId;

    /**
     * 物料类别id
     */
    @NotNull(message = "物料类别id为必填项")
    @NotEmpty(message = "物料类别id不能为空")
    @Size(max = 36, message = "物料类别id长度不能超过36")
    @JsonProperty("material_category_id")
    private String materialCategoryId;

    /**
     * 储位名称
     */
    @NotNull(message = "储位名称为必填项")
    @NotEmpty(message = "储位名称不能为空")
    @Size(max = 50, message = "储位名称长度不能超过50")
    @JsonProperty("storage_spaces_name")
    private String storageSpacesName;

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

