package com.jlkj.common.dto.dto.financeperdto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;



import java.util.Date;

/**
 * 厂商承运关系资料表
 * @author wang'mai
 * @TableName t_manufacturer_transport
 */
@Data
public class ManufacturerTransportDTO  {
    /**
     * 主键
     */
    @TableId
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
    private Date createUser;

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