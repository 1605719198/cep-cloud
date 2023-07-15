package com.jlkj.product.oi.dto.productionreportliftingcosts;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-04-17 14:57:45.237031
 */
@Data
public class DeleteProductionReportLiftingCostsDTO {

    @NotNull(message = "主键标识为必须项")
    @NotEmpty(message = "主键标识不能为空")
    private String id;
}

