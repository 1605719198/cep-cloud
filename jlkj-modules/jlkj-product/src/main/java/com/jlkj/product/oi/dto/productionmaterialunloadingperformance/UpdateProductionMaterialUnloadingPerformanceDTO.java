package com.jlkj.product.oi.dto.productionmaterialunloadingperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-05-10 15:50
 */
@Data
public class UpdateProductionMaterialUnloadingPerformanceDTO {

    @NotEmpty
    @JsonProperty("id")
    private String id;

    @NotEmpty
    @JsonProperty("storage_maintain_id")
    private String storageSpacesId;

    @NotEmpty
    @JsonProperty("storage_maintain_space_name")
    private String storageSpacesName;

    @NotEmpty
    @JsonProperty("unloading_time")
    private String unloadingTime;

    @NotNull(message = "userId为必须项")
    @NotEmpty(message = "userId不能为空")
    @JsonProperty("modify_user_id")
    private String userId;

    @NotNull(message = "userName为必须项")
    @NotEmpty(message = "userName不能为空")
    @JsonProperty("modify_user_name")
    private String userName;
}
