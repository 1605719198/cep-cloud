package com.jlkj.finance.gp.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**
 * 厂商承运关系资料表
 * @author wang'mai
 * @TableName finance_gp_manufacturer_transport
 */
@TableName(value ="finance_gp_manufacturer_transport")
@Data
public class ManufacturerTransport implements Serializable {
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
     * 厂商名称
     */
    private String vendorName;

    /**
     * 法人代表
     */
    private String custOwner;

    /**
     * 电话
     */
    private String officeTelephoneNo;

    /**
     * 传真
     */
    private String fax;

    /**
     * 发票传递方式
     */
    private String billTransType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 中文地址
     */
    private String addr;

    /**
     * 英文地址
     */
    private String engAddr;

    /**
     * 邮递区号
     */
    private String zip;

    /**
     * 联络地址
     */
    private String contactAddr;

    /**
     * 联络英文地址
     */
    private String contactEngAddr;

    /**
     * 邮递区号（联络地址）
     */
    private String contactZip;

    /**
     * 开户银行帐号
     */
    private String remitAccount1;

    /**
     * 开户银行
     */
    private String remitBank1;

    /**
     * 征信
     */
    private String credit;

    /**
     * 税号
     */
    private String taxNo;

    /**
     * 企业代码
     */
    private String enterpriseNo;

    /**
     * 最终用户
     */
    private String lastUser;

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
     * 修改人员
     */
    private String updateUser;

    /**
     * 修改时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}