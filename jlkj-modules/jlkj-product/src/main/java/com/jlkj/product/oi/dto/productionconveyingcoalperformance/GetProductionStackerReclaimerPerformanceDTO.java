package com.jlkj.product.oi.dto.productionconveyingcoalperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlkj.product.oi.dto.BasePage;
import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-05-11 9:48
 */
@Data
public class GetProductionStackerReclaimerPerformanceDTO extends BasePage {

    @JsonProperty("shift_name")
    private String shiftName;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;
}
