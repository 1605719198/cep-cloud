package com.jlkj.finance.ac.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 成本中心基本资料
 * @author wang'mai
 * @TableName t_finance_cost_costcenter
 */
@TableName(value ="finance_ac_cost_costcenter")
@Data
public class FinanceCostCostcenter implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
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
     * 公司别
     */
    private String compId;

    /**
     * 年度
     */
    private String fiscalYear;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 成本中心说明
     */
    private String costCenterDesc;

    /**
     * 会计归属码
     */
    private String allocCode;

    /**
     * 成本中心形态属性
     */
    private String ccType;

    /**
     * 产品产出属性
     */
    private String isProduct;

    /**
     * 状态
     */
    private String stusCode;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */

    private Date createDate;

    /**
     * 最后修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */

    private Date updateDate;

    /**
     * 是否抛AC帐
     */
    private String isPostAc;

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