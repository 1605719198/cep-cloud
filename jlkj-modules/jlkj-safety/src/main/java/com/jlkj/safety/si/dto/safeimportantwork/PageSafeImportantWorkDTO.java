package com.jlkj.safety.si.dto.safeimportantwork;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: dto 查询
 * @author: yzl
 * @time: 2022年6月6日
 */
@Data
public class PageSafeImportantWorkDTO {

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

    /**开始时间*/
    private String planStartTime;

    /**结束时间*/
    private String planEndTime;

    /**单位*/
    private String departmentId;

    /**完成情况*/
    private String completeState;
}
