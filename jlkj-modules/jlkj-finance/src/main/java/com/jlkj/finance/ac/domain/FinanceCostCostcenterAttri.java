package com.jlkj.finance.ac.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 成本中心代码属性
 * @author wang'mai
 * @TableName t_finance_cost_costcenter_attri
 */
@TableName(value ="finance_ac_cost_costcenter_attri")
@Data
public class FinanceCostCostcenterAttri implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 父类
     */
    private String parentId;

    /**
     * 属性名称
     */
    private String attriName;

    /**
     * 属性值
     */
    private String attriValue;

    /**
     * 属性说明
     */
    private String attriDesc;

    /**
     * 资料A
     */
    private String factorA;

    /**
     * 资料B
     */
    private String factorB;

    /**
     * 资料C
     */
    private String factorC;

    /**
     * 建立人
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
     * 建立日期
     */

    private Date createDate;

    /**
     * 最后异动人
     */
    private String updateUser;

    /**
     * 最后异动日期
     */

    private Date updateDate;

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

    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public void setAttriName(String attriName) {
        this.attriName = attriName;
    }

    public void setAttriValue(String attriValue) {
        this.attriValue = attriValue;
    }

    public void setAttriDesc(String attriDesc) {
        this.attriDesc = attriDesc;
    }

    public void setFactorA(String factorA) {
        this.factorA = factorA;
    }

    public void setFactorB(String factorB) {
        this.factorB = factorB;
    }

    public void setFactorC(String factorC) {
        this.factorC = factorC;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}