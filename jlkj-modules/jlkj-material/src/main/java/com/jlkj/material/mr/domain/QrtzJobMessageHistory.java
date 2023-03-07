package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-报文抛送历史表
 *
 * @author sudeyou
 * @since 2023-01-06 11:21:25
 */
@TableName(value ="qrtz_job_message_history")
@Data
public class QrtzJobMessageHistory implements Serializable {
    /**
     * uuid
     */
    @TableId
    private String id;

    /**
     * 定时任务id
     */
    private String jobId;

    /**
     * 举例：cep.erp.mr.mr58
     */
    private String queueId;

    /**
     * 抛送时间
     */
    private Date sendTime;

    /**
     * 抛送类型(1:定时；2:实时)
     */
    private Short sendType;

    /**
     * 抛送报文内容
     */
    private String messageContent;

    /**
     * 描述
     */
    private String descriptionContent;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

