package com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO类-查询-分页-配煤分析-列表
 * @author yzl
 */
@Data
public class PageProductionConfigureCoalSpeciesAnalysisDTO {
    /**
     * 班次
     */
    @NotNull(message = "年份为必填项")
    private String year;

    /**
     * 班别
     */
    private String month;

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

