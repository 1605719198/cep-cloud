package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 岗位考评项目设定明细档
 * @TableName human_pa_dept_type_detail
 */
@TableName(value ="human_pa_dept_type_detail")
@Data
public class DeptTypeDetail implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 岗位考评项目主档ID（human_pa_dept_type表的ID）
     */
    private String deptTypeId;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 序号
     */
    private String srlNo;

    /**
     * 考评项目
     */
    private String item;

    /**
     * 考评项目说明
     */
    private String dataDept;

    /**
     * 权重
     */
    private BigDecimal weight;

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