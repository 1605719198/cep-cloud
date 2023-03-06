package com.jlkj.material.mr.dto.materialsstoragespaces;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-列表-储位
 * @author sudeyou
 */
@Data
public class ListMaterialsStorageSpacesDTO {
    /**
     * 料仓编号
     */
    @NotNull(message = "料仓编号为必填项")
    @Size(max = 36, message = "料仓编号长度不能超过36")
    private String siloId;

}

