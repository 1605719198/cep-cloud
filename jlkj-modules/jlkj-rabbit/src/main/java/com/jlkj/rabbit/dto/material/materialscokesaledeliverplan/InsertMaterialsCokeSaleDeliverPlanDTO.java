package com.jlkj.rabbit.dto.material.materialscokesaledeliverplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-焦炭外售发货计划-新增
 * @author sudeyou
 * @since 2022-08-19 14:00:15
 */
@Data
@Schema(description = "DTO类-焦炭外售发货计划-新增")
public class InsertMaterialsCokeSaleDeliverPlanDTO {
    /**
     * 发货单号
     */
    @NotNull(message = "发货单号为必填项")
    @NotEmpty(message = "发货单号不能为空")
    @Size(max = 20, message = "发货单号长度不能超过20")
    @Schema(description = "发货单号")
    @JsonProperty("plan_list_no")
    private String planListNo;

    /**
     * 运输方式
     */
    @NotNull(message = "运输方式为必填项")
    @Size(max = 1, message = "运输方式长度不能超过1")
    @Schema(description = "运输方式")
    @JsonProperty("carrier_type")
    private String carrierType;

    /**
     * 车号
     */
    @NotNull(message = "车号为必填项")
    @Size(max = 20, message = "车号长度不能超过20")
    @Schema(description = "车号")
    @JsonProperty("carrier_no")
    private String carrierNo;

    /**
     * 料号
     */
    @NotNull(message = "料号为必填项")
    @Size(max = 20, message = "料号长度不能超过20")
    @Schema(description = "料号")
    @JsonProperty("mtrl_no")
    private String mtrlNo;

    /**
     * 储位代码
     */
    @NotNull(message = "储位代码为必填项")
    @Size(max = 10, message = "储位代码长度不能超过10")
    @Schema(description = "储位代码")
    @JsonProperty("from_code")
    private String fromCode;

    /**
     * 客户编号
     */
    @NotNull(message = "客户编号为必填项")
    @Size(max = 10, message = "客户编号长度不能超过10")
    @Schema(description = "客户编号")
    @JsonProperty("to_code")
    private String toCode;

    /**
     * 客户名称
     */
    @NotNull(message = "客户名称为必填项")
    @Size(max = 200, message = "客户名称长度不能超过200")
    @Schema(description = "客户名称")
    @JsonProperty("to_desc")
    private String toDesc;

    /**
     * 合同号
     */
    @NotNull(message = "合同号为必填项")
    @Size(max = 20, message = "合同号长度不能超过20")
    @Schema(description = "合同号")
    @JsonProperty("pchr_no")
    private String pchrNo;

    /**
     * 是否过磅
     */
    @NotNull(message = "是否过磅为必填项")
    @Size(max = 1, message = "是否过磅长度不能超过1")
    @Schema(description = "是否过磅")
    @JsonProperty("is_load")
    private String isLoad;

    /**
     * 过磅点
     */
    @NotNull(message = "过磅点为必填项")
    @Size(max = 3, message = "过磅点长度不能超过3")
    @Schema(description = "过磅点")
    @JsonProperty("load_station")
    private String loadStation;

    /**
     * 开单人单位代码（二级）
     */
    @NotNull(message = "开单人单位代码（二级）为必填项")
    @Size(max = 10, message = "开单人单位代码（二级）长度不能超过10")
    @Schema(description = "开单人单位代码（二级）")
    @JsonProperty("issue_dept")
    private String issueDept;

    /**
     * 开单人单位名称
     */
    @NotNull(message = "开单人单位名称为必填项")
    @Size(max = 40, message = "开单人单位名称长度不能超过40")
    @Schema(description = "开单人单位名称")
    @JsonProperty("issue_dept_name")
    private String issueDeptName;

    /**
     * 开单人工号
     */
    @NotNull(message = "开单人工号为必填项")
    @Size(max = 10, message = "开单人工号长度不能超过10")
    @Schema(description = "开单人工号")
    @JsonProperty("issue_emp_no")
    private String issueEmpNo;

    /**
     * 开单人姓名
     */
    @NotNull(message = "开单人姓名为必填项")
    @Size(max = 20, message = "开单人姓名长度不能超过20")
    @Schema(description = "开单人姓名")
    @JsonProperty("issue_emp_name")
    private String issueEmpName;

    /**
     * 申请日期
     */
    @NotNull(message = "申请日期为必填项")
    @Size(max = 8, message = "申请日期长度不能超过8")
    @Schema(description = "申请日期")
    @JsonProperty("issue_date")
    private String issueDate;

}

