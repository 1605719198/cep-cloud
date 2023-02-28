package com.jlkj.common.dto.finance.gp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * 厂商采购关系资料表
 * @author wang'mai
 * @TableName t_manufacturer_purchase
 */

@Data
public class ManufacturerPurchaseDTO {
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
     * 付款类型
     */
    private String payType;

    /**
     * 交易条件
     */
    private String tradeTerm;

    /**
     * 信用等级
     */
    private String credit;

    /**
     * 设定银行
     */
    private String bankCode;

    /**
     * 汇款银行1
     */
    private String remitBank1;

    /**
     * 汇款帐号1
     */
    private String remitAccount1;

    /**
     * 汇款银行2
     */
    private String remitBank2;

    /**
     * 汇款帐号2
     */
    private String remitAccount2;

    /**
     * 汇款银行3
     */
    private String remitBank3;

    /**
     * 汇款帐号3
     */
    private String remitAccount3;

    /**
     * 厂商性质
     */
    private String vendorType;

    /**
     * 特别备注
     */
    private String memo;

    /**
     * 状态码
     */
    private String status;

    /**
     * 资料所属单位
     */
    private String respDept;

    /**
     * 产品别
     */
    private String productType;

    /**
     * 主要产品说明
     */
    private String productDescp;

    /**
     * 交易不良记录
     */
    private String badHistory;

    /**
     * 关系企业
     */
    private String corpGroup;

    /**
     * 厂商资料来源
     */
    private String source;

    /**
     * 厂调报告
     */
    private String report;

    /**
     * 公司大小章及统一发票
     */
    private String stamp;

    /**
     * 厂商原来名称
     */
    private String originName;

    /**
     * 停权期限（起）
     */
    private String stopPeriodFrom;

    /**
     * 停权期限（迄）
     */
    private String stopPeriodTo;

    /**
     * 营利事业登记证
     */
    private String profitRegis;

    /**
     * 工厂登记证
     */
    private String factoryRegis;

    /**
     * 公司成立日期
     */
    private String foundDate;

    /**
     * 组织型态
     */
    private String orgType;

    /**
     * 员工人数
     */
    private String emplAmout;

    /**
     * 厂房总面积
     */
    private String factoryArea;

    /**
     * 品质认证内容
     */
    private String certification;

    /**
     * 销售对象
     */
    private String customer;

    /**
     * 主要生产设备
     */
    private String equipment;

    /**
     * 联络人
     */
    private String contact;

    /**
     * 部门
     */
    private String department;

    /**
     * 职称
     */
    private String title;

    /**
     * 邮递区号（联络地址）
     */
    private String contactZip;

    /**
     * 联络地址
     */
    private String contactAddr;

    /**
     * 邮寄区号（发票地址）
     */
    private String billZipCode;

    /**
     * 发票地址
     */
    private String billAddress;

    /**
     * 电话
     */
    private String officeTelephoneNo;

    /**
     * 传真
     */
    private String fax;

    /**
     * 电传号码
     */
    private String telex;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 网址
     */
    private String web;

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
     * 建立时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 联络人2
     */
    private String contact2;

    /**
     * 联络人3
     */
    private String contact3;

    /**
     * 部门2
     */
    private String department2;

    /**
     * 部门3
     */
    private String department3;

    /**
     * 职称2
     */
    private String title2;

    /**
     * 职称3
     */
    private String title3;

    /**
     * 电话2
     */
    private String officeTelephoneNo2;

    /**
     * 电话3
     */
    private String officeTelephoneNo3;

    /**
     * 手机2
     */
    private String mobile2;

    /**
     * 手机3
     */
    private String mobile3;

    /**
     * 邮箱2
     */
    private String email2;

    /**
     * 邮箱3
     */
    private String email3;

    /**
     * 最近订购日
     */
    private String lastOrderDate;

    /**
     * 建立人员
     */
    private String createUser;

    /**
     * 汇款帐号名称１
     */
    private String remitName1;

    /**
     * 汇款帐号名称2
     */
    private String remitName2;

    /**
     * 汇款帐号名称3
     */
    private String remitName3;

    /**
     * 汇款银行4
     */
    private String remitBank4;

    /**
     * 汇款帐号4
     */
    private String remitAccount4;

    /**
     * 汇款帐号名称4
     */
    private String remitName4;

    /**
     * 汇款银行5
     */
    private String remitBank5;

    /**
     * 汇款帐号5
     */
    private String remitAccount5;

    /**
     * 汇款帐号名称5
     */
    private String remitName5;

    /**
     * 厂商特性代码
     */
    private String specialCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
