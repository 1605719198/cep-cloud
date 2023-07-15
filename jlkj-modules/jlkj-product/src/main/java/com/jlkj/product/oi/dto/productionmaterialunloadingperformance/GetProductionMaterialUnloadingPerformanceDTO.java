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

    @JsonProperty("shift_name")
    private String shiftName;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("time_start")
    private String unloadingStart;

    @JsonProperty("time_end")
    private String unloadingEnd;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("material_code")
    private String materialCode;

    @JsonProperty("supplier_name")
    private String supplierName;

    @Min(value = 0)
    @Max(value = 2)
    @JsonProperty("is_done")
    private Integer isDone;
}
