package com.jlkj.common.dto.dto.salesprovider;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交运地点主资料
 * @author xk
 * @TableName t_sales_transport_site
 */
@Data
public class SalesTransportSiteDTO {
    /**
     * ID
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
     * 终到地区代码
     */
    private String areaNo;
    /**
     * 终到地区名称
     */
    private String area;

    /**
     * 局／港口／地点中文名称
     */
    private String siteChinaName;

    /**
     * 局／港口／地点英文名称
     */
    private String siteEnglishName;

    /**
     * 里程（公里）
     */
    private Integer mileage;

    /**
     * 到站天数
     */
    private Integer days;

    /**
     * 最大起重（吨）
     */
    private BigDecimal craneMax;

    /**
     * 泊位大小（吨）
     */
    private BigDecimal berth;

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
     * 锁定日期时间（起）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lockDateTimeStart;

    /**
     * 锁定日期时间（讫）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lockDateTimeEnd;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;


}
