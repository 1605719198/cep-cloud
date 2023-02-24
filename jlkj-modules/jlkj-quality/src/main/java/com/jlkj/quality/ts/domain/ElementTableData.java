package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试验元素表格维护表
 * @TableName t_ts_element_table_data
 * @author 265675
 */
@TableName(value ="t_ts_element_table_data")
@Data
public class ElementTableData implements Serializable {
    /**
     * 元素编号
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 表格代号
     */
    private String formatId;

    /**
     * 元素类别
     */
    private String elementType;

    /**
     * 试验元素代号
     */
    private String elementIndex;

    /**
     * 元素显示顺序
     */
    private Integer elementSeq;

    /**
     * 表格元素数量
     */
    private Integer elementCount;

    /**
     * 建档人员
     */
    private String createUserId;

    /**
     * 异动人员
     */
    private String modifyUserId;

    /**
     * 异动日期
     */
    private String modifyTime;

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
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
