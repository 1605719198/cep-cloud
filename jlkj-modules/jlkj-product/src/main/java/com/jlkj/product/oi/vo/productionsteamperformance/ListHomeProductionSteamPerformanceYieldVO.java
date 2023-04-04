package com.jlkj.product.oi.vo.productionsteamperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-首页-蒸汽产量
 * @author sudeyou
 * @since 2022-09-01 09:40:55
 */
@Data
@Schema(description = "VO类-首页-蒸汽产量")
public class ListHomeProductionSteamPerformanceYieldVO {
    /**
     * 锅炉号
     */
    @Schema(description = "锅炉号")
    @JsonProperty("boiler_no")
    private String boilerNo;

    /**
     * 产量
     */
    @Schema(description = "产量")
    @JsonProperty("yield")
    private BigDecimal yield;

}

