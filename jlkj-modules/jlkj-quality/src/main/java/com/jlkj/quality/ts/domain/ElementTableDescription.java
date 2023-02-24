package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分析元素表格说明表
 * @TableName t_ts_element_table_description
 * @author 265675
 */
@TableName(value ="t_ts_element_table_description")
@Data
public class ElementTableDescription implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 表格代号
     */
    private String formatId;

    /**
     * 表格说明
     */
    private String formatMemo;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
