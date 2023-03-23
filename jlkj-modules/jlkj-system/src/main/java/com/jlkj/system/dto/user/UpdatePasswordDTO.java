package com.jlkj.system.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:修改密码DTO
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Data
public class UpdatePasswordDTO {

    @NotNull(message = "user_id为必须项")
    @NotEmpty(message = "user_id不能为空")
    @JsonProperty("user_id")
    private String userId;

    @NotNull(message = "原密码为必须项")
    @NotEmpty(message = "原密码不能为空")
    @JsonProperty("old_password")
    private String oldPassword;

    @NotNull(message = "新密码为必须项")
    @NotEmpty(message = "新密码不能为空")
    @JsonProperty("new_password")
    private String newPassword;
}
