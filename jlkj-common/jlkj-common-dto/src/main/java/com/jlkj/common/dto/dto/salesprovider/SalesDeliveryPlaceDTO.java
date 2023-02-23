package com.jlkj.common.dto.dto.salesprovider;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货人管理-指送地点
 * @author xk
 * @TableName t_sales_delivery_place
 */
@Data
public class SalesDeliveryPlaceDTO {
    /**
     * 指送地点ID
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
     * 收货人编号
     */
    private String receiverNo;

    /**
     * 序号
     */
    private String seqNo;

    /**
     * 运输别代码
     */
    private String transportWayNo;

    /**
     * 地点代码
     */
    private String locationNo;

    /**
     * 收货地址
     */
    private String receiverAddr;

    /**
     * 终到地区
     */
    private String areaNo;

    /**
     * 专用线
     */
    private String specialLine;

    /**
     * 车种限制
     */
    private String carLimit;

    /**
     * 邮递区号
     */
    private String billZip;

    /**
     * 其他资料
     */
    private String otherInfo;

    /**
     * 定价区域
     */
    private String priceArea;

    /**
     * 销售区域
     */
    private String saleArea;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 联络电话
     */
    private String phone;

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

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;


}
