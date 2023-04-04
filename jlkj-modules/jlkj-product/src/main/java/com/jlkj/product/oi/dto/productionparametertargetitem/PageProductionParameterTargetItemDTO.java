package com.jlkj.product.oi.dto.productionparametertargetitem;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class PageProductionParameterTargetItemDTO {

    @NotNull(message = "指标类型id为必须项")
    @NotEmpty(message = "指标类型id不能为空")
    private String targetItemTypeId;

    @NotNull(message = "current为必须项")
    @Min(value = 1, message = "current不能小于1")
    private Long current;

    @NotNull(message = "size为必须项")
    @Min(value = 1, message = "size不能小于1")
    private Long size;

    @NotNull(message = "order为必须项")
    @NotEmpty(message = "order不能为空")
    private String order;

    @NotNull(message = "orderby为必须项")
    private String orderby;
}
