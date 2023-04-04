package com.jlkj.product.oi.dto.productioncokeovenparameterstandard;

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
public class AddProductionCokeOvenParameterStandardDTO {

    @NotNull(message = "规定装煤量必须项")
    @JsonProperty("standard_coal_loading")
    private BigDecimal standardCoalLoading;

    @NotNull(message = "规定结焦时间为必须项")
    @JsonProperty("coking_cycle")
    private BigDecimal cokingCycle;

    @NotNull(message = "焦侧标准火道温度为必须项")
    @JsonProperty("fire_temperature")
    private BigDecimal fireTemperature;

    @NotNull(message = "直行标准温度为必须项")
    @JsonProperty("zhi_hang_temperature")
    private BigDecimal zhiHangTemperature;

    @NotNull(message = "K装正负为必须项")
    @JsonProperty("coal_loading_factor")
    private BigDecimal coalLoadingFactor;

    @NotNull(message = "K计正负为必须项")
    @JsonProperty("planning_factor")
    private BigDecimal planningFactor;

    @NotNull(message = "K均正负为必须项")
    @JsonProperty("average_coefficient")
    private BigDecimal averageCoefficient;

    @NotNull(message = "K安正负为必须项")
    @JsonProperty("stability_factor")
    private BigDecimal stabilityFactor;

    @NotNull(message = "创建人编号为必须项")
    @NotEmpty(message = "创建人编号不能为空")
    @JsonProperty("create_user_id")
    private String createUserId;

    @NotNull(message = "创建人为必须项")
    @NotEmpty(message = "创建人不能为空")
    @JsonProperty("create_user_name")
    private String createUserName;
}
