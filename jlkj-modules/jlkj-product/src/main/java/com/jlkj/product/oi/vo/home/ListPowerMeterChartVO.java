package com.jlkj.product.oi.vo.home;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-首页-发电仪表图表
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "VO类-生产管理-首页-发电仪表图表")
public class ListPowerMeterChartVO {

    /**
     * 汽轮机号
     */
    @Schema(description = "汽轮机号")
    @JsonProperty("turbine_no")
    private String turbineNo;

    /**
     * 产量
     */
    @Schema(description = "产量")
    @JsonProperty("yield")
    private BigDecimal yield;

    /**
     * 汽耗量
     */
    @Schema(description = "汽耗量")
    @JsonProperty("steam")
    private BigDecimal steam;

}

