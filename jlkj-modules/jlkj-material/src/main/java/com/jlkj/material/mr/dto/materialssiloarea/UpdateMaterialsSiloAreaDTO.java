package com.jlkj.material.mr.dto.materialssiloarea;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-修改-料仓区域维护表
 * @author sudeyou
 */
@Data
public class UpdateMaterialsSiloAreaDTO {

    /**
     * 储位编号
     */
    @NotNull(message = "储位编号为必填项")
    @NotEmpty(message = "储位编号不能为空")
    @Size(max = 36, message = "储位编号长度不能超过36")
    @JsonProperty("storage_spaces_id")
    private String storageSpacesId;

    /**
     * 起始位置
     */
    @NotNull(message = "起始位置为必填项")
    @JsonProperty("start_location")
    private Integer startLocation;

    /**
     * 结束位置
     */
    @NotNull(message = "结束位置为必填项")
    @JsonProperty("end_location")
    private Integer endLocation;

    /**
     * 颜色值
     */
    @NotNull(message = "颜色值为必填项")
    @NotEmpty(message = "颜色值不能为空")
    @Size(max = 50, message = "颜色值长度不能超过50")
    @JsonProperty("color_value")
    private String colorValue;

//    /**
//     * 物料编号
//     */
//    @NotNull(message = "物料编号为必填项")
//    @NotEmpty(message = "物料编号不能为空")
//    @Size(max = 50, message = "物料编号长度不能超过36")
//    @JsonProperty("materials_id")
//    private String materialsId;
//
//    /**
//     * 物料名称
//     */
//    @NotNull(message = "物料名称为必填项")
//    @NotEmpty(message = "物料名称不能为空")
//    @Size(max = 50, message = "物料名称长度不能超过50")
//    @JsonProperty("materials_name")
//    private String materialsName;
}

