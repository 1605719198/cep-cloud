package com.jlkj.product.oi.dto.productionplantarget;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-04-20 8:35
 */
@Data
public class DeleteProductionPlanYearDTO {

    @NotNull(message = "计划年度为必须项")
    private Integer planYear;

    @NotNull(message = "删除人编号为必须项")
    @NotEmpty(message = "删除人编号不能为空")
    private String deleteUserId;

    @NotNull(message = "删除人为必须项")
    @NotEmpty(message = "删除人不能为空")
    private String deleteUserName;
}
