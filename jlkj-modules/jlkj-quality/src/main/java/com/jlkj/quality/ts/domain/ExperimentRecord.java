package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 焦炭质量记录
 * @author 265675
 * @TableName t_ts_experiment_record
 */
@TableName(value ="t_ts_experiment_record")
@Data
public class ExperimentRecord implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 样品分类代号
     */
    private String code;

    /**
     * 传输格式
     */
    private String formatId;

    /**
     * 元素数量
     */
    private Integer num;

    /**
     * 接收时间
     */
    private String receiveTime;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 样品编号
     */
    private String sampleId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建人工号
     */
    private String createUser;

    /**
     * 修改人工号
     */
    private String updateUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 传送字符串
     */
    private String jsonString;

    /**
     * 班别
     */
    private String shift;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}