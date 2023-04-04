package com.jlkj.product.oi.vo.productionplanoutputdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-指标跟踪-图表-指标项(日实际)
 * @author sudeyou
 * @since 2022-07-13 14:48:56
 */
@Data
@Schema(description = "VO类-生产管理-指标跟踪-图表-指标项(日实际)")
public class ListProductionPlanOutputDateTargetItemVO {

    /**
     * 日期
     */
    @Schema(description = "日期")
    @JsonProperty("date")
    private Integer date;

    /**
     * 指标项产量
     */
    @Schema(description = "指标项产量")
    @JsonProperty("target_item_output")
    private BigDecimal targetItemOutput;

}

