package com.jlkj.product.oi.vo.productionrealoutputmonth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-指标跟踪-图表-日期(同环比)
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
@Data
public class ListProductionRealOutputMonthRangeVO {

    /**
     * 指标项名称
     */
    @Schema(description = "指标项名称")
    @JsonProperty("target_item_name")
    private String targetItemName;

    /**
     * 指标项产量
     */
    @Schema(description = "指标项产量")
    @JsonProperty("target_item_output")
    private BigDecimal targetItemOutput;

}

