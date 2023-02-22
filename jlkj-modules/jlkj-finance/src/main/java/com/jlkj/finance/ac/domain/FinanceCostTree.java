package com.jlkj.finance.ac.domain;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 成本科目阶层
 * @author wang'mai
 * @TableName t_finance_cost_tree
 */
@TableName(value ="finance_ac_cost_tree")
@Data
public class FinanceCostTree implements Serializable {
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
     * 成本科目阶层编码
     */
    private String costCodeLevelCode;

    /**
     * 上层代码
     */
    private String upperCode;

    /**
     * 阶层代码
     */
    private String levelCode;

    /**
     * 阶层名称
     */
    private String levelName;

    /**
     * 节点类别
     */
    private String nodeType;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */

    private String createDate;

    /**
     * 最后异动人
     */
    private String updateUser;

    /**
     * 最后异动日期
     */

    private String updateDate;

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
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;
    /**
     * 父键
     */
    private String parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}