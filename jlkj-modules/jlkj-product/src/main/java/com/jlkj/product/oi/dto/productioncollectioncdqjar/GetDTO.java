package com.jlkj.product.oi.dto.productioncollectioncdqjar;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2023-06-05 13:08
 */
@Data
public class GetDTO {

    /** 开始时间 */
    @JsonProperty("start_time")
    private String startTime;

    /** 结束时间 */
    @JsonProperty("end_time")
    private String endTime;
}
