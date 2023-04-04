package com.jlkj.product.oi.dto.productioncokeovens;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO类-每日推焦炉数维护-账务统计
 *
 * @author sudeyou
 * @since 2022-10-18 14:50:42
 */
@Data
@Schema(description = "DTO类-每日推焦炉数维护-账务统计")
public class ListProductionCokeOvensAccountDTO {
    /**
     * 账务日期
     */
    @NotNull(message = "账务日期为必填项")
    @NotEmpty(message = "账务日期不能为空")
    @Schema(description = "账务日期")
    @JsonProperty("account_date")
    private String accountDate;
}

