package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 样品试验高低标准
 * @TableName t_ts_element_high_low_standard
 * @author 265675
 */
@TableName(value ="t_ts_element_high_low_standard")
@Data
public class ElementHighLowStandard implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 样品类别
     */
    private String sampleClass;

    /**
     * 元素类别
     */
    private String elementType;

    /**
     * 元素代号
     */
    private String elementIndex;

    /**
     * 最低值
     */
    private BigDecimal minValue;

    /**
     * 最高值
     */
    private BigDecimal maxValue;

    /**
     * 异动人员
     */
    private String modifyUserId;

    /**
     * 异动日期
     */
    private String modifyTime;

    /**
     * 新增人职工编号
     */
    private String createUserId;

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
