package com.jlkj.product.oi.dto.productioncokeitemyield;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlkj.product.oi.dto.BasePage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zzh
 * 焦炭分项产量查询DTO
 * @time 2022年9月9日10:46:00
 */

@Data
@Schema(description = "DTO类-炼焦实绩-焦炭分项产量")
public class CokeItemYieldQueryDTO extends BasePage {

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    @JsonProperty("start_time")
    private String startTime ;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    @Schema(description = "结束时间")
    @JsonProperty("end_time")
    private String endTime ;

    /**
     * 物料代码
     */
    @NotNull(message = "物料代码不能为空")
    @Schema(description = "物料代码")
    @JsonProperty("materials_code")
    private String materialsCode ;

}
