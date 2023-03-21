package com.jlkj.common.dto.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交运地点副资料(铁、汽、水运)
 * @author xk
 * @TableName t_sales_transport_station
 */
@Data
public class SalesTransportStationDTO {
    /**
     * 到站ID
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
     * 站／码头名称
     */
    private String stationName;

    /**
     * 到站天数
     */
    private Integer days;

    /**
     * 生效日期
     */
    private String workDate;

    /**
     * 限制口代码
     */
    private String limitNo;

    /**
     * 限制口与否
     */
    private String isLimit;

    /**
     * 限制量（吨）
     */
    private BigDecimal limitQty;

    /**
     * 接单年月
     */
    private String orderYearMonth;

    /**
     * 已分配量
     */
    private BigDecimal assignedQty;

    /**
     * 已接单量
     */
    private BigDecimal orderQty;

    /**
     * 锁定日期（起）
     */
    private String lockDateStart;

    /**
     * 锁定时间（起）
     */
    private String lockTimeStart;

    /**
     * 锁定日期（讫）
     */
    private String lockDateEnd;

    /**
     * 锁定时间（讫）
     */
    private String lockTimeEnd;

    /**
     * 锁定状态
     */
    private String lockStatus;

    /**
     * 终到地区代码
     */
    private String areaNo;

    /**
     * 终到地区名称
     */
    private String area;

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

    /**
     * 锁定时间起
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lockDateTimeStart;

    /**
     * 锁定时间讫
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lockDateTimeEnd;

}
