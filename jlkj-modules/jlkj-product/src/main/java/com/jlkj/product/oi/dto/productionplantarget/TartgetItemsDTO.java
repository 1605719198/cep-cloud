package com.jlkj.product.oi.dto.productionplantarget;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zyf
 * @Description
 * @create 2022-04-29 8:16
 */
@Data
public class TartgetItemsDTO {
    private String id;

    private BigDecimal value;

    private Integer calcUnitId;
}
