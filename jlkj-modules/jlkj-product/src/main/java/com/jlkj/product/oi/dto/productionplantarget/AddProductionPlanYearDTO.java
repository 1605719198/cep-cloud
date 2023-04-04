package com.jlkj.product.oi.dto.productionplantarget;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zyf
 * @Description
 * @create 2022-04-19 13:24
 */
@Data
public class AddProductionPlanYearDTO {

    @NotNull(message = "计划年度为必须项")
    private Integer planYear;

    @NotNull(message = "计划指标项为必须项")
    private List<TartgetItemsDTO> targetItems;

    @NotNull(message = "userId为必须项")
    @NotEmpty(message = "userId不能为空")
    private String userId;

    @NotNull(message = "userName为必须项")
    @NotEmpty(message = "userName不能为空")
    private String userName;
}
