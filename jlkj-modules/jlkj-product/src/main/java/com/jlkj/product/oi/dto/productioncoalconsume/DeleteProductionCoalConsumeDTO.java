package com.jlkj.product.oi.dto.productioncoalconsume;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-炼焦煤消耗维护-删除
 *
 * @author sudeyou
 * @since 2022-11-03 17:10:54
 */
@Data
@Schema(description = "DTO类-炼焦煤消耗维护-删除")
public class DeleteProductionCoalConsumeDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

}

