package com.jlkj.common.dto.dto.salesprovider;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 交运地点专用线资料
 * @author xk
 * @TableName t_sales_transport_special_line
 */
@Data
public class SalesTransportSpecialLineDTO implements Serializable {
    /**
     * 专用线ID
     */
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 运输方式代码
     */
    private String transWayNo;

    /**
     * 局／港口／地点代码
     */
    private String siteNo;

    /**
     * 站／码头代码
     */
    private String stationNo;

    /**
     * 专用线代码
     */
    private String specialLineNo;

    /**
     * 专用线中文名称
     */
    private String lineName;

    /**
     * 生效日期
     */
    private String workDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

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
