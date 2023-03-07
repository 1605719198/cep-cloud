package com.jlkj.material.mr.dto.materialsstoragespaces;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-分页-储位维护
 * @author sudeyou
 */
@Data
public class PageMaterialsStorageSpacesDTO {
    /**
     * 料仓编号
     */
    @NotNull(message = "料仓编号为必填项")
    @Size(max = 36, message = "料仓编号长度不能超过36")
    private String siloId;

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

    /**
     * 排序字段
     */
    @NotNull(message = "排序字段order为必填项")
    @NotEmpty(message = "排序字段order不能为空")
    private String order;

    /**
     * 排序规则
     */
    @NotNull(message = "排序规则orderby为必填项")
    private String orderby;
}

