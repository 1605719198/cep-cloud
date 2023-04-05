package com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-分页-配煤实绩主记录
 * @author sudeyou
 */
@Data
public class PageProductionConfigureCoalSpeciesPerformanceDTO {
    /**
     * 班次
     */
    @NotNull(message = "班次为必填项")
    @Size(max = 50, message = "班次长度不能超过50")
    private String shiftName;

    /**
     * 班别
     */
    @NotNull(message = "班别为必填项")
    @Size(max = 50, message = "班别长度不能超过50")
    private String className;

    /**
     * 开始时间[开始]
     */
    @NotNull(message = "开始时间[开始]为必填项")
    private String startTime;

    /**
     * 开始时间[结束]
     */
    @NotNull(message = "开始时间[结束]为必填项")
    private String endTime;

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

