package com.jlkj.product.oi.dto.productionplanrepair;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class DeleteProductionPlanRepairDTO {

    @NotNull(message = "计划id为必须项")
    @NotEmpty(message = "计划id不能为空")
    private String id;

    @NotNull(message = "删除人编号为必须项")
    @NotEmpty(message = "删除人编号不能为空")
    private String deleteUserId;

    @NotNull(message = "删除人为必须项")
    @NotEmpty(message = "删除人不能为空")
    private String deleteUserName;
}
