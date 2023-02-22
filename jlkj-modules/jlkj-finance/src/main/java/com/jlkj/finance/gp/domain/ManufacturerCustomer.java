package com.jlkj.finance.gp.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**
 * 厂商客户关系资料表
 * @author wang'mai
 * @TableName finance_gp_manufacturer_customer
 */
@TableName(value ="finance_gp_manufacturer_customer")
@Data
public class ManufacturerCustomer implements Serializable {
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
     * 厂商编码
     */
    private String manufacturerId;

    /**
     * 客户简称
     */
    private String customerName;

    /**
     * 联络人
     */
    private String customerContract;

    /**
     * 联络人职称
     */
    private String customerPost;

    /**
     * 联络电话
     */
    private String customerTel;

    /**
     * 联络手机
     */
    private String customerMobile;

    /**
     * 传真号码
     */
    private String customerFax;

    /**
     * 送货地址
     */
    private String customerAddr;

    /**
     * 发票联式
     */
    private String customerBill;

    /**
     * 交货条件
     */
    private String customerDelivery;

    /**
     * 付款方式
     */
    private String customerPayWay;

    /**
     * 付款条件
     */
    private String customerPayCondition;

    /**
     * 付款天数
     */
    private String customerPayDay;

    /**
     * 结算日
     */
    private String customerCloseDate;

    /**
     * 备注
     */
    private String customerMemo;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 修改时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date updateTime;

    /**
     * 建立人员
     */
    private String createUser;

    /**
     * 建立时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 征信
     */
    private String credit;

    /**
     * 开户银行帐号
     */
    private String remitAccount1;

    /**
     * 开户银行
     */
    private String remitBank1;

    /**
     * 电子邮件
     */
    private String customerEmail;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}