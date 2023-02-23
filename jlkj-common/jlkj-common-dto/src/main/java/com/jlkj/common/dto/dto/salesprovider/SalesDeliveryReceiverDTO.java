package com.jlkj.common.dto.dto.salesprovider;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货人建立作业主档
 *  @author xk
 * @TableName t_sales_delivery_receiver
 */
@Data
public class SalesDeliveryReceiverDTO {
    /**
     * 收货人ID
     */
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 客户编号
     */
    private String custNo;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 收货人序号
     */
    private String receiverOrderNo;

    /**
     * 收货人编号
     */
    private String receiverNo;

    /**
     * 收货人名称
     */
    private String receiverName;

    /**
     * 收货人简称
     */
    private String receiverShortName;

    /**
     * 联络人
     */
    private String contactMan;

    /**
     * 联络电话
     */
    private String phone;

    /**
     * 备注
     */
    private String meno;

    /**
     * 最后修改人员
     */
    private String modifyUserId;

    /**
     * 最后修改日期
     */
    private String modifyDate;

    /**
     * 状态
     */
    private String status;

    /**
     * update识别值
     */
    private String timeStampId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;


}
