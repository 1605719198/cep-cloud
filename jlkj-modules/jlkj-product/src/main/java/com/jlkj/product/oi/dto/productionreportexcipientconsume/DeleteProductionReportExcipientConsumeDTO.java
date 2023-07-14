package com.jlkj.product.oi.dto.productionreportexcipientconsume;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-04-13 15:00:09.782148
 */
@Data
public class DeleteProductionReportExcipientConsumeDTO {

    @NotNull(message = "主键标识为必须项")
    @NotEmpty(message = "主键标识不能为空")
    private String id;
}

