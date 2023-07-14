package com.jlkj.product.oi.dto.productionreportliftingcosts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzl
 * @create 2023-04-17 14:57:45.237031
 */
@Data
public class UpdateProductionReportLiftingCostsDTO {

    @NotNull(message = "id为必须项")
    @JsonProperty("id")
    private String id ;

    @NotNull(message = "统计日期为必须项")
    @JsonProperty("statistics_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statisticsDate ;

    @NotNull(message = "作业区为必须项")
    @JsonProperty("area_name")
    private String areaName ;

    @NotNull(message = "车型为必须项")
    @JsonProperty("car_type")
    private String carType ;

    @NotNull(message = "吨数(t)为必须项")
    @JsonProperty("tonnes")
    private BigDecimal tonnes ;

    @NotNull(message = "当日费用(元)为必须项")
    @JsonProperty("day_cost")
    private BigDecimal dayCost ;

    @NotNull(message = "用车时长(h)为必须项")
    @JsonProperty("car_time")
    private Integer carTime ;

    @NotNull(message = "修改人id为必须项")
    @JsonProperty("modify_user_id")
    private String modifyUserId ;

    @NotNull(message = "修改人为必须项")
    @JsonProperty("modify_user_name")
    private String modifyUserName ;

}

