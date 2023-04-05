package com.jlkj.product.oi.vo.productioncokefactoryperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-焦炭出厂实绩-查询-分页
 * @author sudeyou
 * @since 2022-08-11 14:02:21
 */
@Data
@Schema(description = "VO类-焦炭出厂实绩-查询-分页")
public class PageProductionCokeFactoryPerformanceVO {
    /**
     * 班次
     */
    @Schema(description = "班次")
    @JsonProperty("shift_name")
    private String shiftName;

    /**
     * 班别
     */
    @Schema(description = "班别")
    @JsonProperty("class_name")
    private String className;

    /**
     * 成员
     */
    @Schema(description = "成员")
    @JsonProperty("person")
    private String person;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @JsonProperty("client_name")
    private String clientName;

    /**
     * 物料名称
     */
    @Schema(description = "物料名称")
    @JsonProperty("materials_name")
    private String materialsName;

    /**
     * 车牌号
     */
    @Schema(description = "车牌号")
    @JsonProperty("vehicle_number")
    private String vehicleNumber;

    /**
     * 装车时间
     */
    @Schema(description = "装车时间")
    @JsonProperty("loading_time")
    private String loadingTime;

    /**
     * 净重
     */
    @Schema(description = "净重")
    @JsonProperty("net_wgt")
    private BigDecimal netWgt;

    /**
     * 申请人工号
     */
    @Schema(description = "申请人工号")
    @JsonProperty("issue_emp_no")
    private String issueEmpNo;

    /**
     * 申请人姓名
     */
    @Schema(description = "申请人姓名")
    @JsonProperty("issue_emp_name")
    private String issueEmpName;
}

