package com.jlkj.product.oi.vo.productioncokeovens;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-水份
 *
 * @author sudeyou
 * @since 2022-11-01 10:10:47
 */
@Data
@Schema(description = "VO类-水份")
public class CoalWaterRateVO {

    /**
     * 水份
     */
    @Schema(description = "水份")
    private String mt;

}

