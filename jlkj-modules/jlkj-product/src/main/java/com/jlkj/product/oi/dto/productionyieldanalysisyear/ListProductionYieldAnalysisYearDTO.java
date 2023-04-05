package com.jlkj.product.oi.dto.productionyieldanalysisyear;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO类-生产管理-产量跟踪-查询
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "DTO类-生产管理-产量跟踪-查询-年")
public class ListProductionYieldAnalysisYearDTO {

    /**
     * 动态列【前端不需要传】
     */
    @Schema(description = "动态列【前端不需要传】")
    @JsonProperty("cols")
    private String cols;

    /**
     * 动态列【前端不需要传】
     */
    @Schema(description = "动态列【前端不需要传】")
    @JsonProperty("cols_max")
    private String colsMax;

}

