package com.jlkj.product.oi.dto.dispatchlog;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-查询-分页-调度日志
 * @author sudeyou
 */
@Data
public class PageDispatchLogDTO {

    /**
     * 主键
     */
    @NotNull(message = "主键为必填项")
    @Size(max = 36, message = "主键长度不能超过36")
    private String id;

    /**
     * 调度人员id
     */
    @NotNull(message = "调度人员id为必填项")
    @Size(max = 36, message = "调度人员id长度不能超过36")
    private String dispatcherId;

    /**
     * 处理人员id
     */
    @NotNull(message = "处理人员id为必填项")
    @Size(max = 36, message = "处理人员id长度不能超过36")
    private String workerId;

    /**
     * 状态标识
     */
    @NotNull(message = "状态标识为必填项")
    private Integer stateFlag;

    /**
     * 工作内容
     */
    @NotNull(message = "工作内容为必填项")
    @Size(max = 900, message = "工作内容长度不能超过900")
    private String workContent;

    /**
     * 调度时间[开始]
     */
    @NotNull(message = "调度时间[开始]为必填项")
    private String dispathTimeStart;

    /**
     * 调度时间[结束]
     */
    @NotNull(message = "调度时间[结束]为必填项")
    private String dispathTimeEnd;

    /**
     * 当前页
     */
    @NotNull(message = "当前页current为必填项")
    @Min(value = 1, message = "当前页current不能小于1")
    private Long current;

    /**
     * 每页行数
     */
    @NotNull(message = "每页行数size为必填项")
    @Min(value = 1, message = "每页行数size不能小于1")
    private Long size;

    /**
     * 排序字段
     */
    @NotNull(message = "排序字段order为必填项")
    @NotEmpty(message = "排序字段order不能为空")
    private String order;

    /**
     * 排序规则
     */
    @NotNull(message = "排序规则orderby为必填项")
    private String orderby;
}

