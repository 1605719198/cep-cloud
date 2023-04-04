package com.jlkj.product.oi.dto.productionconveyingcoalrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-生产实绩-上煤记录-删除
 *
 * @author sudeyou
 * @since 2022-11-17 15:18:05
 */
@Data
@Schema(description = "DTO类-生产实绩-上煤记录-删除")
public class DeleteProductionConveyingCoalRecordDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

    @NotNull(message = "删除人编号为必须项")
    @NotEmpty(message = "删除人编号不能为空")
    @JsonProperty("delete_user_id")
    private String deleteUserId;

    @NotNull(message = "删除人为必须项")
    @NotEmpty(message = "删除人不能为空")
    @JsonProperty("delete_user_name")
    private String deleteUserName;

}
