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
 * 薪资计算状态记录表
 * @TableName human_hs_salary_status
 */
@TableName(value ="human_hs_salary_status")
@Data
public class SalaryStatus implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 状态（0为计算失败，5为需批次计算，10为开始计算，20为计算结束，30为封账，35抛账开始，40抛账成功，45抛账失败，50为开放员工查询）
     */
    private String status;

    /**
     * 薪资类别ID
     */
    private String payTypeNo;

    /**
     * 公司ID
     */
    private String compId;

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