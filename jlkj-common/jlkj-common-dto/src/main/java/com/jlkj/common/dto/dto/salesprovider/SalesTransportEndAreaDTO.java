package com.jlkj.common.dto.dto.salesprovider;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 终到地区维护
 * @author xk
 * @TableName t_sales_transport_end_area
 */
@Data
public class SalesTransportEndAreaDTO {
    /**
     * 终到地区ID
     */
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 序号
     */
    private String seqNo;

    /**
     * 终到地区
     */
    private String area;

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
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

}
