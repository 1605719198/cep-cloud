package com.jlkj.material.mr.dto.materialssilo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-修改-料仓维护
 * @author sudeyou
 */
@Data
public class UpdateMaterialsSiloDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @JsonProperty("id")
    private String id;

    /**
     * 料仓名称
     */
    @NotNull(message = "料仓名称为必填项")
    @NotEmpty(message = "料仓名称不能为空")
    @Size(max = 50, message = "料仓名称长度不能超过50")
    @JsonProperty("silo_name")
    private String siloName;

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

    /**
     * 料仓长度
     */
    @NotNull(message = "料仓长度为必填项")
    @JsonProperty("silo_length")
    private Integer siloLength;

}

