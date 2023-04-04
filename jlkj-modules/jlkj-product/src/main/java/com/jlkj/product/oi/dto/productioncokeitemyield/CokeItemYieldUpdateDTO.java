package com.jlkj.product.oi.dto.productioncokeitemyield;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zzh
 * 焦炭分项产量 修改的DTO
 * @time 2022年9月9日10:51:18
 */
@Data
@Schema(description = "DTO类-炼焦实绩-焦炭分项产量")
public class CokeItemYieldUpdateDTO {

    /**
     * uuid
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    @JsonProperty("id")
    private String id ;

    @NotNull(message = "物料名称不能为空")
    @Schema(description = "物料名称")
    @JsonProperty("materials_name")
    private String materialsName ;

    @NotNull(message = "物料名称不能为空")
    @Schema(description = "物料名称")
    @JsonProperty("old_materials_name")
    private String oldMaterialsName ;


    @NotNull(message = "物料代码不能为空")
    @Schema(description = "物料代码")
    @JsonProperty("materials_code")
    private String materialsCode ;

    @NotNull(message = "物料代码不能为空")
    @Schema(description = "物料代码")
    @JsonProperty("old_materials_code")
    private String oldMaterialsCode ;


    /**
     * 产量
     */
    @NotNull(message = "产量不能为空")
    @Schema(description = "产量")
    @JsonProperty("yield")
    private BigDecimal yield ;

    /**
     * 修改之前的产量
     */
    @NotNull(message = "修改之前的产量不能为空")
    @Schema(description = "修改之前的产量")
    @JsonProperty("old_yield")
    private BigDecimal oldYield ;

    /**
     * 时间
     */
    @NotNull(message = "时间不能为空")
    @Schema(description = "时间")
    @JsonProperty("time")
    private String time ;

    /**
     * 时间
     */
    @NotNull(message = "修改之前的时间不能为空")
    @Schema(description = "修改之前的时间")
    @JsonProperty("old_time")
    private String oldTime ;
}
