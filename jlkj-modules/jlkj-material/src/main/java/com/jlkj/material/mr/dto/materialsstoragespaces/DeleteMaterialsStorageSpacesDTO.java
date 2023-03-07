package com.jlkj.material.mr.dto.materialsstoragespaces;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-删除-储位维护表
 * @author sudeyou
 */
@Data
public class DeleteMaterialsStorageSpacesDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @JsonProperty("id")
    private String id;

    @NotNull(message = "删除人编号为必须项")
    @NotEmpty(message = "删除人编号不能为空")
    @JsonProperty("delete_user_id")
    private String deleteUserId;

    @NotNull(message = "删除人为必须项")
    @NotEmpty(message = "删除人不能为空")
    @JsonProperty("delete_user_name")
    private String deleteUserName;

}

