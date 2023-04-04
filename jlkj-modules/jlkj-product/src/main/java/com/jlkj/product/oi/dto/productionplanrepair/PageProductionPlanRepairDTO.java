package com.jlkj.product.oi.dto.productionplanrepair;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class PageProductionPlanRepairDTO {

    @NotNull(message = "部门编号为必须项")
    @JsonProperty("department_id")
    private String departmentId;

    @NotNull(message = "设备编号为必须项")
    @JsonProperty("equipment_number")
    private String equipmentNumber;

    @NotNull(message = "开始日期为必须项")
    @JsonProperty("start_date")
    private String startDate;

    @NotNull(message = "结束日期为必须项")
    @JsonProperty("end_date")
    private String endDate;

    @NotNull(message = "维修内容为必须项")
    @JsonProperty("repair_content")
    private String repairContent;

    @NotNull(message = "计划完成状态为必须项")
    @JsonProperty("plan_state")
    private Integer planState;

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
