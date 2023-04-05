package com.jlkj.product.oi.vo.productionplanconfigcoke;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-配煤计划主记录-计划编号
 *
 * @author sudeyou
 * @since 2022-11-07 19:37:11
 */
@Data
@Schema(description = "VO类-配煤计划主记录-计划编号")
public class PlanNumberVO {
    /**
     * 计划编号
     */
    @Schema(description = "计划编号")
    @JsonProperty("plan_number")
    private String planNumber;

}
