package com.jlkj.material.mr.dto.materialssiloareahistory;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-分页-料仓区域历史
 * @author sudeyou
 */
@Data
public class PageMaterialsSiloAreaHistoryDTO {
    /**
     * 料仓编号
     */
    @NotNull(message = "料仓编号为必填项")
    @NotEmpty(message = "料仓编号不能为空")
    @Size(max = 36, message = "料仓编号长度不能超过36")
    private String siloId;

    /**
     * 储位编号
     */
    @NotNull(message = "储位编号为必填项")
    @NotEmpty(message = "储位编号不能为空")
    @Size(max = 36, message = "储位编号长度不能超过36")
    private String storageSpacesId;

    /**
     * 当前页
     */
    @NotNull(message = "当前页current为必填项")
    @Min(value = 1, message = "当前页current不能小于1")
    private Long current;

    /**
     * 每页行数
     */
    @NotNull(message = "每页行数size为必填项")
    @Min(value = 1, message = "每页行数size不能小于1")
    private Long size;

}

