package com.jlkj.system.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-07-07 8:49
 */
@Data
public class UserLogoutDTO {

    @NotNull(message="user_id为必须项")
    private String user_id;
}
