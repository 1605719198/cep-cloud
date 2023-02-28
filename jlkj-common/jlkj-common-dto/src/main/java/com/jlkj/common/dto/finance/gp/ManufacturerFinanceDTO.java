package com.jlkj.common.dto.finance.gp;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;



import java.util.Date;

/**
 * 厂商报支关系资料表
 * @author wang'mai
 * @TableName t_manufacturer_finance
 */
@TableName(value ="t_manufacturer_finance")
@Data
public class ManufacturerFinanceDTO {
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
     * 联络人
     */
    private String contact;

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
     * 付款方式
     */
    private String payType;

    /**
     * 邮寄区号（发票地址）
     */
    private String billZipCode;

    /**
     * 发票寄送地址
     */
    private String billSendAddr;

    /**
     * 发票寄送地址区号
     */
    private String billSendZip;

    /**
     * 建立人员
     */
    private String createUser;


    /**
     * 受款银行汇入地点1
     */
    private String acceptRemitBank1;

    /**
     * 受款银行汇入地点2
     */
    private String acceptRemitBank2;

    /**
     * 受款银行汇入地点3
     */
    private String acceptRemitBank3;

    /**
     * 市1
     */
    private String ville1;

    /**
     * 市2
     */
    private String ville2;

    /**
     * 市3
     */
    private String ville3;

    /**
     * 省1
     */
    private String province1;

    /**
     * 省2
     */
    private String province2;

    /**
     * 省3
     */
    private String province3;

    /**
     * 区1
     */
    private String mainAreaId1;

    /**
     * 区2
     */
    private String mainAreaId2;

    /**
     * 区3
     */
    private String mainAreaId3;

    /**
     * 汇款银行行号1
     */
    private String bankNum1;

    /**
     * 汇款银行行号2
     */
    private String bankNum2;

    /**
     * 汇款银行行号3
     */
    private String bankNum3;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
