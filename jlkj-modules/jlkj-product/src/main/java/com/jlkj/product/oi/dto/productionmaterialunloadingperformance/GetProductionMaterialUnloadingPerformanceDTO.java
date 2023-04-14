package com.jlkj.product.oi.dto.productionmaterialunloadingperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlkj.product.oi.dto.BasePage;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author zyf
 * @Description
 * @create 2022-05-10 8:47
 */
@Data
public class GetProductionMaterialUnloadingPerformanceDTO extends BasePage {

    private String shiftName;

    private String className;

    private String unloadingStart;

    private String unloadingEnd;

    private String shippingMethod;

    private String materialCode;

    @Min(value = 0)
    @Max(value = 2)
    private Integer isDone;
}
