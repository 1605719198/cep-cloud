package com.jlkj.system.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-04-18 13:37
 */
@Data
public class UserLoginDTO {

    @NotNull(message = "账号为必须项")
    @NotEmpty(message = "账号不能为空")
    private String account;

    @NotNull(message = "密码为必须项")
    @NotEmpty(message = "密码不能为空")
    private String password;

    private String browser;
}
