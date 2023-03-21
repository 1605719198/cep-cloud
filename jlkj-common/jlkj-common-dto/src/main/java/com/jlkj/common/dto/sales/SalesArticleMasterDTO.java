package com.jlkj.common.dto.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 合同条款主档资料
 * @author xk
 * @TableName t_sales_article_master
 */
@Data
public class SalesArticleMasterDTO {
    /**
     * ID
     */
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 版次
     */
    private String editionVersion;

    /**
     * 生效日期
     */
    private String effectDate;

    /**
     * 产品型态
     */
    private String prodType;

    /**
     * 异动人员
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String updateDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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
     * 状态标识
     */
    private String status;


}
