package com.jlkj.product.oi.dto.productionsingleholeyield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class UpdateProductionSingleHoleYieldDTO {

    @NotNull(message = "主键标识为必须项")
    @NotEmpty(message = "主键标识不能为空")
    private String id;

    @NotNull(message = "产量")
    private BigDecimal yield;

    @NotNull(message = "修改人编号为必须项")
    @NotEmpty(message = "修改人编号不能为空")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    @NotNull(message = "修改人为必须项")
    @NotEmpty(message = "修改人不能为空")
    @JsonProperty("modify_user_name")
    private String modifyUserName;
}
