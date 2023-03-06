package com.jlkj.material.mr.dto.materialscoalstock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 看板-煤场日库存
 * @author yzl
 * @date  2022年12月8日 15:57:08
 */
@Data
public class GetCoalStockDTO {
    /**
     * 开始日期
     */
    @NotNull(message = "开始日期")
    @NotEmpty(message = "开始日期不能为空")
    @Schema(description = "开始日期")
    @JsonProperty("start_date")
    private String startDate;
}
