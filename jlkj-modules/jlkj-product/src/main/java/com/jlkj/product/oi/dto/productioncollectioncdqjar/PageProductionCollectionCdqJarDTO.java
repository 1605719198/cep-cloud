package com.jlkj.product.oi.dto.productioncollectioncdqjar;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2023-05-30 10:01:57.793328
 */
@Data
public class PageProductionCollectionCdqJarDTO {

    /** 开始时间 */
    @JsonProperty("start_time")
    private String startTime;

    /** 结束时间 */
    @JsonProperty("end_time")
    private String endTime;

    @NotNull(message = "current为必须项")
    @Min(value = 1, message = "current不能小于1")
    private Long current;

    @NotNull(message = "size为必须项")
    @Min(value = 1, message = "size不能小于1")
    private Long size;

    @NotNull(message = "order为必须项")
    @NotEmpty(message = "order不能为空")
    private String order;

    @NotNull(message = "orderby为必须项")
    private String orderby;
}

