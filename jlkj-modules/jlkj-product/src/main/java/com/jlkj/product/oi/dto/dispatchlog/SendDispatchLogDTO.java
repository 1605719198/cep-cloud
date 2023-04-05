package com.jlkj.product.oi.dto.dispatchlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-指令下发-调度日志
 * @author sudeyou
 */
@Data
public class SendDispatchLogDTO {
    /**
     * 调度记录编号
     */
    @NotNull(message = "调度记录编号为必填项")
    @NotEmpty(message = "调度记录编号不能为空")
    @Size(max = 36, message = "调度记录编号长度不能超过36")
    @JsonProperty("id")
    private String id;

}

