package com.jlkj.system.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zyf
 * @Description
 * @create 2022-09-19 10:12
 */
@Data
public class ResetPasswordDTO {

    @NotNull(message = "user_id为必须项")
    @NotEmpty(message = "user_id不能为空")
    @JsonProperty("user_id")
    private String userId;
}
