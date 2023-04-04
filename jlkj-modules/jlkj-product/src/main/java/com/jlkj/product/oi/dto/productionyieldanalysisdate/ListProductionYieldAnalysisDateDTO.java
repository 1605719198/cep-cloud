package com.jlkj.product.oi.dto.productionyieldanalysisdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DTO类-生产管理-产量跟踪-查询
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "DTO类-生产管理-产量跟踪-查询-日")
public class ListProductionYieldAnalysisDateDTO {
    /**
     * 年份
     */
    @NotNull(message = "年份为必填项")
    @Schema(description = "年份")
    @JsonProperty("year")
    private Integer year;

    /**
     * 月份
     */
    @NotNull(message = "月份为必填项")
    @Schema(description = "月份")
    @JsonProperty("month")
    private Integer month;

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

    /**
     * 全焦指标项id【前端不需要传】
     */
    @Schema(description = "全焦指标项id【前端不需要传】")
    @JsonProperty("qj_item_id")
    private String qjItemId;

    /**
     * 冶金焦指标项id【前端不需要传】
     */
    @Schema(description = "冶金焦指标项id【前端不需要传】")
    @JsonProperty("yjj_item_id")
    private String yjjItemId;

}

