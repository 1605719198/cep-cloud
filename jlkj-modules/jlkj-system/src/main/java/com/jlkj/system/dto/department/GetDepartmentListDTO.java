package com.jlkj.system.dto.department;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-07-06 14:05
 */
@Data
public class GetDepartmentListDTO {

    @NotNull(message = "department_id为必须项")
    private String department_id;

    @NotNull(message = "department_name为必须项")
    private String department_name;

    @NotNull(message = "current为必须项")
    @Min(value = 1, message = "current不能小于1")
    private Long current;

    @NotNull(message = "size为必须项")
    @Min(value = 1, message = "size不能小于1")
    private Long size;
}
