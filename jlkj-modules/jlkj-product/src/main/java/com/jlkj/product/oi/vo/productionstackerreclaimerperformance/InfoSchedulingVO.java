package com.jlkj.product.oi.vo.productionstackerreclaimerperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-获取排班信息
 *
 * @author sudeyou
 * @since 2022-11-10 09:11:08
 */
@Data
@Schema(description = "VO类-获取排班信息")
public class InfoSchedulingVO {
    /**
     * 班次
     */
    @Schema(description = "班次")
    @JsonProperty("shift")
    private String shift;

    /**
     * 班别
     */
    @Schema(description = "班别")
    @JsonProperty("class_type")
    private String classType;

}
