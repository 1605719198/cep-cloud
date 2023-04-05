package com.jlkj.product.oi.dto.productionconveyingcoalrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-生产实绩-上煤记录-新增
 *
 * @author sudeyou
 * @since 2022-11-17 15:18:05
 */
@Data
@Schema(description = "DTO类-生产实绩-上煤记录-新增")
public class InsertProductionConveyingCoalRecordDTO {
    /**
     * 班次代码
     */
    @NotNull(message = "班次代码为必填项")
    @NotEmpty(message = "班次代码不能为空")
    @Size(max = 36, message = "班次代码长度不能超过36")
    @Schema(description = "班次代码")
    @JsonProperty("shift_name")
    private String shiftName;

    /**
     * 班别代码
     */
    @NotNull(message = "班别代码为必填项")
    @NotEmpty(message = "班别代码不能为空")
    @Size(max = 36, message = "班别代码长度不能超过36")
    @Schema(description = "班别代码")
    @JsonProperty("class_name")
    private String className;

    /**
     * 配煤计划id
     */
    @NotNull(message = "配煤计划id为必填项")
    @NotEmpty(message = "配煤计划id不能为空")
    @Size(max = 36, message = "配煤计划id长度不能超过36")
    @Schema(description = "配煤计划id")
    @JsonProperty("config_coke_plan_id")
    private String configCokePlanId;

    /**
     * 焦炭等级料号
     */
    @NotNull(message = "焦炭等级料号为必填项")
    @NotEmpty(message = "焦炭等级料号不能为空")
    @Size(max = 36, message = "焦炭等级料号长度不能超过36")
    @Schema(description = "焦炭等级料号")
    @JsonProperty("coal_level_materials_code")
    private String coalLevelMaterialsCode;

    /**
     * 焦炭等级名称
     */
    @NotNull(message = "焦炭等级名称为必填项")
    @NotEmpty(message = "焦炭等级名称不能为空")
    @Size(max = 36, message = "焦炭等级名称长度不能超过36")
    @Schema(description = "焦炭等级名称")
    @JsonProperty("coal_level_materials_name")
    private String coalLevelMaterialsName;

    /**
     * 配煤仓号
     */
    @NotNull(message = "配煤仓号为必填项")
    @Schema(description = "配煤仓号")
    @JsonProperty("coke_warehouse_number")
    private Integer cokeWarehouseNumber;

    /**
     * 煤的料号
     */
    @NotNull(message = "煤的料号为必填项")
    @NotEmpty(message = "煤的料号不能为空")
    @Size(max = 36, message = "煤的料号长度不能超过36")
    @Schema(description = "煤的料号")
    @JsonProperty("coke_materials_code")
    private String cokeMaterialsCode;

    /**
     * 煤的料号名称
     */
    @NotNull(message = "煤的料号名称为必填项")
    @NotEmpty(message = "煤的料号名称不能为空")
    @Size(max = 36, message = "煤的料号名称长度不能超过36")
    @Schema(description = "煤的料号名称")
    @JsonProperty("coke_materials_name")
    private String cokeMaterialsName;

    /**
     * 上煤开始时间
     */
    @NotNull(message = "上煤开始时间为必填项")
    @NotEmpty(message = "上煤开始时间不能为空")
    @Size(max = 36, message = "上煤开始时间长度不能超过36")
    @Schema(description = "上煤开始时间")
    @JsonProperty("start_time")
    private String startTime;

    /**
     * 上煤结束时间
     */
    @NotNull(message = "上煤结束时间为必填项")
    @NotEmpty(message = "上煤结束时间不能为空")
    @Size(max = 36, message = "上煤结束时间长度不能超过36")
    @Schema(description = "上煤结束时间")
    @JsonProperty("end_time")
    private String endTime;

    /**
     * 上煤重量
     */
    @NotNull(message = "上煤重量为必填项")
    @Schema(description = "上煤重量")
    @JsonProperty("coke_weight")
    private Integer cokeWeight;

    /**
     * 上煤前料位
     */
    @NotNull(message = "上煤前料位为必填项")
    @Schema(description = "上煤前料位")
    @JsonProperty("coke_material_level_befor")
    private Integer cokeMaterialLevelBefor;

    /**
     * 上煤后料位
     */
    @NotNull(message = "上煤后料位为必填项")
    @Schema(description = "上煤后料位")
    @JsonProperty("coke_material_level_after")
    private Integer cokeMaterialLevelAfter;

    /**
     * 创建人id
     */
    @NotNull(message = "创建人id为必填项")
    @NotEmpty(message = "创建人id不能为空")
    @Size(max = 36, message = "创建人id长度不能超过36")
    @Schema(description = "创建人id")
    @JsonProperty("create_user_id")
    private String createUserId;

    /**
     * 创建人
     */
    @NotNull(message = "创建人为必填项")
    @NotEmpty(message = "创建人不能为空")
    @Size(max = 50, message = "创建人长度不能超过50")
    @Schema(description = "创建人")
    @JsonProperty("create_user_name")
    private String createUserName;

    /**
     * 提交人部门
     */
    @NotNull(message = "部门编号为必须项")
    @NotEmpty(message = "部门编号不能为空")
    @JsonProperty("department_id")
    private String departmentId;

    /**
     * 应出勤时间
     */
    @NotNull(message = "应出勤时间为必填项")
    @NotEmpty(message = "应出勤时间不能为空")
    @Schema(description = "应出勤时间")
    @JsonProperty("due_attendance_time_work")
    private String dueAttendanceTimeWork;

    /**
     * 下班时间
     */
    @NotNull(message = "下班时间为必填项")
    @NotEmpty(message = "下班时间不能为空")
    @Schema(description = "下班时间")
    @JsonProperty("due_attendance_time_offduty")
    private String dueAttendanceTimeOffduty;

    @Schema(description = "煤的料号")
    @JsonProperty("coke_materials_small_code")
    private String cokeMaterialsSmallCode;

    @Schema(description = "煤的料号名称")
    @JsonProperty("coke_materials_small_name")
    private String cokeMaterialsSmallName;


}
