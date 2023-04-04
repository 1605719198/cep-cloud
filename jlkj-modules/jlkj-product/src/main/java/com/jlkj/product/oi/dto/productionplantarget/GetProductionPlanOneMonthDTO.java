package com.jlkj.product.oi.dto.productionplantarget;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-04-20 9:55
 */
@Data
public class GetProductionPlanOneMonthDTO {

    @NotNull(message = "计划年度为必须项")
    private Integer planYear;

    @NotNull(message = "月份为必须项")
    private Integer planMonth;
}
