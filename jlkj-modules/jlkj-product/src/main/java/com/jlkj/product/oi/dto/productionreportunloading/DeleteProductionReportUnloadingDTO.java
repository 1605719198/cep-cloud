package com.jlkj.product.oi.dto.productionreportunloading;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-04-14 11:00:33.379528
 */
@Data
public class DeleteProductionReportUnloadingDTO {

    @NotNull(message = "主键标识为必须项")
    @NotEmpty(message = "主键标识不能为空")
    private String id;
}

