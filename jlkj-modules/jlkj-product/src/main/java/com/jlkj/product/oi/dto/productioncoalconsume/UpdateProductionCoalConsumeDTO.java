package com.jlkj.product.oi.dto.productioncoalconsume;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-炼焦煤消耗维护-修改
 *
 * @author sudeyou
 * @since 2022-11-03 17:10:54
 */
@Data
@Schema(description = "DTO类-炼焦煤消耗维护-修改")
public class UpdateProductionCoalConsumeDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @Schema(description = "主键标识;UUID")
    @JsonProperty("id")
    private String id;

    /**
     * 账务日期
     */
    @NotNull(message = "账务日期为必填项")
    @NotEmpty(message = "账务日期不能为空")
    @Schema(description = "账务日期")
    @JsonProperty("account_date")
    private String accountDate;

    /**
     * 物料代码
     */
    @NotNull(message = "物料代码为必填项")
    @NotEmpty(message = "物料代码不能为空")
    @Size(max = 36, message = "物料代码长度不能超过36")
    @Schema(description = "物料代码")
    @JsonProperty("materials_code")
    private String materialsCode;

    /**
     * 物料名称
     */
    @NotNull(message = "物料名称为必填项")
    @NotEmpty(message = "物料名称不能为空")
    @Size(max = 90, message = "物料名称长度不能超过90")
    @Schema(description = "物料名称")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 煤消耗量(t)
     */
    @NotNull(message = "煤消耗量(t)为必填项")
    @Schema(description = "煤消耗量(t)")
    @JsonProperty("consumption")
    private Integer consumption;

    /**
     * 修改人id
     */
    @NotNull(message = "修改人id为必填项")
    @NotEmpty(message = "修改人id不能为空")
    @Size(max = 36, message = "修改人id长度不能超过36")
    @Schema(description = "修改人id")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    /**
     * 修改人
     */
    @NotNull(message = "修改人为必填项")
    @NotEmpty(message = "修改人不能为空")
    @Size(max = 50, message = "修改人长度不能超过50")
    @Schema(description = "修改人")
    @JsonProperty("modify_user_name")
    private String modifyUserName;

}

