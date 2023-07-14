package com.jlkj.product.oi.dto.productionreportoilconsume;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-04-13 13:27:01.939087
 */
@Data
public class DeleteProductionReportOilConsumeDTO {

    @NotNull(message = "主键标识为必须项")
    @NotEmpty(message = "主键标识不能为空")
    private String id;
}

