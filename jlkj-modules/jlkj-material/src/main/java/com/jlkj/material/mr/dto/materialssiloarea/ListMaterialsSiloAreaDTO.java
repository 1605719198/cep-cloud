package com.jlkj.material.mr.dto.materialssiloarea;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-分页-料仓区域维护
 * @author sudeyou
 */
@Data
public class ListMaterialsSiloAreaDTO {

    /**
     * 料仓编号
     */
    @NotNull(message = "料仓编号为必填项")
    @NotEmpty(message = "料仓编号不能为空")
    @Size(max = 36, message = "料仓编号长度不能超过36")
    @JsonProperty("silo_id")
    private String siloId;

    /**
     * 是否显示0长度区域(0否,1是)
     */
    @NotNull(message = "是否显示0长度区域为必填项")
    @JsonProperty("show_zero")
    private Integer showZero;

}

