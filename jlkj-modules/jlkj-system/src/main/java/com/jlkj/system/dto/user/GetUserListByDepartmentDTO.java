package com.jlkj.system.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-07-05 13:14
 */
@Data
public class GetUserListByDepartmentDTO {

    @NotNull(message = "部门id为必需")
    private String department_id;
}
