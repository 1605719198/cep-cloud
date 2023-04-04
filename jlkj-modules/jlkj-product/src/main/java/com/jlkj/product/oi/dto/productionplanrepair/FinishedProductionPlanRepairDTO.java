package com.jlkj.product.oi.dto.productionplanrepair;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class FinishedProductionPlanRepairDTO {

    @NotNull(message = "计划id为必须项")
    @NotEmpty(message = "计划id不能为空")
    private String id;

    @NotNull(message = "实际开始时间为必须项")
    @NotEmpty(message = "实际开始时间不能为空")
    @JsonProperty("real_start_date")
    private String realStartDate;

    @NotNull(message = "实际完成时间为必须项")
    @NotEmpty(message = "实际完成时间不能为空")
    @JsonProperty("real_end_date")
    private String realEndDate;

    @NotNull(message = "完成操作人id为必须项")
    @NotEmpty(message = "完成操作人id不能为空")
    @JsonProperty("complete_user_id")
    private String completeUserId;

    @NotNull(message = "完成操作人姓名为必须项")
    @NotEmpty(message = "完成操作人姓名不能为空")
    @JsonProperty("complete_user_name")
    private String completeUserName;

    @Size(max = 900, message = "计划完成说明长度不能超过900")
    @JsonProperty("remark")
    private String remark;
}
