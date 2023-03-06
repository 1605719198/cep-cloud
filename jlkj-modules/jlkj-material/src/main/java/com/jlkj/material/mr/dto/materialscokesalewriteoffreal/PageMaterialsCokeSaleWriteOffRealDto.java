package com.jlkj.material.mr.dto.materialscokesalewriteoffreal;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yzl
 * @create 2022年9月8日 14:26:50
 */
@Data
public class PageMaterialsCokeSaleWriteOffRealDto {

    private String startTime;
    private String endTime;
    private String carrierNo;
    private String carrierType;
    private String toDesc;

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
}
