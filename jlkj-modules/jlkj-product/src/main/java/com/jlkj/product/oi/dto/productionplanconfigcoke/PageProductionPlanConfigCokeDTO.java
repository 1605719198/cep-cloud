package com.jlkj.product.oi.dto.productionplanconfigcoke;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO类-查询-分页-配煤计划主记录
 * @author sudeyou
 */
@Data
public class PageProductionPlanConfigCokeDTO {
    /**
     * 开始时间[开始]
     */
    @NotNull(message = "开始时间[开始]为必填项")
    private String planStartTime;

    /**
     * 开始时间[结束]
     */
    @NotNull(message = "开始时间[结束]为必填项")
    private String planEndTime;

    /**
     * 计划状态
     */
    @NotNull(message = "计划状态为必填项")
    private Short planState;

    /**
     * 焦炭等级
     */
    @NotNull(message = "焦炭等级为必填项")
    private String materialId;

    /**
     * 计划编号
     */
    private String planNumber;

    /**
     * 当前页
     */
    @NotNull(message = "当前页current为必填项")
    @Min(value = 1, message = "当前页current不能小于1")
    private Long current;

    /**
     * 每页行数
     */
    @NotNull(message = "每页行数size为必填项")
    @Min(value = 1, message = "每页行数size不能小于1")
    private Long size;

    /**
     * 排序字段
     */
    @NotNull(message = "排序字段order为必填项")
    @NotEmpty(message = "排序字段order不能为空")
    private String order;

    /**
     * 排序规则
     */
    @NotNull(message = "排序规则orderby为必填项")
    private String orderby;
}

