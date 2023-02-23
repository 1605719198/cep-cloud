package com.jlkj.common.dto.dto.salesprovider;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同条款明细资料
 * @author xk
 * @TableName t_sales_article_second
 */
@Data
public class SalesArticleSecondDTO implements Serializable {
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
     * 产品型态
     */
    private String prodType;

    /**
     * 生效日期
     */
    private String effectDate;

    /**
     * 序号
     */
    private String serialNo;

    /**
     * 合同条款
     */
    private String article;

    /**
     * 状态
     */
    private String status;

    /**
     * 异动人员
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String updateDate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

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
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;


}
