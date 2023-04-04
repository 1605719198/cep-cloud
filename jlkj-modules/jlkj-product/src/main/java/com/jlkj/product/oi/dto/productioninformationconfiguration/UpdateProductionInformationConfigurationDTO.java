package com.jlkj.product.oi.dto.productioninformationconfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO类-生产信息配置-修改
 *
 * @author sudeyou
 * @since 2022-10-18 14:47:44
 */
@Data
@Schema(description = "DTO类-生产信息配置-修改")
public class UpdateProductionInformationConfigurationDTO {
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
     * 熄焦方式(1:干熄;2:湿熄)
     */
    @NotNull(message = "熄焦方式(1:干熄;2:湿熄)为必填项")
    @NotEmpty(message = "熄焦方式(1:干熄;2:湿熄)不能为空")
    @Size(max = 1, message = "熄焦方式(1:干熄;2:湿熄)长度不能超过1")
    @Schema(description = "熄焦方式(1:干熄;2:湿熄)")
    @JsonProperty("quenching_method")
    private String quenchingMethod;

    /**
     * 单孔理论重量
     */
    @NotNull(message = "单孔理论重量为必填项")
    @Schema(description = "单孔理论重量")
    @JsonProperty("single_hole_weight")
    private BigDecimal singleHoleWeight;

    /**
     * 理论单耗
     */
    @NotNull(message = "理论单耗为必填项")
    @Schema(description = "理论单耗")
    @JsonProperty("single_consumption")
    private BigDecimal singleConsumption;

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

