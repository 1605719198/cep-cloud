package com.jlkj.common.dto.aop.syslog;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zyf
 * 系统日志
 * @TableName sys_logs
 */
@TableName(value ="sys_logs")
@Data
public class SysLogs implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 事件类型
     */
    private String type;

    /**
     * 请求路径
     */
    private String uri;

    /**
     * 日志内容
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}