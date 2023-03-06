package com.jlkj.material.mr.vo.materialscoaldaystock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-首页-原煤库存
 * @author sudeyou
 * @since 2022-09-02 10:55:24
 */
@Data
@Schema(description = "VO类-首页-原煤库存")
public class ListHomeCoalStockVO {
    /**
     * 库存量
     */
    @Schema(description = "库存量")
    @JsonProperty("inventory")
    private String inventory;

    /**
     * 入库量
     */
    @Schema(description = "入库量")
    @JsonProperty("material_weight")
    private String materialWeight;

    /**
     * 日期
     */
    @Schema(description = "日期")
    @JsonProperty("stat_date")
    private String statDate;

}

