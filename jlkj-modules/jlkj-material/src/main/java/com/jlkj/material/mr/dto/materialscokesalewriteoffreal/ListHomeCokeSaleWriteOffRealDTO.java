package com.jlkj.material.mr.dto.materialscokesalewriteoffreal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * DTO类-首页-焦碳库存
 * @author yzl
 * @since 2022年12月8日 09:12:19
 */
@Data
@Schema(description = "DTO类-首页-焦炭外发")
public class ListHomeCokeSaleWriteOffRealDTO {
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
    @NotNull(message = "结束日期为必填项")
    @NotEmpty(message = "结束日期不能为空")
    @Schema(description = "结束日期")
    @JsonProperty("end_date")
    private String endDate;
}
