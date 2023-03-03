package com.jlkj.finance.aa.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会计科目参号资料
 * @author wang'mai
 * @TableName t_finance_ref_code
 */
@TableName(value ="finance_aa_ref_code")
@Data
public class FinanceRefCode implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 参号类别编码
     */
    private String refTypeNum;

    /**
     * 参号类型编码
     */
    private String refKindNum;

    /**
     * 参号规则
     */
    private String refTypeRule;

    /**
     * 参号检核规则
     */
    private String refTypeCheck;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
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

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
