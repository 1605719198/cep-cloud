package com.jlkj.material.mr.vo.materialscoalstock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-首页-物料分析
 * @author sudeyou
 * @since 2022-08-16 16:07:53
 */
@Data
@Schema(description = "VO类-首页-物料分析")
public class ListHomeMaterialAnalysisListVO {
    /**
     * 物料名称
     */
    @Schema(description = "物料名称")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 库存
     */
    @Schema(description = "库存")
    @JsonProperty("inventory")
    private String inventory;

    /**
     * 周转率
     */
    @Schema(description = "周转率")
    @JsonProperty("turnover_rate")
    private String turnoverRate;

    /**
     * 入库量
     */
    @Schema(description = "入库量")
    @JsonProperty("warehousing_volume")
    private BigDecimal warehousingVolume;

}

