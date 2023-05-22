package com.jlkj.human.hs.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司薪资参数设定表
 * @TableName human_hs_pay_param_standard
 */
@TableName(value ="human_hs_pay_param_standard")
@Data
public class PayParamStandard implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String uuid;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 标准编码
     */
    private String standardCode;

    /**
     * 标准
     */
    private String standard;

    /**
     * 说明
     */
    private String description;

    /**
     * 状态
     */
    private String status;

    /**
     * 薪资参数ID
     */
    private String payParaId;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}