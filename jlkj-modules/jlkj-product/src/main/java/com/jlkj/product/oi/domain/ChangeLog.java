package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-变更日志
 * @author sudeyou
 * @since 2022-09-21 10:57:59
 */
@TableName(value ="product_change_log")
@Data
public class ChangeLog implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 功能名称
     */
    private String functionName;

    /**
     * 变更内容
     */
    private String content;

    /**
     * 创建人id
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

