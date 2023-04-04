package com.jlkj.product.oi.dto.dispatchlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-处理结果反馈-调度日志
 * @author sudeyou
 */
@Data
public class FeedbackDispatchLogDTO {
    /**
     * 调度记录编号
     */
    @NotNull(message = "调度记录编号为必填项")
    @NotEmpty(message = "调度记录编号不能为空")
    @Size(max = 36, message = "调度记录编号长度不能超过36")
    @JsonProperty("id")
    private String id;

    /**
     * 处理说明
     */
    @NotNull(message = "处理说明为必填项")
    @NotEmpty(message = "处理说明不能为空")
    @Size(max = 900, message = "处理说明长度不能超过900")
    @JsonProperty("dispose_content")
    private String disposeContent;

    /**
     * 处理结果
     */
    @NotNull(message = "处理结果为必填项")
    @NotEmpty(message = "处理结果不能为空")
    @Size(max = 90, message = "处理结果长度不能超过90")
    @JsonProperty("dispose")
    private String dispose;

}

