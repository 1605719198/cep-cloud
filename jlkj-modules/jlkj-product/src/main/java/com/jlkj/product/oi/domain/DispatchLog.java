package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-调度日志
 * @author sudeyou
 */
@TableName(value ="product_dispatch_log")
@Data
public class DispatchLog implements Serializable {
    /**
     * 调度记录编号
     */
    @TableId
    private String id;

    /**
     * 调度人员ID
     */
    private String dispatcherId;

    /**
     * 调度人员姓名
     */
    private String dispatcherName;

    /**
     * 处理人员ID
     */
    private String workerId;

    /**
     * 处理人员姓名
     */
    private String workerName;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 处理说明
     */
    private String disposeContent;

    /**
     * 处理结果
     */
    private String dispose;

    /**
     * 完成标识
     */
    private Short stateFlag;

    /**
     * 填报时间
     */
    private Date createTime;

    /**
     * 调度时间
     */
    private Date dispathTime;

    /**
     * 处理反馈时间
     */
    private Date disposeTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

