package com.jlkj.product.oi.dto.productionplantarget;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-04-20 8:59
 */
@Data
public class GetProductionPlanMonthDTO {

    @NotNull(message = "计划年度为必须项")
    private Integer planYear;
}
