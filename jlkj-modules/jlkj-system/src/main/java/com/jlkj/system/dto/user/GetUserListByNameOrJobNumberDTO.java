package com.jlkj.system.dto.user;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-07-05 13:52
 */
@Data
public class GetUserListByNameOrJobNumberDTO {
    @NotNull(message = "部门id为必需")
    private String department_id;

    @NotNull(message = "关键词为必需")
    private String keywords;

    @NotNull(message = "current为必须项")
    @Min(value = 1, message = "current不能小于1")
    private Long current;

    @NotNull(message = "size为必须项")
    @Min(value = 1, message = "size不能小于1")
    private Long size;
}
