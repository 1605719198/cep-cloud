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
public class AddProductionPlanRepairDTO {

    @NotNull(message = "计划编号为必须项")
    @NotEmpty(message = "计划编号不能为空")
    @JsonProperty("plan_code")
    private String planCode;

    @NotNull(message = "部门编号为必须项")
    @NotEmpty(message = "部门编号不能为空")
    @JsonProperty("department_id")
    private String departmentId;

    @NotNull(message = "维修类型编号为必须项")
    @JsonProperty("repair_type_id")
    private Integer repairTypeId;

    @NotNull(message = "主体责任人编号为必须项")
    @NotEmpty(message = "主体责任人编号不能为空")
    @JsonProperty("person_main_liable_id")
    private String personMainLiableId;

    @NotNull(message = "主体责任人为必须项")
    @NotEmpty(message = "主体责任人不能为空")
    @JsonProperty("person_main_liable_name")
    private String personMainLiableName;

    @NotNull(message = "专业责任人编号为必须项")
    @NotEmpty(message = "专业责任人编号不能为空")
    @JsonProperty("person_profession_liable_id")
    private String personProfessionLiableId;

    @NotNull(message = "专业责任人为必须项")
    @NotEmpty(message = "专业责任人不能为空")
    @JsonProperty("person_profession_liable_name")
    private String personProfessionLiableName;

    @NotNull(message = "设备编号为必须项")
    @NotEmpty(message = "设备编号不能为空")
    @JsonProperty("equipment_number")
    private String equipmentNumber;

    @NotNull(message = "设备名称为必须项")
    @NotEmpty(message = "设备名称不能为空")
    @JsonProperty("equipment_name")
    private String equipmentName;

    @NotNull(message = "计划开始为必须项")
    @NotEmpty(message = "计划开始不能为空")
    @JsonProperty("plan_start_date")
    private String planStartDate;

    @NotNull(message = "计划结束为必须项")
    @NotEmpty(message = "计划结束不能为空")
    @JsonProperty("plan_end_date")
    private String planEndDate;

    @NotNull(message = "维修内容为必须项")
    @NotEmpty(message = "维修内容不能为空")
    @Size(max = 900, message = "维修内容长度不能超过900")
    @JsonProperty("repair_content")
    private String repairContent;

    @NotNull(message = "是否影响产量为必须项")
    @JsonProperty("is_affect_output")
    private Integer isAffectOutput;

    @NotNull(message = "预提醒天数为必须项")
    @JsonProperty("remind_days")
    private Integer remindDays;

    @NotNull(message = "创建人编号为必须项")
    @NotEmpty(message = "创建人编号不能为空")
    @JsonProperty("create_user_id")
    private String createUserId;

    @NotNull(message = "创建人为必须项")
    @NotEmpty(message = "创建人不能为空")
    @JsonProperty("create_user_name")
    private String createUserName;
}
