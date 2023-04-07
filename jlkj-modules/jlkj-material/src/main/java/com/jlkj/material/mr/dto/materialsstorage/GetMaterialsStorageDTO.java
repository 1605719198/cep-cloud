package com.jlkj.material.mr.dto.materialsstorage;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-05-17 10:06
 */
@Data
public class GetMaterialsStorageDTO  {

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

    private String createDateStart;

    private String createDateEnd;
}
