package com.jlkj.material.mr.dto.materialssiloareahistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-新增-料仓区域历史表
 * @author sudeyou
 */
@Data
public class AddMaterialsSiloAreaHistoryDTO {
    /**
     * 料仓编号
     */
    @NotNull(message = "料仓编号为必填项")
    @NotEmpty(message = "料仓编号不能为空")
    @Size(max = 36, message = "料仓编号长度不能超过36")
    @JsonProperty("silo_id")
    private String siloId;

    /**
     * 储位编号
     */
    @NotNull(message = "储位编号为必填项")
    @NotEmpty(message = "储位编号不能为空")
    @Size(max = 36, message = "储位编号长度不能超过36")
    @JsonProperty("storage_spaces_id")
    private String storageSpacesId;

    /**
     * 物料编号
     */
    @NotNull(message = "物料编号为必填项")
    @NotEmpty(message = "物料编号不能为空")
    @Size(max = 36, message = "物料编号长度不能超过36")
    @JsonProperty("materials_id")
    private String materialsId;

    /**
     * 物料名称
     */
    @NotNull(message = "物料名称为必填项")
    @NotEmpty(message = "物料名称不能为空")
    @Size(max = 50, message = "物料名称长度不能超过50")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 开始时间
     */
    @JsonProperty("start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间为必填项")
    @NotEmpty(message = "结束时间不能为空")
    @JsonProperty("end_time")
    private String endTime;

}

