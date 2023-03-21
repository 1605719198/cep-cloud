package com.jlkj.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zyf
 * @TableName t_sys_queue_dlx
 */
@TableName(value ="t_sys_queue_dlx")
@Data
public class SysQueueDlx implements Serializable {
    /**
     *
     */
    @TableId
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 队列名称
     */
    private String queueName;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 异常内容
     */
    private String exceptionComtent;

    /**
     * 是否已处理(0未处理1已处理)
     */
    private Integer isChecked;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
