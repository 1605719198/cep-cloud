package com.jlkj.product.oi.dto.productionreportlogisticscosts;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-04-17 15:00:13.650137
 */
@Data
public class DeleteProductionReportLogisticsCostsDTO {

    @NotNull(message = "主键标识为必须项")
    @NotEmpty(message = "主键标识不能为空")
    private String id;
}

