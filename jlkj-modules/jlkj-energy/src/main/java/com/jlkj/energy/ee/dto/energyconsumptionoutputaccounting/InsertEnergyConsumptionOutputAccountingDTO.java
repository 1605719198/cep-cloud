package com.jlkj.energy.ee.dto.energyconsumptionoutputaccounting;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO类-能源耗用产出账务信息-新增
 * @author sudeyou
 * @since 2022-09-16 16:10:10
 */
@Data
@Schema(description = "DTO类-能源耗用产出账务信息-新增")
public class InsertEnergyConsumptionOutputAccountingDTO {
    /**
     * 成本中心代号
     */
    @NotNull(message = "成本中心代号为必填项")
    @NotEmpty(message = "成本中心代号不能为空")
    @Size(max = 8, message = "成本中心代号长度不能超过8")
    @Schema(description = "成本中心代号")
    @JsonProperty("cost_center")
    private String costCenter;

    /**
     * 工厂产线代码
     */
    @NotNull(message = "工厂产线代码为必填项")
    @NotEmpty(message = "工厂产线代码不能为空")
    @Size(max = 8, message = "工厂产线代码长度不能超过8")
    @Schema(description = "工厂产线代码")
    @JsonProperty("factory_id")
    private String factoryId;

    /**
     * 产线别代号
     */
    @NotNull(message = "产线别代号为必填项")
    @NotEmpty(message = "产线别代号不能为空")
    @Size(max = 1, message = "产线别代号长度不能超过1")
    @Schema(description = "产线别代号")
    @JsonProperty("mill_id")
    private String millId;

    /**
     * 能源计量日期
     */
    @NotNull(message = "能源计量日期为必填项")
    @NotEmpty(message = "能源计量日期不能为空")
    @Size(max = 8, message = "能源计量日期长度不能超过8")
    @Schema(description = "能源计量日期")
    @JsonProperty("eount_date")
    private String eountDate;

    /**
     * 能源代码
     */
    @NotNull(message = "能源代码为必填项")
    @NotEmpty(message = "能源代码不能为空")
    @Size(max = 10, message = "能源代码长度不能超过10")
    @Schema(description = "能源代码")
    @JsonProperty("engy_id")
    private String engyId;

    /**
     * 能源量类别代号
     */
    @NotNull(message = "能源量类别代号为必填项")
    @NotEmpty(message = "能源量类别代号不能为空")
    @Size(max = 1, message = "能源量类别代号长度不能超过1")
    @Schema(description = "能源量类别代号")
    @JsonProperty("io_type_id")
    private String ioTypeId;

    /**
     * 能源计量单位
     */
    @NotNull(message = "能源计量单位为必填项")
    @NotEmpty(message = "能源计量单位不能为空")
    @Size(max = 8, message = "能源计量单位长度不能超过8")
    @Schema(description = "能源计量单位")
    @JsonProperty("engy_unit_f")
    private String engyUnitF;

    /**
     * 能源量
     */
    @NotNull(message = "能源量为必填项")
    @Schema(description = "能源量")
    @JsonProperty("pro_con_qty")
    private BigDecimal proConQty;

    /**
     * 能源热值
     */
    @NotNull(message = "能源热值为必填项")
    @Schema(description = "能源热值")
    @JsonProperty("cal_value")
    private BigDecimal calValue;

    /**
     * 能量值
     */
    @NotNull(message = "能量值为必填项")
    @Schema(description = "能量值")
    @JsonProperty("engy_value")
    private BigDecimal engyValue;

    /**
     * 卖方成本中心
     */
    @NotNull(message = "卖方成本中心为必填项")
    @NotEmpty(message = "卖方成本中心不能为空")
    @Size(max = 8, message = "卖方成本中心长度不能超过8")
    @Schema(description = "卖方成本中心")
    @JsonProperty("charge_cost_center")
    private String chargeCostCenter;

    /**
     * 卖方工场产线代码
     */
    @NotNull(message = "卖方工场产线代码为必填项")
    @NotEmpty(message = "卖方工场产线代码不能为空")
    @Size(max = 8, message = "卖方工场产线代码长度不能超过8")
    @Schema(description = "卖方工场产线代码")
    @JsonProperty("charge_factory_id")
    private String chargeFactoryId;

}

