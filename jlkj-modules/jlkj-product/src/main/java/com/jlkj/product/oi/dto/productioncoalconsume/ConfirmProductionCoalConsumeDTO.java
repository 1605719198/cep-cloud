package com.jlkj.product.oi.dto.productioncoalconsume;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO类-炼焦煤消耗维护-确认
 *
 * @author sudeyou
 * @since 2022-10-19 11:07:13
 */
@Data
@Schema(description = "DTO类-炼焦煤消耗维护-确认")
public class ConfirmProductionCoalConsumeDTO {
    /**
     * 账务日期
     */
    @NotNull(message = "账务日期为必填项")
    @NotEmpty(message = "账务日期不能为空")
    @Schema(description = "账务日期")
    @JsonProperty("account_date")
    private String accountDate;

}

