package com.jlkj.product.oi.dto.productioncokeovenparameterstandard;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class DeleteProductionCokeOvenParameterStandardDTO {

    @NotNull(message = "主键标识为必须项")
    @NotEmpty(message = "主键标识不能为空")
    private String id;

}
