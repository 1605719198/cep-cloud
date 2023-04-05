package com.jlkj.product.oi.vo.productionplanoutputmonth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-指标跟踪-图表-指标项(月实际)
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
@Data
public class ListProductionPlanOutputMonthTargetItemVO {

    /**
     * 月份
     */
    @Schema(description = "月份")
    @JsonProperty("month")
    private Integer month;

    /**
     * 指标项产量
     */
    @Schema(description = "指标项产量")
    @JsonProperty("target_item_output")
    private BigDecimal targetItemOutput;

}

