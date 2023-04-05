package com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformance;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DTO类-查询-分页-配煤分析-图表
 * @author yzl
 */
@Data
public class PageProductionConfigureCoalSpeciesAnalysisChartDTO {
    /**
     * 年份
     */
    @NotNull(message = "年份为必填项")
    private String year;

    /**
     * 月份
     */
    private String month;

}
