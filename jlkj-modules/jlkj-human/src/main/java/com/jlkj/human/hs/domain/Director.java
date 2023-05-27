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
 * 各公司人事业务负责人
 * @TableName human_hs_director
 */
@TableName(value ="human_hs_director")
@Data
public class Director implements Serializable {
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
     * 职责编码
     */
    private String dutyCode;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 岗位ID
     */
    private String postId;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 预留字段1
     */
    private String resvAttr1;

    /**
     * 预留字段2
     */
    private String resvAttr2;

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