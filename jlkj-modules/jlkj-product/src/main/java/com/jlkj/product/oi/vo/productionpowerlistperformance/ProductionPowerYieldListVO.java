package com.jlkj.product.oi.vo.productionpowerlistperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-首页-发电量
 * @author zzh
 * @since 2022年9月7日15:16:36
 */

@Data
@Schema(description = "VO类-发电实绩-发电量实绩")
public class ProductionPowerYieldListVO {
    /**
     * 汽轮机号
     */
    @Schema(description = "汽轮机号")
    @JsonProperty("turbine_no")
    private String turbineNo ;

    /**
     * 班次
     */
    @Schema(description = "班次")
    @JsonProperty("shift")
    private String shift;

    /**
     * 班别
     */
    @Schema(description = "班别")
    @JsonProperty("classes")
    private String classes ;

    /**
     * 时间
     */
    @Schema(description = "时间")
    @JsonProperty("stat_time")
    private String statTime ;

    /**
     * 产量
     */
    @Schema(description = "产量")
    @JsonProperty("yield")
    private BigDecimal yield ;

}
