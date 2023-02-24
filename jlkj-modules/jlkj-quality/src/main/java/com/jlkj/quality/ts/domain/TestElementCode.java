package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试验元素代号维护表
 * @TableName t_ts_test_element_code
 * @author 265675
 */
@TableName(value ="t_ts_test_element_code")
@Data
public class TestElementCode implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 元素类别
     */
    private String elementType;

    /**
     * 试验元素序号
     */
    private String elementIndex;

    /**
     * 元素名称/试验项目
     */
    private String elementName;

    /**
     * 单位
     */
    private String elementUnit;

    /**
     * 整数字数
     */
    private Integer integerNo;

    /**
     * 小数字数
     */
    private Integer pointerNo;

    /**
     * 建档人员
     */
    private String createEmployNo;

    /**
     * 建档日期
     */
    private String createDate;

    /**
     * 异动人员
     */
    private String modifyEmployNo;

    /**
     * 异动日期
     */
    private String modifyDate;

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
     * 创建人名称
     */
    private String createUserName;

    /**
     * 修改人名称
     */
    private String updateUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
