package com.jlkj.product.oi.dto.logmaintain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlkj.product.oi.dto.BasePage;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-10-10 9:17
 */
@Data
public class GetDto extends BasePage {

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("item_id")
    private String itemId;
}
