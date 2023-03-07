package com.jlkj.material.mr.dto.materialssiloarea;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * DTO类-修改-料仓区域维护表
 * @author sudeyou
 */
@Data
public class SaveMaterialsSiloAreaDTO {

    /**
     * 料仓编号
     */
    @NotNull(message = "料仓编号为必填项")
    @NotEmpty(message = "料仓编号不能为空")
    @Size(max = 36, message = "料仓编号长度不能超过36")
    @JsonProperty("silo_id")
    private String siloId;

    /**
     * 创建人id
     */
    @NotNull(message = "创建人id为必填项")
    @NotEmpty(message = "创建人id不能为空")
    @Size(max = 36, message = "创建人id长度不能超过36")
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
     * 储位列表
     */
    @NotNull(message = "储位列表为必填项")
    @JsonProperty("storage_spaces")
    private List<UpdateMaterialsSiloAreaDTO> storageSpaces;

}

