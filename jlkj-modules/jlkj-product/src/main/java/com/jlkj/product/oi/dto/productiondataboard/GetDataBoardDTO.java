package com.jlkj.product.oi.dto.productiondataboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 看板
 *
 * @author yzl
 * @date 2023年2月23日 13:33:45
 */
@Data
public class GetDataBoardDTO {

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期")
    @NotEmpty(message = "开始日期不能为空")
    @Schema(description = "开始日期")
    @JsonProperty("start_date")
    private String startDate;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期")
    @NotEmpty(message = "结束日期不能为空")
    @Schema(description = "结束日期")
    @JsonProperty("end_date")
    private String endDate;

}
