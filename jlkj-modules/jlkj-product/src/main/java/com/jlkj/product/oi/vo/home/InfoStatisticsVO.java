package com.jlkj.product.oi.vo.home;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-首页-统计
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "VO类-生产管理-首页-统计")
public class InfoStatisticsVO {

    /**
     * 全焦率
     */
    @Schema(description = "全焦率")
    @JsonProperty("full_coke_rate")
    private BigDecimal fullCokeRate;

    /**
     * 干熄焦率
     */
    @Schema(description = "干熄焦率")
    @JsonProperty("coke_dry_quenching_rate")
    private BigDecimal cokeDryQuenchingRate;

    /**
     * 蒸汽产率
     */
    @Schema(description = "蒸汽产率")
    @JsonProperty("steam_rate")
    private BigDecimal steamRate;

    /**
     * 吨焦耗煤
     */
    @Schema(description = "吨焦耗煤")
    @JsonProperty("coal_consumption_per_ton")
    private BigDecimal coalConsumptionPerTon;

    /**
     * 吨焦耗电
     */
    @Schema(description = "吨焦耗电")
    @JsonProperty("electric_consumption_per_ton")
    private BigDecimal electricConsumptionPerTon;

}

