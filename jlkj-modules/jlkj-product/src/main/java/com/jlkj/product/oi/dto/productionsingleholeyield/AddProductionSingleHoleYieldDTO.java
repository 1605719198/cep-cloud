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
public class AddProductionSingleHoleYieldDTO {

    @NotNull(message = "产量")
    private BigDecimal yield;

    @NotNull(message = "创建人编号为必须项")
    @NotEmpty(message = "创建人编号不能为空")
    @JsonProperty("create_user_id")
    private String createUserId;

    @NotNull(message = "创建人为必须项")
    @NotEmpty(message = "创建人不能为空")
    @JsonProperty("create_user_name")
    private String createUserName;
}
