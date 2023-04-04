package com.jlkj.product.oi.dto.productionparametertargetitem;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-04-19 8:54
 */
@Data
public class GetProductionParameterTargetItemDTO {

    @NotNull
    @Min(0)
    @Max(2)
    private Integer targetItemTypeId;
}
