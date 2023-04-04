package com.jlkj.product.oi.dto.productioncoefficientrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlkj.product.oi.dto.BasePage;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-08-11 14:00
 */
@Data
public class GetProductionCoefficientRecordDTO extends BasePage {

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("shift_name")
    private String shiftName;

    @JsonProperty("class_name")
    private String className;
}
