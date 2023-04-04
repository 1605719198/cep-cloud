package com.jlkj.product.oi.dto.dispatchlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-修改-调度日志
 * @author sudeyou
 */
@Data
public class UpdateDispatchLogDTO {
    /**
     * 调度记录编号
     */
    @NotNull(message = "调度记录编号为必填项")
    @NotEmpty(message = "调度记录编号不能为空")
    @Size(max = 36, message = "调度记录编号长度不能超过36")
    @JsonProperty("id")
    private String id;

    /**
     * 处理人员ID
     */
    @NotNull(message = "处理人员ID为必填项")
    @NotEmpty(message = "处理人员ID不能为空")
    @Size(max = 36, message = "处理人员ID长度不能超过36")
    @JsonProperty("worker_id")
    private String workerId;

    /**
     * 处理人员姓名
     */
    @NotNull(message = "处理人员姓名为必填项")
    @NotEmpty(message = "处理人员姓名不能为空")
    @Size(max = 50, message = "处理人员姓名长度不能超过50")
    @JsonProperty("worker_name")
    private String workerName;

    /**
     * 工作内容
     */
    @NotNull(message = "工作内容为必填项")
    @NotEmpty(message = "工作内容不能为空")
    @Size(max = 900, message = "工作内容长度不能超过900")
    @JsonProperty("work_content")
    private String workContent;

    /**
     * 调度时间
     */
    @NotNull(message = "调度时间为必填项")
    @NotEmpty(message = "调度时间不能为空")
    @JsonProperty("dispath_time")
    private String dispathTime;

}

