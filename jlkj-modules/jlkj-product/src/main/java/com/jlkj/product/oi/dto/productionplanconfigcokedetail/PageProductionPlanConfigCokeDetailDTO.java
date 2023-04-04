package com.jlkj.product.oi.dto.productionplanconfigcokedetail;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-分页-配煤计划配煤记录
 * @author sudeyou
 */
@Data
public class PageProductionPlanConfigCokeDetailDTO {
    /**
     * 计划编号
     */
    @NotNull(message = "计划编号为必填项")
    @NotEmpty(message = "计划编号不能为空")
    @Size(max = 36, message = "计划编号长度不能超过36")
    private String planId;

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

