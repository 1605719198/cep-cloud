package com.jlkj.finance.ac.dto;

import lombok.Data;

import java.util.Date;
/**
 * @author wang'mai
 */
@Data
public class FinanceCostCostcenterAttriDTO {

    /**
     * 主键
     */
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
    /**
     * 属性说明
     */
    private String label;
    /**
     * 属性名称
     */
    private String value;
}
