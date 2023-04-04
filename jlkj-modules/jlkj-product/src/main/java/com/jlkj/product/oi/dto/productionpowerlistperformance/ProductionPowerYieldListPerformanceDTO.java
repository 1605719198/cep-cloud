package com.jlkj.product.oi.dto.productionpowerlistperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlkj.product.oi.dto.BasePage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO类-发电实绩-发电量实绩
 * @author zzh
 * @since 2022年9月7日15:46:06
 */

@Data
@Schema(description = "DTO类-发电实绩-发电量实绩")
public class ProductionPowerYieldListPerformanceDTO extends BasePage {

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期")
    @NotEmpty(message = "开始日期不能为空")
    @Schema(description = "开始日期")
    @JsonProperty("start_date")
    private String startDate ;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期")
    @NotEmpty(message = "结束日期不能为空")
    @Schema(description = "结束日期")
    @JsonProperty("end_date")
    private String endDate ;

    /**
     * 班别
     */
    @NotNull(message = "班别")
    @NotEmpty(message = "班别不能为空")
    @Schema(description = "班别")
    @JsonProperty("classes")
    private String classes ;

    /**
     * 班次
     */
    @NotNull(message = "班次")
    @NotEmpty(message = "班次不能为空")
    @Schema(description = "班次")
    @JsonProperty("shift")
    private String shift ;
}
