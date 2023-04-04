package com.jlkj.product.oi.swaggerdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zzh
 */

@Data
public class ProductionSteamYieldPerformanceSwagger implements Serializable {

    @Schema(title = "主键标识;UUID")
    private String id ;

    @Schema(title = "锅炉号")
    private String boiler_no ;

    @Schema(title = "班次")
    private String shift ;

    @Schema(title = "班别")
    private String classes ;

    @Schema(title = "时间")
    private String stat_time ;

    @Schema(title = "产量")
    private BigDecimal yield ;
}
