package com.jlkj.product.oi.dto.productioncokeovens;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-每日推焦炉数维护-新增
 *
 * @author sudeyou
 * @since 2022-11-01 10:31:58
 */
@Data
@Schema(description = "DTO类-每日推焦炉数维护-新增")
public class InsertProductionCokeOvensDTO {
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
     * 日推焦总炉数
     */
    @NotNull(message = "日推焦总炉数为必填项")
    @Schema(description = "日推焦总炉数")
    @JsonProperty("out_num")
    private Integer outNum;

    /**
     * 日推焦干熄炉数
     */
    @NotNull(message = "日推焦干熄炉数为必填项")
    @Schema(description = "日推焦干熄炉数")
    @JsonProperty("dry_out_num")
    private Integer dryOutNum;

    /**
     * 日推焦湿熄炉数
     */
    @NotNull(message = "日推焦湿熄炉数为必填项")
    @Schema(description = "日推焦湿熄炉数")
    @JsonProperty("wet_out_num")
    private Integer wetOutNum;

    /**
     * 创建人id
     */
    @NotNull(message = "创建人id为必填项")
    @NotEmpty(message = "创建人id不能为空")
    @Size(max = 36, message = "创建人id长度不能超过36")
    @Schema(description = "创建人id")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人
     */
    @NotNull(message = "创建人为必填项")
    @NotEmpty(message = "创建人不能为空")
    @Size(max = 50, message = "创建人长度不能超过50")
    @Schema(description = "创建人")
    @JsonProperty("create_user_name")
    private String createUserName;

}

