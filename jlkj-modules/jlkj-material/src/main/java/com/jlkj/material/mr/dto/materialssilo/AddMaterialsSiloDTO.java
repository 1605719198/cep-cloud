package com.jlkj.material.mr.dto.materialssilo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-新增-料仓维护
 * @author sudeyou
 */
@Data
public class AddMaterialsSiloDTO {
    /**
     * 料仓名称
     */
    @NotNull(message = "料仓名称为必填项")
    @NotEmpty(message = "料仓名称不能为空")
    @Size(max = 50, message = "料仓名称长度不能超过50")
    @JsonProperty("silo_name")
    private String siloName;

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

    /**
     * 料仓长度
     */
    @NotNull(message = "料仓长度为必填项")
    @JsonProperty("silo_length")
    private Integer siloLength;

}

