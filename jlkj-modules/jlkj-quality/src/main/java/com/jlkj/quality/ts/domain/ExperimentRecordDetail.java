package com.jlkj.quality.ts.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 焦炭质量记录
 * @author 265675
 * @TableName t_ts_experiment_record_detail
 */
@TableName(value ="t_ts_experiment_record_detail")
@Data
public class ExperimentRecordDetail implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 样品编号主表ID
     */
    private String parentId;

    /**
     * 样品分类代号
     */
    private String code;

    /**
     * 传输格式
     */
    private String formatId;

    /**
     * 接收时间
     */
    private String receiveTime;

    /**
     * 样品编号
     */
    private String sampleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
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
     * 元素名称
     */
    private String element;

    /**
     * 元素代号
     */
    private String elementId;

    /**
     * 元素值
     */
    private String elementValue;

    /**
     * 样品类型(1焦炭，2入炉煤，3装车煤)
     */
    private String sampleType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}