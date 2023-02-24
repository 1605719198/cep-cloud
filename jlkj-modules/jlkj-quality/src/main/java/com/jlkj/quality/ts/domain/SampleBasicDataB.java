package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 样品类别标准资料档B
 * @TableName t_ts_sample_basic_data_b
 * @author 265675
 */
@TableName(value ="t_ts_sample_basic_data_b")
@Data
public class SampleBasicDataB implements Serializable {
    /**
     * 元素编号
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 样品类别
     */
    private String sampleClass;

    /**
     * 结果接收站
     */
    private String sendStation;

    /**
     * 发送格式
     */
    private String sendFormat;

    /**
     * 修改人职工编号
     */
    private String modifyEmployNo;

    /**
     * 修改日期
     */
    private String modifyDate;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 新增人职工编号
     */
    private String createEmployNo;

    /**
     * 新增日期
     */
    private String createDate;

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
