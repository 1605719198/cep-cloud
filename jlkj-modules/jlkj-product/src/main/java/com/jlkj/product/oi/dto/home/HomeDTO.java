package com.jlkj.product.oi.dto.home;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-生产看板
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "DTO类-生产看板")
public class HomeDTO {

    /**
     * 开始日期
     */
    @Schema(description = "开始日期")
    @NotNull(message = "开始日期为必填项")
    @NotEmpty(message = "开始日期不能为空")
    @Size(max = 50, message = "开始日期长度不能超过50")
    @JsonProperty("start_date")
    private String startDate;

    /**
     * 结束日期
     */
    @Schema(description = "结束日期")
    @NotNull(message = "结束日期为必填项")
    @NotEmpty(message = "结束日期不能为空")
    @Size(max = 50, message = "结束日期长度不能超过50")
    @JsonProperty("end_date")
    private String endDate;
}

