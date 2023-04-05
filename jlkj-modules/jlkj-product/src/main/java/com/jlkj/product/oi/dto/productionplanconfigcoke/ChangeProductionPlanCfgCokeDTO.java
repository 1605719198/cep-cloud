package com.jlkj.product.oi.dto.productionplanconfigcoke;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-手动切配煤计划-计划管理--配煤计划主表
 * @author yzl
 */
@Data
public class ChangeProductionPlanCfgCokeDTO {
    /**
     * 主键标识;UUID
     */
    @NotNull(message = "主键标识;UUID为必填项")
    @NotEmpty(message = "主键标识;UUID不能为空")
    @Size(max = 36, message = "主键标识;UUID长度不能超过36")
    @JsonProperty("id")
    private String id;





}
